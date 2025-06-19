package web.configuration;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class DatabaseConfig
{
    Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean("entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("web.model.entity");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager hibernateTransactionManager( SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager= new HibernateTransactionManager();
        transactionManager.setSessionFactory( sessionFactory);
        return transactionManager;
    }

    @Bean
    public Properties hibernateProperties(
            @Value("${hibernate.show_sql}") String showSql,
            @Value("${hibernate.dialect}") String dialect,
            @Value("${hibernate.hbm2ddl.auto}") String ddl,
            @Value("true") String format
    )
    {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", ddl);
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", showSql);
        hibernateProperties.setProperty("hibernate.format_sql", format);

        System.out.println("hibernateProperties : "+ hibernateProperties);
        return hibernateProperties;
    }

    @Bean
    public DataSource getDataSource(
            @Value("${jdbc.driverClassName}") String driverClassName,
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.user}") String user,
            @Value("${jdbc.pass}") String pass
    ) {

        logger.info("DBConfig :: Loading DataSource", driverClassName);

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(pass);
        DataSource retval = dataSourceBuilder.build();
        logger.info("DBConfig :: Loading DataSource", retval);

        return retval;
    }
}
