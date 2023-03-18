package org.dddml.suidemocontracts.config;

import org.dddml.suidemocontracts.domain.domainname.AbstractDomainNameApplicationService;
import org.dddml.suidemocontracts.domain.domainname.DomainNameApplicationService;
import org.dddml.suidemocontracts.domain.domainname.DomainNameStateQueryRepository;
import org.dddml.suidemocontracts.domain.domainname.DomainNameStateRepository;
import org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameEventStore;
import org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameStateQueryRepository;
import org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameStateRepository;
import org.dddml.suidemocontracts.domain.order.hibernate.HibernateOrderItemEventDao;
import org.dddml.suidemocontracts.specialization.EventStore;
import org.dddml.suidemocontracts.specialization.ReadOnlyProxyGenerator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AggregatesHibernateConfig {
    /**
     * <bean id="domainNameStateRepository" class="org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameStateRepository">
     * <property name="sessionFactory" ref="hibernateSessionFactory"/>
     * <property name="readOnlyProxyGenerator" ref="stateReadOnlyProxyGenerator"/>
     * </bean>
     */
    @Bean
    public DomainNameStateRepository domainNameStateRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateDomainNameStateRepository repository = new HibernateDomainNameStateRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    /**
     * <bean id="domainNameStateQueryRepository" class="org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameStateQueryRepository">
     * <property name="sessionFactory" ref="hibernateSessionFactory"/>
     * <property name="readOnlyProxyGenerator" ref="stateReadOnlyProxyGenerator"/>
     * </bean>
     */
    @Bean
    public DomainNameStateQueryRepository domainNameStateQueryRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateDomainNameStateQueryRepository repository = new HibernateDomainNameStateQueryRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    /**
     * <bean id="domainNameEventStore" class="org.dddml.suidemocontracts.domain.domainname.hibernate.HibernateDomainNameEventStore">
     * <property name="sessionFactory" ref="hibernateSessionFactory"/>
     * </bean>
     */
    @Bean
    public HibernateDomainNameEventStore domainNameEventStore(SessionFactory hibernateSessionFactory) {
        HibernateDomainNameEventStore eventStore = new HibernateDomainNameEventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    /**
     * <bean id="domainNameApplicationService" class="org.dddml.suidemocontracts.domain.domainname.AbstractDomainNameApplicationService$SimpleDomainNameApplicationService">
     * <constructor-arg ref="domainNameEventStore"/>
     * <constructor-arg ref="domainNameStateRepository"/>
     * <constructor-arg ref="domainNameStateQueryRepository"/>
     * </bean>
     */
    @Bean
    public DomainNameApplicationService domainNameApplicationService(
            @Qualifier("domainNameEventStore") EventStore domainNameEventStore,
            DomainNameStateRepository domainNameStateRepository,
            DomainNameStateQueryRepository domainNameStateQueryRepository) {
        return new AbstractDomainNameApplicationService.SimpleDomainNameApplicationService(
                domainNameEventStore,
                domainNameStateRepository,
                domainNameStateQueryRepository
        );
    }

    /**
     *   <bean id="orderItemEventDao" class="org.dddml.suidemocontracts.domain.order.hibernate.HibernateOrderItemEventDao">
     *     <property name="sessionFactory" ref="hibernateSessionFactory"/>
     *   </bean>
     */
    @Bean
    public HibernateOrderItemEventDao orderItemEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderItemEventDao dao = new HibernateOrderItemEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

}
