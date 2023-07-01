// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.order.hibernate;

import java.util.*;
import java.math.BigInteger;
import org.dddml.roochdemocontracts.domain.*;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Criteria;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.roochdemocontracts.domain.order.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderStateRepository implements OrderStateRepository {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("OrderId", "TotalAmount", "EstimatedShipDate", "DeliveryWeekdays", "FavoriteDeliveryWeekday", "Items", "OrderShipGroups", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    public OrderState get(String id, boolean nullAllowed) {
        OrderState.SqlOrderState state = (OrderState.SqlOrderState)getCurrentSession().get(AbstractOrderState.SimpleOrderState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderState.SimpleOrderState();
            state.setOrderId(id);
        }
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (OrderState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderState.SqlOrderState.class, Saveable.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    public void save(OrderState state) {
        OrderState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if(s.getOffChainVersion() == null) {
            getCurrentSession().save(s);
        } else {
            getCurrentSession().update(s);
        }

        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().flush();
    }

    public void merge(OrderState detached) {
        OrderState persistent = getCurrentSession().get(AbstractOrderState.SimpleOrderState.class, detached.getOrderId());
        if (persistent != null) {
            merge(persistent, detached);
            getCurrentSession().merge(detached);
        } else {
            getCurrentSession().save(detached);
        }
        getCurrentSession().flush();
    }

    private void merge(OrderState persistent, OrderState detached) {
        ((OrderState.MutableOrderState) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getItems() != null) {
            removeNonExistentItems(persistent.getItems(), detached.getItems());
            for (OrderItemState d : detached.getItems()) {
                OrderItemState p = persistent.getItems().get(d.getProductObjectId());
                if (p == null)
                    getCurrentSession().save(d);
                else
                    merge(p, d);
            }
        }
        if (detached.getOrderShipGroups() != null) {
            removeNonExistentOrderShipGroups(persistent.getOrderShipGroups(), detached.getOrderShipGroups());
            for (OrderShipGroupState d : detached.getOrderShipGroups()) {
                OrderShipGroupState p = persistent.getOrderShipGroups().get(d.getShipGroupSeqId());
                if (p == null)
                    getCurrentSession().save(d);
                else
                    merge(p, d);
            }
        }
    }

    private void merge(OrderItemState persistent, OrderItemState detached) {
        ((OrderItemState.MutableOrderItemState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void merge(OrderShipGroupState persistent, OrderShipGroupState detached) {
        ((OrderShipGroupState.MutableOrderShipGroupState) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getOrderItemShipGroupAssociations() != null) {
            removeNonExistentOrderItemShipGroupAssociations(persistent.getOrderItemShipGroupAssociations(), detached.getOrderItemShipGroupAssociations());
            for (OrderItemShipGroupAssociationState d : detached.getOrderItemShipGroupAssociations()) {
                OrderItemShipGroupAssociationState p = persistent.getOrderItemShipGroupAssociations().get(d.getProductObjId());
                if (p == null)
                    getCurrentSession().save(d);
                else
                    merge(p, d);
            }
        }
    }

    private void merge(OrderItemShipGroupAssociationState persistent, OrderItemShipGroupAssociationState detached) {
        ((OrderItemShipGroupAssociationState.MutableOrderItemShipGroupAssociationState) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getSubitems() != null) {
            removeNonExistentSubitems(persistent.getSubitems(), detached.getSubitems());
            for (OrderItemShipGroupAssocSubitemState d : detached.getSubitems()) {
                OrderItemShipGroupAssocSubitemState p = persistent.getSubitems().get(d.getOrderItemShipGroupAssocSubitemDay());
                if (p == null)
                    getCurrentSession().save(d);
                else
                    merge(p, d);
            }
        }
    }

    private void merge(OrderItemShipGroupAssocSubitemState persistent, OrderItemShipGroupAssocSubitemState detached) {
        ((OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void removeNonExistentItems(EntityStateCollection<String, OrderItemState> persistentCollection, EntityStateCollection<String, OrderItemState> detachedCollection) {
        Set<String> removedIds = persistentCollection.stream().map(i -> i.getProductObjectId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getProductObjectId()));
        for (String i : removedIds) {
            OrderItemState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

    private void removeNonExistentOrderShipGroups(EntityStateCollection<Integer, OrderShipGroupState> persistentCollection, EntityStateCollection<Integer, OrderShipGroupState> detachedCollection) {
        Set<Integer> removedIds = persistentCollection.stream().map(i -> i.getShipGroupSeqId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getShipGroupSeqId()));
        for (Integer i : removedIds) {
            OrderShipGroupState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

    private void removeNonExistentOrderItemShipGroupAssociations(EntityStateCollection<String, OrderItemShipGroupAssociationState> persistentCollection, EntityStateCollection<String, OrderItemShipGroupAssociationState> detachedCollection) {
        Set<String> removedIds = persistentCollection.stream().map(i -> i.getProductObjId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getProductObjId()));
        for (String i : removedIds) {
            OrderItemShipGroupAssociationState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

    private void removeNonExistentSubitems(EntityStateCollection<Day, OrderItemShipGroupAssocSubitemState> persistentCollection, EntityStateCollection<Day, OrderItemShipGroupAssocSubitemState> detachedCollection) {
        Set<Day> removedIds = persistentCollection.stream().map(i -> i.getOrderItemShipGroupAssocSubitemDay()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getOrderItemShipGroupAssocSubitemDay()));
        for (Day i : removedIds) {
            OrderItemShipGroupAssocSubitemState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

}

