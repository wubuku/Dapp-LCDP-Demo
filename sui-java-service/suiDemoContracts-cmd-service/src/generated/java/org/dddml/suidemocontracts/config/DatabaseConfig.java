package org.dddml.suidemocontracts.config;

import org.dddml.suidemocontracts.specialization.NullReadOnlyProxyGenerator;
import org.dddml.suidemocontracts.specialization.ReadOnlyProxyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
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


    @Bean
    public JpaTransactionManager transactionManager() throws IOException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(hibernateSessionFactory().getObject());
        return transactionManager;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource);
//        emf.setMappingResources(//resolveMappingResources(
//                Arrays.stream(ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(
//                                "classpath:/hibernate/*.hbm.xml"))
//                        .map(r -> "classpath:/hibernate/" + r.getFilename()).toArray(String[]::new)
//        );
//        emf.setPackagesToScan("org.dddml.suidemocontracts.*"); //add annotation mappings
//        emf.setJpaProperties(
//                org.hibernate.cfg.Environment.getProperties() //resources/hibernate.properties
//        );
//        emf.setJpaVendorAdapter(vendorAdapter);
//
//        return emf;
//    }

//    private String[] resolveMappingResources(ResourcePatternResolver resolver, String locationPattern) throws IOException {
//        Resource[] resources = resolver.getResources(locationPattern);
//        PathMatcher matcher = new AntPathMatcher();//((PathMatchingResourcePatternResolver) resolver).getPathMatcher();
//        String[] classPaths = new String[resources.length];
//
//        for (int i = 0; i < resources.length; i++) {
//            Resource resource = resources[i];
//            String path = resource.getURL().getPath();
//            String classpath = "classpath:" + matcher.extractPathWithinPattern(locationPattern, path);
//            classPaths[i] = classpath;
//        }
//        return classPaths;
//    }

//    @Bean
//    public HibernateTransactionManager transactionManager() throws IOException {
//        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//        hibernateTransactionManager.setSessionFactory(hibernateSessionFactory().getObject());
//        return hibernateTransactionManager;
//    }

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
        sessionFactory.setPackagesToScan("org.dddml.suidemocontracts.*"); //add annotation mappings

        //hibernate.physical_naming_strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        sessionFactory.setPhysicalNamingStrategy(new org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy());

        sessionFactory.setHibernateProperties(
                org.hibernate.cfg.Environment.getProperties() //resources/hibernate.properties
        );
        return sessionFactory;
    }

    @Bean
    public ReadOnlyProxyGenerator stateReadOnlyProxyGenerator() {
        return new NullReadOnlyProxyGenerator();
    }

}
