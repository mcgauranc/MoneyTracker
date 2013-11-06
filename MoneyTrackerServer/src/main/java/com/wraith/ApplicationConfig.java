package com.wraith;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wraith.repository.handler.*;
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
@ComponentScan(basePackages = {"com.wraith.configuration", "com.wraith.encoding", "com.wraith.repository", "com.wraith.security", "com.wraith.authentication."})
@EnableAsync
@EnableJpaRepositories(basePackages = {"com.wraith.repository"})
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
    @Value("${hibernate.cache.factory_class}")
    private String cacheFactoryClass;
    @Value("${hibernate.use_second_level_cache}")
    private String useSecondLevelCache;
    @Value("${hibernate.cache.use_query_cache}")
    private String useQueryCache;
    @Value("${hibernate.generate_statistics}")
    private String generateStatistics;
    @Value("${hibernate.show_sql}")
    private String showSql;

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
        vendorAdapter.setDatabasePlatform(dialect);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        properties.setProperty("hibernate.cache.region.factory_class", cacheFactoryClass);
        properties.setProperty("hibernate.cache.use_second_level_cache", useSecondLevelCache);
        properties.setProperty("hibernate.cache.use_query_cache", useQueryCache);
        properties.setProperty("hibernate.generate_statistics", generateStatistics);
        properties.setProperty("show_sql", showSql);

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

    @Bean(name = "accountEventHandler")
    AccountEventHandler accountEventHandler() {
        return new AccountEventHandler();
    }

    @Bean(name = "accountTypeEventHandler")
    AccountTypeEventHandler accountTypeEventHandler() {
        return new AccountTypeEventHandler();
    }

    @Bean(name = "addressEventHandler")
    AddressEventHandler addressEventHandler() {
        return new AddressEventHandler();
    }

    @Bean(name = "authoritiesEventHandler")
    AuthoritiesEventHandler authoritiesEventHandler() {
        return new AuthoritiesEventHandler();
    }

    @Bean(name = "categoryEventHandler")
    CategoryEventHandler categoryEventHandler() {
        return new CategoryEventHandler();
    }

    @Bean(name = "countryEventHandler")
    CountryEventHandler countryEventHandler() {
        return new CountryEventHandler();
    }

    @Bean(name = "currencyEventHandler")
    CurrencyEventHandler currencyEventHandler() {
        return new CurrencyEventHandler();
    }

    @Bean(name = "groupsEventHandler")
    GroupsEventHandler groupsEventHandler() {
        return new GroupsEventHandler();
    }
}
