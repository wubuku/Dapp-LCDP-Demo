// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.*;
import java.math.BigInteger;
import org.dddml.suidemocontracts.domain.*;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Criteria;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderV2StateRepository implements OrderV2StateRepository {
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
    public OrderV2State get(String id, boolean nullAllowed) {
        OrderV2State.SqlOrderV2State state = (OrderV2State.SqlOrderV2State)getCurrentSession().get(AbstractOrderV2State.SimpleOrderV2State.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractOrderV2State.SimpleOrderV2State();
            state.setOrderId(id);
        }
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (OrderV2State) getReadOnlyProxyGenerator().createProxy(state, new Class[]{OrderV2State.SqlOrderV2State.class, Saveable.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    public void save(OrderV2State state) {
        OrderV2State s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (OrderV2State) getReadOnlyProxyGenerator().getTarget(state);
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

    public void merge(OrderV2State detached) {
        OrderV2State persistent = getCurrentSession().get(AbstractOrderV2State.SimpleOrderV2State.class, detached.getOrderId());
        if (persistent != null) {
            merge(persistent, detached);
            getCurrentSession().merge(detached);
        } else {
            getCurrentSession().save(detached);
        }
        getCurrentSession().flush();
    }

    private void merge(OrderV2State persistent, OrderV2State detached) {
        ((OrderV2State.MutableOrderV2State) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getItems() != null) {
            removeNonExistentItems(persistent.getItems(), detached.getItems());
            for (OrderV2ItemState d : detached.getItems()) {
                OrderV2ItemState p = persistent.getItems().get(d.getProductId());
                merge(p, d);
            }
        }
        if (detached.getOrderShipGroups() != null) {
            removeNonExistentOrderShipGroups(persistent.getOrderShipGroups(), detached.getOrderShipGroups());
            for (OrderShipGroupState d : detached.getOrderShipGroups()) {
                OrderShipGroupState p = persistent.getOrderShipGroups().get(d.getShipGroupSeqId());
                merge(p, d);
            }
        }
    }

    private void merge(OrderV2ItemState persistent, OrderV2ItemState detached) {
        ((OrderV2ItemState.MutableOrderV2ItemState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void merge(OrderShipGroupState persistent, OrderShipGroupState detached) {
        ((OrderShipGroupState.MutableOrderShipGroupState) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getOrderItemShipGroupAssociations() != null) {
            removeNonExistentOrderItemShipGroupAssociations(persistent.getOrderItemShipGroupAssociations(), detached.getOrderItemShipGroupAssociations());
            for (OrderItemShipGroupAssociationState d : detached.getOrderItemShipGroupAssociations()) {
                OrderItemShipGroupAssociationState p = persistent.getOrderItemShipGroupAssociations().get(d.getProductId());
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
                merge(p, d);
            }
        }
    }

    private void merge(OrderItemShipGroupAssocSubitemState persistent, OrderItemShipGroupAssocSubitemState detached) {
        ((OrderItemShipGroupAssocSubitemState.MutableOrderItemShipGroupAssocSubitemState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void removeNonExistentItems(EntityStateCollection<String, OrderV2ItemState> persistentCollection, EntityStateCollection<String, OrderV2ItemState> detachedCollection) {
        Set<String> removedIds = persistentCollection.stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getProductId()));
        for (String i : removedIds) {
            OrderV2ItemState s = persistentCollection.get(i);
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
        Set<String> removedIds = persistentCollection.stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getProductId()));
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

