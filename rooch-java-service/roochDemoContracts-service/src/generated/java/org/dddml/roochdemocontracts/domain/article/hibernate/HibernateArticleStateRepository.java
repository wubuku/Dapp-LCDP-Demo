// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.article.hibernate;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;
import org.hibernate.Session;
import org.hibernate.Criteria;
//import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.SessionFactory;
import org.dddml.roochdemocontracts.domain.article.*;
import org.dddml.roochdemocontracts.specialization.*;
import org.dddml.roochdemocontracts.specialization.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

public class HibernateArticleStateRepository implements ArticleStateRepository {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return this.sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    private static final Set<String> readOnlyPropertyPascalCaseNames = new HashSet<String>(Arrays.asList("Id", "Title", "Author", "Content", "Tags", "References", "Version", "OffChainVersion", "CreatedBy", "CreatedAt", "UpdatedBy", "UpdatedAt", "Active", "Deleted"));
    
    private ReadOnlyProxyGenerator readOnlyProxyGenerator;
    
    public ReadOnlyProxyGenerator getReadOnlyProxyGenerator() {
        return readOnlyProxyGenerator;
    }

    public void setReadOnlyProxyGenerator(ReadOnlyProxyGenerator readOnlyProxyGenerator) {
        this.readOnlyProxyGenerator = readOnlyProxyGenerator;
    }

    @Transactional(readOnly = true)
    public ArticleState get(String id, boolean nullAllowed) {
        ArticleState.SqlArticleState state = (ArticleState.SqlArticleState)getCurrentSession().get(AbstractArticleState.SimpleArticleState.class, id);
        if (!nullAllowed && state == null) {
            state = new AbstractArticleState.SimpleArticleState();
            state.setId(id);
        }
        if (getReadOnlyProxyGenerator() != null && state != null) {
            return (ArticleState) getReadOnlyProxyGenerator().createProxy(state, new Class[]{ArticleState.SqlArticleState.class, Saveable.class}, "getStateReadOnly", readOnlyPropertyPascalCaseNames);
        }
        return state;
    }

    public void save(ArticleState state) {
        ArticleState s = state;
        if (getReadOnlyProxyGenerator() != null) {
            s = (ArticleState) getReadOnlyProxyGenerator().getTarget(state);
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

    public void merge(ArticleState detached) {
        ArticleState persistent = getCurrentSession().get(AbstractArticleState.SimpleArticleState.class, detached.getId());
        if (persistent != null) {
            merge(persistent, detached);
            getCurrentSession().merge(detached);
        } else {
            getCurrentSession().save(detached);
        }
        getCurrentSession().flush();
    }

    private void merge(ArticleState persistent, ArticleState detached) {
        ((ArticleState.MutableArticleState) detached).setOffChainVersion(persistent.getOffChainVersion());
        if (detached.getReferences() != null) {
            removeNonExistentReferences(persistent.getReferences(), detached.getReferences());
            for (ReferenceState d : detached.getReferences()) {
                ReferenceState p = persistent.getReferences().get(d.getReferenceNumber());
                merge(p, d);
            }
        }
    }

    private void merge(ReferenceState persistent, ReferenceState detached) {
        ((ReferenceState.MutableReferenceState) detached).setOffChainVersion(persistent.getOffChainVersion());
    }

    private void removeNonExistentReferences(EntityStateCollection<BigInteger, ReferenceState> persistentCollection, EntityStateCollection<BigInteger, ReferenceState> detachedCollection) {
        Set<BigInteger> removedIds = persistentCollection.stream().map(i -> i.getReferenceNumber()).collect(java.util.stream.Collectors.toSet());
        detachedCollection.forEach(i -> removedIds.remove(i.getReferenceNumber()));
        for (BigInteger i : removedIds) {
            ReferenceState s = persistentCollection.get(i);
            persistentCollection.remove(s);
            getCurrentSession().delete(s);
        }
    }

}

