package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderV2ItemStateDao implements OrderV2ItemStateDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("ProductId", "Quantity", "ItemAmount", "Version", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted", "OrderV2OrderId"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderV2ItemState get(OrderV2ItemId id, boolean nullAllowed, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getVersion();
        OrderV2ItemState.SqlOrderV2ItemState state = (OrderV2ItemState.SqlOrderV2ItemState) getCurrentSession().get(AbstractOrderV2ItemState.SimpleOrderV2ItemState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderV2ItemState.SimpleOrderV2ItemState();
            state.setOrderV2ItemId(id);
        }
        //if (getReadOnlyProxyGenerator() != null && state != null) {
        //    return (OrderV2ItemState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderV2ItemState.SqlOrderV2ItemState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        //}
        if (state != null) { ((AbstractOrderV2ItemState)state).setOrderV2State(aggregateState); }
        if (nullAllowed && aggregateVersion != null) { assertNoConcurrencyConflict(id.getOrderV2OrderId(), aggregateVersion); }
        return state;
    }

    private void assertNoConcurrencyConflict(String aggregateId, Long aggregateVersion) {
        Criteria crit = getCurrentSession().createCriteria(AbstractOrderV2State.SimpleOrderV2State.class);
        crit.setProjection(Projections.property("version"));
        crit.add(Restrictions.eq("orderId", aggregateId));
        Long v = (Long) crit.uniqueResult();
        if (!aggregateVersion.equals(v)) {
            throw DomainError.named("concurrencyConflict", "Conflict between new state version (%1$s) and old version (%2$s)", v, aggregateVersion);
        }
    }

    @Override
    public void save(OrderV2ItemState state)
    {
        OrderV2ItemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderV2ItemState) getReadOnlyProxyGenerator().getTarget(state);
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
    public Iterable<OrderV2ItemState> findByOrderV2OrderId(String orderV2OrderId, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getVersion();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderV2ItemState.SimpleOrderV2ItemState.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderV2ItemId.orderV2OrderId", orderV2OrderId))
            ;
        List<OrderV2ItemState> list = criteria.add(partIdCondition).list();
        list.forEach(i -> ((AbstractOrderV2ItemState)i).setOrderV2State(aggregateState));
        if (aggregateVersion != null) { assertNoConcurrencyConflict(orderV2OrderId, aggregateVersion); }
        return list;
    }

    @Override
    public void delete(OrderV2ItemState state)
    {
        OrderV2ItemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderV2ItemState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().delete(s);
    }

}

