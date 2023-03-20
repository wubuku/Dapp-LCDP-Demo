package org.dddml.suidemocontracts.specialization.hibernate;

import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.IdGenerator;
import org.hibernate.SessionFactory;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;

import java.io.Serializable;

public class TableIdGenerator<TCommand, TState> implements IdGenerator<Long, TCommand, TState> {

    private static final String DEFAULT_ROOT_ENTITY_NAME = "org.dddml.suidemocontracts.tool.hibernate.Hello";

    private String rootEntityName = DEFAULT_ROOT_ENTITY_NAME;

    private SessionFactory sessionFactory;

    public String getRootEntityName() {
        return rootEntityName;
    }

    public void setRootEntityName(String rootEntityName) {
        this.rootEntityName = rootEntityName;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = (SessionFactory) ApplicationContext.current.get("hibernateSessionFactory");
        }
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long generateId(TCommand tCommand) {
        return getNextId();
    }

    @Override
    public Long getNextId() {
        TableGenerator idGen = (TableGenerator) ((SessionFactoryImpl) getSessionFactory()).getIdentifierGenerator(getRootEntityName());
        SessionImpl session = (SessionImpl) getSessionFactory().openSession();
        try {
            //System.out.println("================ Table Id Generator =================");
            Serializable id = idGen.generate(session, null);
            return (Long) id;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean equals(TCommand tCommand, TState tState) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isArbitraryIdEnabled() {
        return true;
    }
}
