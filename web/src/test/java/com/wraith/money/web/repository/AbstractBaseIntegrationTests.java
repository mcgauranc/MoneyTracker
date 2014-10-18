package com.wraith.money.web.repository;

import com.wraith.money.repository.*;
import com.wraith.money.web.ApplicationConfig;
import com.wraith.money.web.helper.EntityRepositoryHelper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.inject.Inject;
import java.util.Collection;

/**
 * User: rowan.massey
 * Date: 30/03/13
 * Time: 00:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
public abstract class AbstractBaseIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {


    @Inject
    protected AuthenticationManager authenticationManager;
    protected Authentication admin;

    @Inject
    @Qualifier("repositoryExporterHandlerAdapter")
    protected RequestMappingHandlerAdapter handlerAdapter;

    @Inject
    @Qualifier("repositoryExporterHandlerMapping")
    protected RequestMappingHandlerMapping handlerMapping;

    @Inject
    protected UsersRepository usersRepository;

    @Inject
    protected CategoryRepository categoryRepository;

    @Inject
    protected CurrencyRepository currencyRepository;

    @Inject
    protected PayeeRepository payeeRepository;

    @Inject
    protected AccountRepository accountRepository;

    protected EntityRepositoryHelper entityRepositoryHelper;

    @Before
    public void setUp() {
        admin = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("Admin", "Passw0rd"));
        entityRepositoryHelper = new EntityRepositoryHelper(handlerMapping, handlerAdapter);
    }

    protected void authenticate(String userName, String password) {
        authenticate(userName, password, null);
    }

    protected void authenticate(String userName, String password, Collection<GrantedAuthority> authorities) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password, authorities));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    /**
     * This method retrieves a specific JSONObject from the given array, based on provided parameters.
     *
     * @param key   The key to search for within the array of objects.
     * @param value The value for the given key.
     * @param array The array containing the given key and value.
     * @return The JSONObject which contains the provided key and value.
     */
    protected JSONObject getJsonObjectFromArray(String key, String value, JSONArray array) {
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.containsKey(key)) {
                if (jsonObject.get(key).toString().equalsIgnoreCase(value)) {
                    return jsonObject;
                }
            }
        }
        return null;
    }
}
