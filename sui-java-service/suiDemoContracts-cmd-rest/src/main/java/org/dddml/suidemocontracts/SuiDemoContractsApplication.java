package org.dddml.suidemocontracts;

// import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
// import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
// import io.eventuate.tram.sagas.orchestration.SagaOrchestratorConfiguration;

import org.dddml.suidemocontracts.specialization.ApplicationContext;
import org.dddml.suidemocontracts.specialization.spring.SpringApplicationContext;
import org.dddml.suidemocontracts.sui.contract.service.MoveObjectIdGeneratorObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableSwagger2
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
//@ImportResource("classpath*:/config/SpringConfigs.xml")
@EntityScan(basePackages = {
        "org.dddml.suidemocontracts.sui.contract"
})
@Import({
// 		//TramMessageProducerJdbcConfiguration.class,
// 		TramCommandProducerConfiguration.class,
// 		SagaOrchestratorConfiguration.class,
        //TramEventsPublisherConfiguration.class,
// 		EventuateTramDomainEventPublisherConfiguration.class,
// 		EventuateTramDomainEventConsumersConfiguration.class,
// 		EventuateTramCommandHandlersConfiguration.class,
// 		TramJdbcKafkaConfiguration.class
})
@EnableScheduling
//@EnableAutoConfiguration
public class SuiDemoContractsApplication {

    @Autowired
    private MoveObjectIdGeneratorObjectService moveObjectIdGeneratorObjectService;

    public static void main(String[] args) {
        //StaticMethodConstraints.assertStaticVerificationAndMutationMethods();
        ConfigurableApplicationContext ctx = SpringApplication.run(SuiDemoContractsApplication.class, args);
        ApplicationContext.current = new SpringApplicationContext(ctx);
        ctx.publishEvent(new ContextStartedEvent(ctx));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initMoveObjectIdGeneratorObjects() {
        moveObjectIdGeneratorObjectService.initMoveObjectIdGeneratorObjects();
    }
}
