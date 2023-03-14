package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderShipGroupStateDao implements OrderShipGroupStateDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("ShipGroupSeqId", "ShipmentMethod", "OrderItemShipGroupAssociations", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted", "OrderV2OrderId"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderShipGroupState get(OrderV2OrderShipGroupId id, boolean nullAllowed, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        OrderShipGroupState.SqlOrderShipGroupState state = (OrderShipGroupState.SqlOrderShipGroupState) getCurrentSession().get(AbstractOrderShipGroupState.SimpleOrderShipGroupState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderShipGroupState.SimpleOrderShipGroupState();
            state.setOrderV2OrderShipGroupId(id);
        }
        //if (getReadOnlyProxyGenerator() != null && state != null) {
        //    return (OrderShipGroupState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderShipGroupState.SqlOrderShipGroupState.class, Saveable.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        //}
        if (state != null) { ((AbstractOrderShipGroupState)state).setOrderV2State(aggregateState); }
        if (nullAllowed && aggregateVersion != null) { assertNoConcurrencyConflict(id.getOrderV2OrderId(), aggregateVersion); }
        return state;
    }

    private void assertNoConcurrencyConflict(String aggregateId, Long aggregateVersion) {
        Criteria crit = getCurrentSession().createCriteria(AbstractOrderV2State.SimpleOrderV2State.class);
        crit.setProjection(Projections.property("offChainVersion"));
        crit.add(Restrictions.eq("orderId", aggregateId));
        Long v = (Long) crit.uniqueResult();
        if (!aggregateVersion.equals(v)) {
            throw DomainError.named("concurrencyConflict", "Conflict between new state version (%1$s) and old version (%2$s)", v, aggregateVersion);
        }
    }

    @Override
    public void save(OrderShipGroupState state)
    {
        OrderShipGroupState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderShipGroupState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if(s.getOffChainVersion() == null) {
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
    public Iterable<OrderShipGroupState> findByOrderV2OrderId(String orderV2OrderId, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderShipGroupState.SimpleOrderShipGroupState.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderV2OrderShipGroupId.orderV2OrderId", orderV2OrderId))
            ;
        List<OrderShipGroupState> list = criteria.add(partIdCondition).list();
        list.forEach(i -> ((AbstractOrderShipGroupState)i).setOrderV2State(aggregateState));
        if (aggregateVersion != null) { assertNoConcurrencyConflict(orderV2OrderId, aggregateVersion); }
        return list;
    }

    @Override
    public void delete(OrderShipGroupState state)
    {
        OrderShipGroupState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderShipGroupState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().delete(s);
    }

}

