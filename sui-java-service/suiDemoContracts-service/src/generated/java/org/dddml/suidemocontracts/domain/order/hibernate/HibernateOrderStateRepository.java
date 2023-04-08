// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.order.hibernate;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.Session;
import org.hibernate.Criteria;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderStateRepository implements OrderStateRepository {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("Id", "TotalAmount", "Items", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
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
            state.setId(id);
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
        OrderState persistent = getCurrentSession().get(AbstractOrderState.SimpleOrderState.class, detached.getId());
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
                OrderItemState p = persistent.getItems().get(d.getProductId());
                merge(p, d);
            }
        }
    }

    private void merge(OrderItemState persistent, OrderItemState detached) {
        ((OrderItemState.MutableOrderItemState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void removeNonExistentItems(EntityStateCollection<String, OrderItemState> persistentCollection, EntityStateCollection<String, OrderItemState> detachedCollection) {
        Set<String> removedIds = persistentCollection.stream().map(i -> i.getProductId()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getProductId()));
        for (String i : removedIds) {
            OrderItemState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

}

