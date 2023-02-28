package org.dddml.suidemocontracts.domain.orderv2.hibernate;

import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.specialization.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateOrderV2ItemEventDao implements OrderV2ItemEventDao
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void save(OrderV2ItemEvent e)
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
    public Iterable<OrderV2ItemEvent> findByOrderV2EventId(OrderV2EventId orderV2EventId)
    {
        Criteria criteria = getCurrentSession().createCriteria(AbstractOrderV2ItemEvent.class);
        Junction partIdCondition = Restrictions.conjunction()
            .add(Restrictions.eq("orderV2ItemEventId.orderV2OrderId", orderV2EventId.getOrderId()))
            .add(Restrictions.eq("orderV2ItemEventId.orderV2Version", orderV2EventId.getVersion()))
            ;
        return criteria.add(partIdCondition).list();
    }

}

