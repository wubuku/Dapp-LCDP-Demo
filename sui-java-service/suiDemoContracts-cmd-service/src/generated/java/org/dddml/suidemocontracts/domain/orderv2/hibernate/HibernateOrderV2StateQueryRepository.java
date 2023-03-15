package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderV2StateQueryRepository implements OrderV2StateQueryRepository
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("OrderId", "TotalAmount", "EstimatedShipDate", "Items", "OrderShipGroups", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    public OrderV2State get(String id) {

        OrderV2State state = (OrderV2State)getCurrentSession().get(AbstractOrderV2State.SimpleOrderV2State.class, id);
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (OrderV2State) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderV2State.SqlOrderV2State.class, Saveable.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    @Transactional(readOnly = true)
    public Iterable<OrderV2State> getAll(Integer firstResult, Integer maxResults)
    {
        Criteria criteria = getCurrentSession().createCriteria(OrderV2State.class);
        if (firstResult != null) { criteria.setFirstResult(firstResult); }
        if (maxResults != null) { criteria.setMaxResults(maxResults); }
         addNotDeletedRestriction(criteria);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public Iterable<OrderV2State> get(Iterable<Map.Entry<String, Object>> filter, List<String> orders, Integer firstResult, Integer maxResults)
    {
        Criteria criteria = getCurrentSession().createCriteria(OrderV2State.class);

        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, firstResult, maxResults);
        addNotDeletedRestriction(criteria);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public Iterable<OrderV2State> get(org.dddml.support.criterion.Criterion filter, List<String> orders, Integer firstResult, Integer maxResults)
    {
        Criteria criteria = getCurrentSession().createCriteria(OrderV2State.class);

        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, firstResult, maxResults);
        addNotDeletedRestriction(criteria);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public OrderV2State getFirst(Iterable<Map.Entry<String, Object>> filter, List<String> orders)
    {
        List<OrderV2State> list = (List<OrderV2State>)get(filter, orders, 0, 1);
        if (list == null || list.size() <= 0)
        {
            return null;
        }
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public OrderV2State getFirst(Map.Entry<String, Object> keyValue, List<String> orders)
    {
        List<Map.Entry<String, Object>> filter = new ArrayList<>();
        filter.add(keyValue);
        return getFirst(filter, orders);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderV2State> getByProperty(String propertyName, Object propertyValue, List<String> orders, Integer firstResult, Integer maxResults)
    {
        Map.Entry<String, Object> keyValue = new java.util.AbstractMap.SimpleEntry<String, Object> (propertyName, propertyValue);
        List<Map.Entry<String, Object>> filter = new ArrayList<Map.Entry<String, Object>>();
        filter.add(keyValue);
        return get(filter, orders, firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public long getCount(Iterable<Map.Entry<String, Object>> filter)
    {
        Criteria criteria = getCurrentSession().createCriteria(OrderV2State.class);
        criteria.setProjection(Projections.rowCount());
        if (filter != null) {
            HibernateUtils.criteriaAddFilter(criteria, filter);
        }
        addNotDeletedRestriction(criteria);
        return (long)criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public long getCount(org.dddml.support.criterion.Criterion filter)
    {
        Criteria criteria = getCurrentSession().createCriteria(OrderV2State.class);
        criteria.setProjection(Projections.rowCount());
        if (filter != null)
        {
            org.hibernate.criterion.Criterion hc = CriterionUtils.toHibernateCriterion(filter);
            criteria.add(hc);
        }
        addNotDeletedRestriction(criteria);
        return (long)criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public OrderV2ItemState getOrderV2Item(String orderV2OrderId, String productId) {
        OrderV2ItemId entityId = new OrderV2ItemId(orderV2OrderId, productId);
        return (OrderV2ItemState) getCurrentSession().get(AbstractOrderV2ItemState.SimpleOrderV2ItemState.class, entityId);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderV2ItemState> getOrderV2Items(String orderV2OrderId, org.dddml.support.criterion.Criterion filter, List<String> orders) {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderV2ItemState.SimpleOrderV2ItemState.class);
        org.hibernate.criterion.Junction partIdCondition = org.hibernate.criterion.Restrictions.conjunction()
            .add(org.hibernate.criterion.Restrictions.eq("orderV2ItemId.orderV2OrderId", orderV2OrderId))
            ;
        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, 0, Integer.MAX_VALUE);
        return criteria.add(partIdCondition).list();
    }

    @Transactional(readOnly = true)
    public OrderShipGroupState getOrderShipGroup(String orderV2OrderId, Integer shipGroupSeqId) {
        OrderV2OrderShipGroupId entityId = new OrderV2OrderShipGroupId(orderV2OrderId, shipGroupSeqId);
        return (OrderShipGroupState) getCurrentSession().get(AbstractOrderShipGroupState.SimpleOrderShipGroupState.class, entityId);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderShipGroupState> getOrderShipGroups(String orderV2OrderId, org.dddml.support.criterion.Criterion filter, List<String> orders) {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderShipGroupState.SimpleOrderShipGroupState.class);
        org.hibernate.criterion.Junction partIdCondition = org.hibernate.criterion.Restrictions.conjunction()
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderShipGroupId.orderV2OrderId", orderV2OrderId))
            ;
        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, 0, Integer.MAX_VALUE);
        return criteria.add(partIdCondition).list();
    }

    @Transactional(readOnly = true)
    public OrderItemShipGroupAssociationState getOrderItemShipGroupAssociation(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String productId) {
        OrderV2OrderItemShipGroupAssociationId entityId = new OrderV2OrderItemShipGroupAssociationId(orderV2OrderId, orderShipGroupShipGroupSeqId, productId);
        return (OrderItemShipGroupAssociationState) getCurrentSession().get(AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState.class, entityId);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderItemShipGroupAssociationState> getOrderItemShipGroupAssociations(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, org.dddml.support.criterion.Criterion filter, List<String> orders) {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssociationState.SimpleOrderItemShipGroupAssociationState.class);
        org.hibernate.criterion.Junction partIdCondition = org.hibernate.criterion.Restrictions.conjunction()
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderItemShipGroupAssociationId.orderV2OrderId", orderV2OrderId))
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderItemShipGroupAssociationId.orderShipGroupShipGroupSeqId", orderShipGroupShipGroupSeqId))
            ;
        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, 0, Integer.MAX_VALUE);
        return criteria.add(partIdCondition).list();
    }

    @Transactional(readOnly = true)
    public OrderItemShipGroupAssocSubitemState getOrderItemShipGroupAssocSubitem(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, Integer orderItemShipGroupAssocSubitemSeqId) {
        OrderV2OrderItemShipGroupAssocSubitemId entityId = new OrderV2OrderItemShipGroupAssocSubitemId(orderV2OrderId, orderShipGroupShipGroupSeqId, orderItemShipGroupAssociationProductId, orderItemShipGroupAssocSubitemSeqId);
        return (OrderItemShipGroupAssocSubitemState) getCurrentSession().get(AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState.class, entityId);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderItemShipGroupAssocSubitemState> getOrderItemShipGroupAssocSubitems(String orderV2OrderId, Integer orderShipGroupShipGroupSeqId, String orderItemShipGroupAssociationProductId, org.dddml.support.criterion.Criterion filter, List<String> orders) {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssocSubitemState.SimpleOrderItemShipGroupAssocSubitemState.class);
        org.hibernate.criterion.Junction partIdCondition = org.hibernate.criterion.Restrictions.conjunction()
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderV2OrderId", orderV2OrderId))
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderShipGroupShipGroupSeqId", orderShipGroupShipGroupSeqId))
            .add(org.hibernate.criterion.Restrictions.eq("orderV2OrderItemShipGroupAssocSubitemId.orderItemShipGroupAssociationProductId", orderItemShipGroupAssociationProductId))
            ;
        HibernateUtils.criteriaAddFilterAndOrdersAndSetFirstResultAndMaxResults(criteria, filter, orders, 0, Integer.MAX_VALUE);
        return criteria.add(partIdCondition).list();
    }


    protected static void addNotDeletedRestriction(Criteria criteria) {
        criteria.add(org.hibernate.criterion.Restrictions.eq("deleted", false));
    }

}

