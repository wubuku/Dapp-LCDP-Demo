package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemShipGroupAssocSubitemStateDao implements OrderItemShipGroupAssocSubitemStateDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("OrderItemShipGroupAssocSubitemDay", "Description", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted", "OrderV2OrderId", "OrderShipGroupShipGroupSeqId", "OrderItemShipGroupAssociationProductId"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderItemShipGroupAssocSubitemState get(OrderV2OrderItemShipGroupAssocSubitemId id, boolean nullAllowed, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState state = (OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState) getCurrentSession().get(AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState();
            state.setOrderV2OrderItemShipGroupAssocSubitemId(id);
        }
        //if (getReadOnlyProxyGenerator() != null && state != null) {
        //    return (OrderItemShipGroupAssocSubitemState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderItemShipGroupAssocSubitemState.SqlOrderItemShipGroupAssocSubitemState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        //}
        if (state != null) { ((AbstractOrderItemShipGroupAssocSubitemState)state).setOrderV2State(aggregateState); }
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
    public void save(OrderItemShipGroupAssocSubitemState state)
    {
        OrderItemShipGroupAssocSubitemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemShipGroupAssocSubitemState) getReadOnlyProxyGenerator().getTarget(state);
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
    public Iterable<OrderItemShipGroupAssocSubitemState> findByOrderV2OrderIdAndOrderShipGroupShipGroupSeqIdAndOrderItemShipGroupAssociationProductId(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, OrderV2State aggregateState)
    {
        Long aggregateVersion = aggregateState.getOffChainVersion();
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderV2OrderId", orderV2OrderId))
            .add(Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderShipGroupShipGroupSeqId", orderShipGroupShipGroupSeqId))
            .add(Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderItemShipGroupAssociationProductId", orderItemShipGroupAssociationProductId))
            ;
        List<OrderItemShipGroupAssocSubitemState> list = criteria.add(partIdCondition).list();
        list.forEach(i -> ((AbstractOrderItemShipGroupAssocSubitemState)i).setOrderV2State(aggregateState));
        if (aggregateVersion != null) { assertNoConcurrencyConflict(orderV2OrderId, aggregateVersion); }
        return list;
    }

    @Override
    public void delete(OrderItemShipGroupAssocSubitemState state)
    {
        OrderItemShipGroupAssocSubitemState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderItemShipGroupAssocSubitemState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().delete(s);
    }

}

