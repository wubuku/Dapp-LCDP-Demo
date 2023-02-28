package org.dddml.suidemocontracts.domain.order.hibernate;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemEventDao implements OrderItemEventDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderItemEvent e)
    {
        getCurrentSession().save(e);
        if (e instanceof Saveable)
        {
            Saveable saveable = (Saveable) e;
            saveable.save();
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<OrderItemEvent> findByOrderEventId(OrderEventId orderEventId)
    {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemEvent.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderItemEventId.orderId", orderEventId.getId()))
            .add(Restrictions.eq("orderItemEventId.orderVersion", orderEventId.getVersion()))
            ;
        return criteria.add(partIdCondition).list();
    }

}

