package org.dddml.aptosdemocontracts;

import org.dddml.aptosdemocontracts.specialization.ApplicationContext;
import org.dddml.aptosdemocontracts.specialization.spring.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableSwagger2
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
@EntityScan(basePackages = {
        "org.dddml.aptosdemocontracts.apots.contract"
})
@EnableScheduling
//@EnableAutoConfiguration
public class AptosDemoContractsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(AptosDemoContractsApplication.class, args);
        ApplicationContext.current = new SpringApplicationContext(ctx);
        ctx.publishEvent(new ContextStartedEvent(ctx));
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void initMoveObjectIdGeneratorObjects() {
//        moveObjectIdGeneratorObjectService.initMoveObjectIdGeneratorObjects();
//    }
}
