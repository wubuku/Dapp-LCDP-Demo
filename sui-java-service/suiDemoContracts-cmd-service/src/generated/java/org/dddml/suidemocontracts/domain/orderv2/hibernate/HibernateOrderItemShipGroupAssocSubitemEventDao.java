package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderItemShipGroupAssocSubitemEventDao implements OrderItemShipGroupAssocSubitemEventDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderItemShipGroupAssocSubitemEvent e)
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
    public Iterable<OrderItemShipGroupAssocSubitemEvent> findByOrderItemShipGroupAssociationEventId(OrderItemShipGroupAssociationEventId orderItemShipGroupAssociationEventId)
    {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderItemShipGroupAssocSubitemEvent.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderItemShipGroupAssocSubitemEventId.orderV2OrderId", orderItemShipGroupAssociationEventId.getOrderV2OrderId()))
            .add(Restrictions.eq("orderItemShipGroupAssocSubitemEventId.orderShipGroupShipGroupSeqId", orderItemShipGroupAssociationEventId.getOrderShipGroupShipGroupSeqId()))
            .add(Restrictions.eq("orderItemShipGroupAssocSubitemEventId.orderItemShipGroupAssociationProductId", orderItemShipGroupAssociationEventId.getProductId()))
            .add(Restrictions.eq("orderItemShipGroupAssocSubitemEventId.version", orderItemShipGroupAssociationEventId.getVersion()))
            ;
        return criteria.add(partIdCondition).list();
    }

}

