package com.wraith.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * User: rowan.massey
 * Date: 14/08/2014
 * Time: 23:39
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password").roles("ADMIN", "USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/accounts/**").hasRole("ADMIN")
                .antMatchers("/accountTypes/**").hasRole("ADMIN")
                .antMatchers("/addresses/**").hasRole("ADMIN")
                .antMatchers("/authorities/**").hasRole("ADMIN")
                .antMatchers("/countries/**").hasRole("ADMIN")
                .antMatchers("/currencies/**").hasRole("ADMIN")
                .antMatchers("/groups/**").hasRole("ADMIN")
                .antMatchers("/payees/**").hasRole("USER")
                .antMatchers("/transactions/**").hasRole("USER")
                .anyRequest().authenticated();
    }

//    <security:http-basic entry-point-ref="basicEntryPoint"/>
//    <security:form-login authentication-success-handler-ref="ajaxAuthenticationSuccessHandler"
//    login-page="/WEB-INF/pages/login/login.jsp"/>
//    <security:logout logout-url="/j_spring_security_logout"/>
//    <security:remember-me data-source-ref="dataSource"/>
//    <!--<security:custom-filter ref="digestFilter" after="BASIC_AUTH_FILTER"/>-->
//    </security:http>
//
//    <security:authentication-manager alias="authenticationManager">
//    <security:authentication-provider user-service-ref="moneyUserDetailsService">
//    <security:password-encoder hash="md5">
//    <security:salt-source user-property="username"/>
//    </security:password-encoder>
//    </security:authentication-provider>
//    </security:authentication-manager>
//
//    <bean id="rememberMeService"
//    class="org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices">
//    <constructor-arg name="userDetailsService" ref="moneyUserDetailsService"/>
//    <constructor-arg name="key" value="testKeyForMoney"/>
//    <constructor-arg name="tokenRepository" ref="customTokenService"/>
//    </bean>
//
//
//    <bean id="moneyUserDetailsService" class="com.wraith.security.MoneyUserDetailsService">
//    <property name="dataSource" ref="dataSource"/>
//    </bean>
//
//    <!--<bean id="digestFilter" class="org.springframework.security.web.authentication.www.DigestAuthenticationFilter">-->
//    <!--<property name="userDetailsService" ref="moneyUserDetailsService"/>-->
//    <!--<property name="authenticationEntryPoint" ref="digestEntryPoint"/>-->
//    <!--</bean>-->
//
//    <bean id="basicEntryPoint" class=
//            "org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint">
//    <property name="realmName" value="MoneyTracker Realm via Basic Authentication"/>
//    </bean>

}
