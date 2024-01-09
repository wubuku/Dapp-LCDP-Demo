// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.aptosdemocontracts.domain.order.hibernate;

import java.util.*;
import java.math.BigInteger;
import org.dddml.aptosdemocontracts.domain.*;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Criteria;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.aptosdemocontracts.domain.order.*;
import org.dddml.aptosdemocontracts.specialization.*;
import org.dddml.aptosdemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderStateRepository implements OrderStateRepository {
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
            getCurrentSession().save(persistent);
        } else {
            getCurrentSession().save(detached);
        }
        getCurrentSession().flush();
    }

    private void merge(OrderState persistent, OrderState detached) {
        ((AbstractOrderState) persistent).merge(detached);
    }

}

