package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderShipGroupEventDao implements OrderShipGroupEventDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderShipGroupEvent e)
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
    public Iterable<OrderShipGroupEvent> findByOrderV2EventId(OrderV2EventId orderV2EventId)
    {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderShipGroupEvent.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderShipGroupEventId.orderV2OrderId", orderV2EventId.getOrderId()))
            .add(Restrictions.eq("orderShipGroupEventId.version", orderV2EventId.getVersion()))
            ;
        return criteria.add(partIdCondition).list();
    }

}

