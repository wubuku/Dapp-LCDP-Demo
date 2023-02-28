package org.dddml.suidemocontracts;

// import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
// import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
// import io.eventuate.tram.sagas.orchestration.SagaOrchestratorConfiguration;
import org.dddml.suidemocontracts.domain.StaticMethodConstraints;
// import org.dddml.suidemocontracts.domain.eventuate.tram.EventuateTramCommandHandlersConfiguration;
// import org.dddml.suidemocontracts.domain.eventuate.tram.EventuateTramDomainEventConsumersConfiguration;
import org.dddml.suidemocontracts.specialization.ApplicationContext;
// import org.dddml.suidemocontracts.specialization.eventuate.tram.EventuateTramDomainEventPublisherConfiguration;
import org.dddml.suidemocontracts.specialization.spring.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextStartedEvent;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by liusizuo on 2018/7/5 .
 */
@EnableSwagger2
@SpringBootApplication
@ImportResource("classpath*:/config/SpringConfigs.xml")
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
//@EnableAutoConfiguration
public class SuiDemoContractsApplication {
	public static void main(String[] args) {
		//StaticMethodConstraints.assertStaticVerificationAndMutationMethods();
		ConfigurableApplicationContext ctx = SpringApplication.run(SuiDemoContractsApplication.class, args);
		ApplicationContext.current = new SpringApplicationContext(ctx);
		ctx.publishEvent(new ContextStartedEvent(ctx));
	}
}
