package org.dddml.suidemocontracts.restful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@Profile("dev")
public class SpringfoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select()
                .paths(PathSelectors.regex("/error.*").negate())
                .build()
                .pathMapping("/api"); // set base path for swagger ui
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Document").version("1.0.0").build();
    }
}
