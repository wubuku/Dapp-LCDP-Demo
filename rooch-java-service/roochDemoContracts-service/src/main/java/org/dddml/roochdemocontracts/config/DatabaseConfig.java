package org.dddml.roochdemocontracts.config;

import org.dddml.roochdemocontracts.specialization.NullReadOnlyProxyGenerator;
import org.dddml.roochdemocontracts.specialization.ReadOnlyProxyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

// Enables Spring's annotation-driven transaction management capability, similar to the support found in Spring's <tx:*> XML namespace.
@EnableTransactionManagement
@Configuration
public class DatabaseConfig {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    // 注入 Spring 配置
    @Autowired
    private Environment springEnvironment;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    // @Bean
    // public JpaTransactionManager transactionManager() throws IOException {
    // JpaTransactionManager transactionManager = new JpaTransactionManager();
    // transactionManager.setEntityManagerFactory(hibernateSessionFactory().getObject());
    // return transactionManager;
    // }

    @Bean(name = {
            "hibernateSessionFactory",
            "entityManagerFactory"
    })
    public LocalSessionFactoryBean hibernateSessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMappingLocations(
                ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                        .getResources("classpath:/hibernate/*.hbm.xml")
        );
        sessionFactory.setPackagesToScan("org.dddml.roochdemocontracts.*"); // add annotation mappings

        // hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        sessionFactory.setPhysicalNamingStrategy(
                new org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy());

        // Read config from application-test.yml
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
                springEnvironment.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.show_sql",
                springEnvironment.getProperty("spring.jpa.show-sql", "true"));
        hibernateProperties.setProperty("hibernate.connection.pool_size",
                springEnvironment.getProperty("spring.jpa.properties.hibernate.connection.pool_size", "1"));
        hibernateProperties.setProperty("hibernate.cache.provider_class",
                springEnvironment.getProperty("spring.jpa.properties.hibernate.cache.provider_class"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
                springEnvironment.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));

        log.debug("Hibernate properties: {}", hibernateProperties);
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public ReadOnlyProxyGenerator stateReadOnlyProxyGenerator() {
        return new NullReadOnlyProxyGenerator();
    }
}
