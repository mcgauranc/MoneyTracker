package com.wraith.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wraith.ApplicationConfig;
import com.wraith.repository.entity.BaseEntity;
import com.wraith.repository.entity.Users;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

/**
 * User: rowan.massey
 * Date: 30/03/13
 * Time: 00:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfig.class)
@IntegrationTest
@ActiveProfiles(value = "testing")
public abstract class AbstractBaseIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

    public static final String ACCOUNTS_PATH = "accounts";
    public static final String USERS_PATH = "users";
    public static final String CURRENCY_PATH = "currency";
    public static final String ACCOUNT_TYPES_PATH = "accountTypes";
    public static final String COUNTRIES_PATH = "countries";
    public static final String ADDRESSES_PATH = "addresses";
    public static final String AUTHORITIES_PATH = "authorities";
    public static final String CATEGORIES_PATH = "categories";
    public static final String TRANSACTIONS_PATH = "transactions";
    public static final String GROUPS_PATH = "groups";
    public static final String PAYEES_PATH = "payees";

    protected ObjectMapper mapper;
    protected JSONParser parser;

    @Inject
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;
    protected Authentication admin;

    @Autowired
    protected RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    protected RequestMappingHandlerMapping handlerMapping;

    @Autowired
    protected UsersRepository usersRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected CurrencyRepository currencyRepository;

    @Autowired
    protected PayeeRepository payeeRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Before
    public void setUp() {
        admin = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("Admin", "Passw0rd"));
        mapper = new ObjectMapper();
        parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
    }

    protected void authenticate(String userName, String password) {
        authenticate(userName, password, null);
    }

    protected void authenticate(String userName, String password, Collection<GrantedAuthority> authorities) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password, authorities));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    /**
     * This method strips the 'http://localhost' from the provided uri and returns just the resource request.
     * e.g. /user/1
     *
     * @param uri The uri from which to remove the localhost string.
     * @return A resource uri. e.g. /user/1
     */
    protected String getResourceURI(String uri) {
        String LOCALHOST = "http://localhost/api";
        return StringUtils.remove(uri, LOCALHOST);
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


    /**
     * This method performs a GET request to the server to retrieve records, along with the associated parameters - if any.
     *
     * @param uri        The uri on which the request will be performed.
     * @param parameters The parameters that will get passed to the request.
     * @return The response object from the server.
     */
    protected MockHttpServletResponse performGetRequest(String uri, Map parameters) throws Exception {
        return performGetRequest(uri, parameters, HttpStatus.OK);
    }

    /**
     * This method performs a GET request to the server to retrieve records, along with the associated parameters - if any.
     *
     * @param uri        The uri on which the request will be performed.
     * @param parameters The parameters that will get passed to the request.
     * @return The response object from the server.
     */
    protected MockHttpServletResponse performGetRequest(String uri, Map parameters, HttpStatus expectedStatus) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");
        request.setRequestURI(uri);

        if (parameters != null) {
            request.setParameters(parameters);
        }

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertEquals(response.getStatus(), expectedStatus.value());
        return response;
    }

    /**
     * This method performs a GET request to the server to retrieve records.
     *
     * @param uri The uri on which the request will be performed.
     * @return The response object from the server.
     */
    protected MockHttpServletResponse performGetRequest(String uri) throws Exception {
        return performGetRequest(uri, null);
    }

    /**
     * This method performs a POST request to the server to create records.
     *
     * @param uri     The uri to which the content will be posted to.
     * @param content A byte array of the contents of the request to send to the server.
     * @return The response from the server.
     */
    protected MockHttpServletResponse performPostRequest(String uri, byte[] content) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("POST");
        request.setRequestURI(uri);
        request.setContentType("application/hal+json");
//        request.setContentType("application/json");
        request.setContent(content);

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        return response;
    }

    /**
     * This method performs a POST request to the server to create records.
     *
     * @param uri     The uri to which the content will be posted to.
     * @param content A byte array of the contents of the request to send to the server.
     * @return The response from the server.
     */
    protected MockHttpServletResponse performPutRequest(String uri, byte[] content) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("PUT");
        request.setRequestURI(uri);
        request.setContentType("application/json");
        request.setContent(content);

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
        return response;
    }

    /**
     * This method performs a DELETE request to the server to delete a specific record.
     *
     * @param uri The uri to which the content will be posted to.
     * @return The response from the server.
     */
    protected MockHttpServletResponse performDeleteRequest(String uri) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("DELETE");
        request.setRequestURI(uri);

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
        return response;
    }

    /**
     * This method creates a new entity for a given entity object.
     *
     * @return The REST query location of the created entity.
     * @throws Exception
     */
    protected <T extends BaseEntity> String createNewEntity(BaseEntity entity, String path) throws Exception {
        byte[] entityBytes = mapper.writeValueAsBytes(entity);
        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/api/".concat(path).concat("/"), entityBytes);
        Assert.assertNotNull(postResponse);
        return getResourceURI(postResponse.getHeader("Location"));
    }

    /**
     * This method creates a new user to test account requests.
     *
     * @param userName  The user name of the created user
     * @param password  The password for the created user.
     * @param firstName The users first name.
     * @param lastName  The users last name.
     * @throws Exception
     */
    protected String createNewUser(String userName, String password, String firstName, String lastName) throws Exception {
        Users user = UserRequestTest.getNewUser(userName, password, firstName, lastName);
        return createNewEntity(user, USERS_PATH);
    }
}
