package com.wraith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wraith.repository.ApplicationConfig;
import com.wraith.repository.ApplicationRestConfig;
import com.wraith.repository.RestExporterWebInitializer;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * User: rowan.massey
 * Date: 30/03/13
 * Time: 00:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class, ApplicationRestConfig.class, RestExporterWebInitializer.class})
public class RepositoryRestConfigurationIntegrationTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ObjectMapper mapper;
    private JSONParser parser;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        mapper = new ObjectMapper();
        parser = new JSONParser();
    }

    @Test
    public void testReponseContentType() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/user/");

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);

        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(response.getContentType(), "application/json");
    }

    @Test
    public void testUserGetRequest() throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/user/1");

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());

        Object object = parser.parse(response.getContentAsString());
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals((String) jsonObject.get("userName"), "Administrator");
        Assert.assertEquals((String) jsonObject.get("firstName"), "Admin");
        Assert.assertEquals((String) jsonObject.get("lastName"), "User");
    }

//    @Test
//    public void testUserPostRequest() throws Exception {
//        Users user = getNewUser("rowan.massey", "Passw0rd", "Rowan", "Massey");
//
//        OutputStream stream = new ByteArrayOutputStream();
//        mapper.writeValue(stream, user);
//
//        request.setMethod("POST");
//        request.setRequestURI("/user/");
//        request.setContentType("application/json");
//        request.setContent(stream.toString().getBytes());
//
//        Object handler = handlerMapping.getHandler(request).getHandler();
//        handlerAdapter.handle(request, response, handler);
//        Assert.assertTrue(response != null);
//        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
//    }
//
//    private Users getNewUser(String userName, String password, String firstName, String lastName) {
//        Users user = new Users();
//        user.setUserName(userName);
//        user.setPassword(password);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//
//        return user;
//    }
}
