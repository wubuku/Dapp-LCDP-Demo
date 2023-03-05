package org.dddml.suidemocontracts.domain.daysummary.hibernate;

import java.util.*;
import org.dddml.suidemocontracts.domain.*;
import java.math.BigInteger;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.suidemocontracts.domain.daysummary.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateDaySummaryStateRepository implements DaySummaryStateRepository
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("Day", "Description", "Metadata", "ArrayData", "OptionalData", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    public DaySummaryState get(Day id, boolean nullAllowed) {
        DaySummaryState.SqlDaySummaryState state = (DaySummaryState.SqlDaySummaryState)getCurrentSession().get(AbstractDaySummaryState.SimpleDaySummaryState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractDaySummaryState.SimpleDaySummaryState();
            state.setDay(id);
        }
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (DaySummaryState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{DaySummaryState.SqlDaySummaryState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    public void save(DaySummaryState state)
    {
        DaySummaryState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (DaySummaryState) getReadOnlyProxyGenerator().getTarget(state);
        }
        if(s.getOffChainVersion() == null) {
            getCurrentSession().save(s);
        }else {
            getCurrentSession().update(s);
        }

        if (s instanceof Saveable)
        {
            Saveable saveable = (Saveable) s;
            saveable.save();
        }
        getCurrentSession().flush();
    }

    //protected static void addNotDeletedRestriction(Criteria criteria) {
    //    criteria.add(org.hibernate.criterion.Restrictions.eq("deleted", false));
    //}

}

