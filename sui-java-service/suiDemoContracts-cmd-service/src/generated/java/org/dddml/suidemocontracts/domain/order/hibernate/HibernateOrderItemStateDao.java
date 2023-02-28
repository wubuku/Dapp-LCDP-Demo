package org.dddml.suidemocontracts.domain.order.hibernate;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemStateDao implements OrderItemStateDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("ProductId", "Quantity", "ItemAmount", "Version", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted", "OrderId"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemState get(OrderItemId id, boolean nullAllowed, OrderState aggregateState)
    {
        Long aggregateVersion = aggregateState.getVersion();
        OrderItemState.SqlOrderItemState state = (OrderItemState.SqlOrderItemState) getCurrentSession().get(AbstractOrderItemState.SimpleOrderItemState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderItemState.SimpleOrderItemState();
            state.setOrderItemId(id);
        }
        //if (getReadOnlyProxyGenerator() != null && state != null) {
        //    return (OrderItemState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderItemState.SqlOrderItemState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        //}
        if (state != null) { ((AbstractOrderItemState)state).setOrderState(aggregateState); }
        if (nullAllowed && aggregateVersion != null) { assertNoConcurrencyConflict(id.getOrderId(), aggregateVersion); }
        return state;
    }

    private void assertNoConcurrencyConflict(String aggregateId, Long aggregateVersion) {
        Criteria crit = getCurrentSession().createCriteria(AbstractOrderState.SimpleOrderState.class);
        crit.setProjection(Projections.property("version"));
        crit.add(Restrictions.eq("id", aggregateId));
        Long v = (Long) crit.uniqueResult();
        if (!aggregateVersion.equals(v)) {
            throw DomainError.named("concurrencyConflict", "Conflict between new state version (%1$s) and old version (%2$s)", v, aggregateVersion);
        }
    }

    @Override
    public void save(OrderItemState state)
    {
        OrderItemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if(s.getVersion() == null) {
            getCurrentSession().save(s);
        }else {
            getCurrentSession().update(s);
        }

        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<OrderItemState> findByOrderId(String orderId, OrderState aggregateState)
    {
        Long aggregateVersion = aggregateState.getVersion();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemState.SimpleOrderItemState.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderItemId.orderId", orderId))
            ;
        List<OrderItemState> list = criteria.add(partIdCondition).list();
        list.forEach(i -> ((AbstractOrderItemState)i).setOrderState(aggregateState));
        if (aggregateVersion != null) { assertNoConcurrencyConflict(orderId, aggregateVersion); }
        return list;
    }

    @Override
    public void delete(OrderItemState state)
    {
        OrderItemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().delete(s);
    }

}

