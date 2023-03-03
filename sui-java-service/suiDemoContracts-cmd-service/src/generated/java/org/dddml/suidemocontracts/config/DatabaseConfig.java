package org.dddml.suidemocontracts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

// Enables Spring's annotation-driven transaction management capability, similar to the support found in Spring's <tx:*> XML namespace.
@EnableTransactionManagement
@Configuration
public class DatabaseConfig {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @Bean(name = {
            "hibernateSessionFactory",
            "entityManagerFactory"
    })
    public LocalSessionFactoryBean localSessionFactoryBean() throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMappingLocations(
                ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                        .getResources("classpath:/hibernate/*.hbm.xml")
        );
        sessionFactory.setHibernateProperties(
                org.hibernate.cfg.Environment.getProperties()
        );
        return sessionFactory;
    }
}
