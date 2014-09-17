package com.wraith.money.web;

import com.wraith.money.repository.handler.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

/**
 * This is the entry class to the money track application. It sets up all of the security, controllers, services etc.
 */
@EnableAsync
@Configuration
@EnableJpaRepositories(basePackages = {"com.wraith.money.repository"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.wraith.money"})
@EnableTransactionManagement
public class ApplicationConfig {

    /**
     * This is the main entry point to run the application. If you wish to run the application execute the
     * spring-boot:run maven command. To allow for DEBUGGING, right click this method and select
     * "Debug 'ApplicationConfig.main()'"
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationConfig.class);
        app.run(args);
    }

    @Bean
    public RepositoryRestConfiguration restConfiguration() {
        return new RepositoryRestConfiguration();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5MB");
        factory.setMaxRequestSize("5MB");
        return factory.createMultipartConfig();
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
