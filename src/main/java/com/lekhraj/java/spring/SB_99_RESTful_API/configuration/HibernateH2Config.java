package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.lekhraj.java.spring.SB_99_RESTful_API",
        entityManagerFactoryRef = "entityManagerFactory_for_h2",
        transactionManagerRef = "transactionManager_for_h2"
)
public class HibernateH2Config
{
    @Autowired    private org.springframework.core.env.Environment env;

    // used - entityManagerFactory_for_h2
    private Properties hibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("h2.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("h2.jpa.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("h2.jpa.hibernate.ddl-auto"));
        return properties;
    }

    @Bean(name = "entityManagerFactory_for_h2") // 1. SessionFactory
    @Primary
    public LocalSessionFactoryBean sessionFactory(
            @Qualifier("dataSource_for_h2") DataSource dataSource)
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan("com.lekhraj.java.spring.SB_99_RESTful_API.entities");
            sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean(name = "transactionManager_for_h2") // 2. HibernateTransactionManager
    public PlatformTransactionManager hibernateTransactionManager(
            @Qualifier("entityManagerFactory_for_h2") SessionFactory sessionFactory)
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean(name = "dataSource_for_h2") //  3. javax.sql.DataSource
    public DataSource dataSource()
    {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("h2.datasource.driverClassName"))
                .url(env.getProperty("h2.datasource.url"))
                .username(env.getProperty("h2.datasource.username"))
                .password(env.getProperty("h2.datasource.password"))
                .build();
    }

    @Bean(name = "PlatformTransactionManager_for_h2")
    public TransactionTemplate transactionTemplate(
            @Qualifier("transactionManager_for_h2") PlatformTransactionManager transactionManager)
    {
        return new TransactionTemplate(transactionManager);
    }



    // =========== JPA (not in use) =========
    //@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.lekhraj.java.spring.SB_99_RESTful_API.entities");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    // @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}

