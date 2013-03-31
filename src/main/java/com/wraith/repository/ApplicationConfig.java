package com.wraith.repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wraith.repository.handler.UserEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.config.RepositoryRestConfiguration;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.wraith.repository")
@EnableAsync
@EnableJpaRepositories(basePackages = "com.wraith.repository")
@PropertySource({"classpath:db.properties"})
@EnableTransactionManagement
public class ApplicationConfig {

    @Value("${db.minPoolSize}")
    private Integer minPoolSize;
    @Value("${db.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${db.maxStatementsPerConnection}")
    private Integer maxStatementsPerConnection;
    @Value("${db.maxStatements}")
    private Integer maxStatements;
    @Value("${db.connectionTestQuery}")
    private String connectionTestQuery;
    @Value("${db.acquireRetryAttempts}")
    private Integer acquireRetryAttempts;
    @Value("${db.acquireRetryDelay}")
    private Integer acquireRetryDelay;
    @Value("${db.idleConnectionTestPeriod}")
    private Integer idleConnectionTestPeriod;
    @Value("${db.testConnectionOnCheckin}")
    private Boolean testConnectionOnCheckin;
    @Value("${db.dialect}")
    private String dialect;

    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.driver}")
    private String driver;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String password;
    @Value("${hibernate.hbm2ddl}")
    private String hbm2ddl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]
                {new ClassPathResource("db.properties")};
        placeholderConfigurer.setLocations(resources);
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }

    @Bean
    public RepositoryRestConfiguration restConfiguration() {
        return new RepositoryRestConfiguration();
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        dataSource.setAcquireRetryDelay(acquireRetryDelay);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setJdbcUrl(jdbcUrl);
        try {
            dataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setUser(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //vendorAdapter.setDatabase(Database.SQL_SERVER);
        vendorAdapter.setDatabasePlatform(dialect);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource());
        factory.setJpaProperties(properties);

        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean(name = "userEventHandler")
    UserEventHandler userEventHandler() {
        return new UserEventHandler();
    }
}
