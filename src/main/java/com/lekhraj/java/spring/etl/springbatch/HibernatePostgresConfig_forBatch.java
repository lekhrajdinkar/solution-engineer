package com.lekhraj.java.spring.etl.springbatch;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.lekhraj.java.spring.elt.springbatch",
        entityManagerFactoryRef = "entityManagerFactory_for_postgres2",
        transactionManagerRef = "transactionManager_for_postgres2"
)
public class HibernatePostgresConfig_forBatch
{
    @Autowired    private org.springframework.core.env.Environment env;

    // used - entityManagerFactory_for_postgres
    private Properties hibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("postgres.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("postgres.jpa.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("postgres.jpa.hibernate.ddl-auto"));
        return properties;
    }

    @Bean(name = "entityManagerFactory_for_postgres2") // 1. SessionFactory
    public LocalSessionFactoryBean sessionFactoryPostgres(
            @Qualifier("dataSource_for_postgres2") DataSource dataSource)
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan("com.lekhraj.java.spring.database2");
            sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean(name = "transactionManager_for_postgres2") // 2. HibernateTransactionManager
    public PlatformTransactionManager hibernateTransactionManager(
            @Qualifier("entityManagerFactory_for_postgres2") SessionFactory sessionFactory)
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean(name = "dataSource_for_postgres2") //  3. javax.sql.DataSource
    public DataSource dataSource()
    {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("postgres.datasource.driverClassName"))
                .url(env.getProperty("postgres.datasource.url"))
                .username(env.getProperty("postgres.datasource.username"))
                .password(env.getProperty("postgres.datasource.password"))
                .build();
        // dataSource.addDataSourceProperty("currentSchema", "schema_name");
    }


    @Bean(name = "PlatformTransactionManager_for_postgres2")
    public TransactionTemplate transactionTemplate(
            @Qualifier("transactionManager_for_postgres2") PlatformTransactionManager transactionManager)
    {
        return new TransactionTemplate(transactionManager);
    }


}

