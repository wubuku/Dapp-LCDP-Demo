package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemShipGroupAssociationEventDao implements OrderItemShipGroupAssociationEventDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderItemShipGroupAssociationEvent e)
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
    public Iterable<OrderItemShipGroupAssociationEvent> findByOrderShipGroupEventId(OrderShipGroupEventId orderShipGroupEventId)
    {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssociationEvent.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderItemShipGroupAssociationEventId.orderV2OrderId", orderShipGroupEventId.getOrderV2OrderId()))
            .add(Restrictions.eq("orderItemShipGroupAssociationEventId.orderShipGroupShipGroupSeqId", orderShipGroupEventId.getShipGroupSeqId()))
            .add(Restrictions.eq("orderItemShipGroupAssociationEventId.version", orderShipGroupEventId.getVersion()))
            ;
        return criteria.add(partIdCondition).list();
    }

}

