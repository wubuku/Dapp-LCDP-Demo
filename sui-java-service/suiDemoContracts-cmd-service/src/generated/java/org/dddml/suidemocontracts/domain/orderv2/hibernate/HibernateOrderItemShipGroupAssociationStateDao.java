package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemShipGroupAssociationStateDao implements OrderItemShipGroupAssociationStateDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("ProductId", "Quantity", "CancelQuantity", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted", "OrderV2OrderId", "OrderShipGroupShipGroupSeqId"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemShipGroupAssociationState get(OrderV2OrderItemShipGroupAssociationId id, boolean nullAllowed, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState state = (OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState) getCurrentSession().get(AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState();
            state.setOrderV2OrderItemShipGroupAssociationId(id);
        }
        //if (getReadOnlyProxyGenerator() != null && state != null) {
        //    return (OrderItemShipGroupAssociationState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderItemShipGroupAssociationState.SqlOrderItemShipGroupAssociationState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        //}
        if (state != null) { ((AbstractOrderItemShipGroupAssociationState)state).setOrderV2State(aggregateState); }
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
    public void save(OrderItemShipGroupAssociationState state)
    {
        OrderItemShipGroupAssociationState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemShipGroupAssociationState) getReadOnlyProxyGenerator().getTarget(state);
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
    public Iterable<OrderItemShipGroupAssociationState> findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqId(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderV2OrderItemShipGroupAssociationId.orderV2OrderId", orderV2OrderId))
            .add(Restrictions.eq("orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId", orderShipGroupShipGroupSeqId))
            ;
        List<OrderItemShipGroupAssociationState> list = criteria.add(partIdCondition).list();
        list.forEach(i -> ((AbstractOrderItemShipGroupAssociationState)i).setOrderV2State(aggregateState));
        if (aggregateVersion != null) { assertNoConcurrencyConflict(orderV2OrderId, aggregateVersion); }
        return list;
    }

    @Override
    public void delete(OrderItemShipGroupAssociationState state)
    {
        OrderItemShipGroupAssociationState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemShipGroupAssociationState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().delete(s);
    }

}

