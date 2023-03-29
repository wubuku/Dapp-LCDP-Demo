// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.config;

import org.dddml.suidemocontracts.domain.domainname.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.domainname.hibernate.*;
import org.dddml.suidemocontracts.domain.order.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.order.hibernate.*;
import org.dddml.suidemocontracts.domain.product.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.product.hibernate.*;
import org.dddml.suidemocontracts.domain.orderv2.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.orderv2.hibernate.*;
import org.dddml.suidemocontracts.domain.daysummary.*;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.domain.daysummary.hibernate.*;
import org.dddml.suidemocontracts.specialization.AggregateEventListener;
import org.dddml.suidemocontracts.specialization.EventStore;
import org.dddml.suidemocontracts.specialization.IdGenerator;
import org.dddml.suidemocontracts.specialization.ReadOnlyProxyGenerator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregatesHibernateConfig {


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

    @Bean
    public HibernateDomainNameEventStore domainNameEventStore(SessionFactory hibernateSessionFactory) {
        HibernateDomainNameEventStore eventStore = new HibernateDomainNameEventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    @Bean
    public AbstractDomainNameApplicationService.SimpleDomainNameApplicationService domainNameApplicationService(
            @Qualifier("domainNameEventStore") EventStore domainNameEventStore,
            DomainNameStateRepository domainNameStateRepository,
            DomainNameStateQueryRepository domainNameStateQueryRepository
    ) {
        AbstractDomainNameApplicationService.SimpleDomainNameApplicationService applicationService = new AbstractDomainNameApplicationService.SimpleDomainNameApplicationService(
                domainNameEventStore,
                domainNameStateRepository,
                domainNameStateQueryRepository
        );
        return applicationService;
    }



    @Bean
    public OrderItemEventDao orderItemEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderItemEventDao dao = new HibernateOrderItemEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

    @Bean
    public OrderStateRepository orderStateRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateOrderStateRepository repository = new HibernateOrderStateRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public OrderStateQueryRepository orderStateQueryRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateOrderStateQueryRepository repository = new HibernateOrderStateQueryRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public HibernateOrderEventStore orderEventStore(SessionFactory hibernateSessionFactory) {
        HibernateOrderEventStore eventStore = new HibernateOrderEventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    @Bean
    public AbstractOrderApplicationService.SimpleOrderApplicationService orderApplicationService(
            @Qualifier("orderEventStore") EventStore orderEventStore,
            OrderStateRepository orderStateRepository,
            OrderStateQueryRepository orderStateQueryRepository
    ) {
        AbstractOrderApplicationService.SimpleOrderApplicationService applicationService = new AbstractOrderApplicationService.SimpleOrderApplicationService(
                orderEventStore,
                orderStateRepository,
                orderStateQueryRepository
        );
        return applicationService;
    }



    @Bean
    public ProductStateRepository productStateRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateProductStateRepository repository = new HibernateProductStateRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public ProductStateQueryRepository productStateQueryRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateProductStateQueryRepository repository = new HibernateProductStateQueryRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public HibernateProductEventStore productEventStore(SessionFactory hibernateSessionFactory) {
        HibernateProductEventStore eventStore = new HibernateProductEventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    @Bean
    public AbstractProductApplicationService.SimpleProductApplicationService productApplicationService(
            @Qualifier("productEventStore") EventStore productEventStore,
            ProductStateRepository productStateRepository,
            ProductStateQueryRepository productStateQueryRepository
    ) {
        AbstractProductApplicationService.SimpleProductApplicationService applicationService = new AbstractProductApplicationService.SimpleProductApplicationService(
                productEventStore,
                productStateRepository,
                productStateQueryRepository
        );
        return applicationService;
    }



    @Bean
    public OrderV2ItemEventDao orderV2ItemEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderV2ItemEventDao dao = new HibernateOrderV2ItemEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

    @Bean
    public OrderShipGroupEventDao orderShipGroupEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderShipGroupEventDao dao = new HibernateOrderShipGroupEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

    @Bean
    public OrderItemShipGroupAssociationEventDao orderItemShipGroupAssociationEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderItemShipGroupAssociationEventDao dao = new HibernateOrderItemShipGroupAssociationEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

    @Bean
    public OrderItemShipGroupAssocSubitemEventDao orderItemShipGroupAssocSubitemEventDao(SessionFactory hibernateSessionFactory) {
        HibernateOrderItemShipGroupAssocSubitemEventDao dao = new HibernateOrderItemShipGroupAssocSubitemEventDao();
        dao.setSessionFactory(hibernateSessionFactory);
        return dao;
    }

    @Bean
    public OrderV2StateRepository orderV2StateRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateOrderV2StateRepository repository = new HibernateOrderV2StateRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public OrderV2StateQueryRepository orderV2StateQueryRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateOrderV2StateQueryRepository repository = new HibernateOrderV2StateQueryRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public HibernateOrderV2EventStore orderV2EventStore(SessionFactory hibernateSessionFactory) {
        HibernateOrderV2EventStore eventStore = new HibernateOrderV2EventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    @Bean
    public AbstractOrderV2ApplicationService.SimpleOrderV2ApplicationService orderV2ApplicationService(
            @Qualifier("orderV2EventStore") EventStore orderV2EventStore,
            OrderV2StateRepository orderV2StateRepository,
            OrderV2StateQueryRepository orderV2StateQueryRepository
    ) {
        AbstractOrderV2ApplicationService.SimpleOrderV2ApplicationService applicationService = new AbstractOrderV2ApplicationService.SimpleOrderV2ApplicationService(
                orderV2EventStore,
                orderV2StateRepository,
                orderV2StateQueryRepository
        );
        return applicationService;
    }



    @Bean
    public DaySummaryStateRepository daySummaryStateRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateDaySummaryStateRepository repository = new HibernateDaySummaryStateRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public DaySummaryStateQueryRepository daySummaryStateQueryRepository(
            SessionFactory hibernateSessionFactory,
            ReadOnlyProxyGenerator stateReadOnlyProxyGenerator
    ) {
        HibernateDaySummaryStateQueryRepository repository = new HibernateDaySummaryStateQueryRepository();
        repository.setSessionFactory(hibernateSessionFactory);
        repository.setReadOnlyProxyGenerator(stateReadOnlyProxyGenerator);
        return repository;
    }

    @Bean
    public HibernateDaySummaryEventStore daySummaryEventStore(SessionFactory hibernateSessionFactory) {
        HibernateDaySummaryEventStore eventStore = new HibernateDaySummaryEventStore();
        eventStore.setSessionFactory(hibernateSessionFactory);
        return eventStore;
    }

    @Bean
    public AbstractDaySummaryApplicationService.SimpleDaySummaryApplicationService daySummaryApplicationService(
            @Qualifier("daySummaryEventStore") EventStore daySummaryEventStore,
            DaySummaryStateRepository daySummaryStateRepository,
            DaySummaryStateQueryRepository daySummaryStateQueryRepository
    ) {
        AbstractDaySummaryApplicationService.SimpleDaySummaryApplicationService applicationService = new AbstractDaySummaryApplicationService.SimpleDaySummaryApplicationService(
                daySummaryEventStore,
                daySummaryStateRepository,
                daySummaryStateQueryRepository
        );
        return applicationService;
    }


}