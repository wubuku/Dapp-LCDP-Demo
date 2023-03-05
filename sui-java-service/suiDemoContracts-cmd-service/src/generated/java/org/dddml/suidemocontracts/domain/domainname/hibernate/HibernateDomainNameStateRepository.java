package org.dddml.suidemocontracts.domain.domainname.hibernate;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.suidemocontracts.domain.domainname.*;
import org.dddml.suidemocontracts.specialization.*;
import org.dddml.suidemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateDomainNameStateRepository implements DomainNameStateRepository
{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("DomainNameId", "ExpirationDate", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    public DomainNameState get(DomainNameId id, boolean nullAllowed) {
        DomainNameState.SqlDomainNameState state = (DomainNameState.SqlDomainNameState)getCurrentSession().get(AbstractDomainNameState.SimpleDomainNameState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractDomainNameState.SimpleDomainNameState();
            state.setDomainNameId(id);
        }
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (DomainNameState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{DomainNameState.SqlDomainNameState.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    public void save(DomainNameState state)
    {
        DomainNameState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (DomainNameState) getReadOnlyProxyGenerator().getTarget(state);
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

