package com.wraith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wraith.repository.handler.AccountEventHandler;
import com.wraith.repository.handler.AccountTypeEventHandler;
import com.wraith.repository.handler.AddressEventHandler;
import com.wraith.repository.handler.AuthoritiesEventHandler;
import com.wraith.repository.handler.CategoryEventHandler;
import com.wraith.repository.handler.CountryEventHandler;
import com.wraith.repository.handler.CurrencyEventHandler;
import com.wraith.repository.handler.GroupsEventHandler;
import com.wraith.repository.handler.UserEventHandler;

@EnableAsync
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.wraith.configuration", "com.wraith.encoding", "com.wraith.repository",
		"com.wraith.security", "com.wraith.authentication." })
@EnableTransactionManagement
public class ApplicationConfig {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

	@Bean
	public RepositoryRestConfiguration restConfiguration() {
		return new RepositoryRestConfiguration();
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
