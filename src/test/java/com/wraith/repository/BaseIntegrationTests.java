package com.wraith.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
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

import java.util.Map;

/**
 * User: rowan.massey
 * Date: 30/03/13
 * Time: 00:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {ApplicationConfig.class, ApplicationRestConfig.class, RestExporterWebInitializer.class})
public class BaseIntegrationTests {

    private final String LOCALHOST = "http://localhost";

    protected ObjectMapper mapper;
    protected JSONParser parser;

    @Autowired
    protected RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    protected RequestMappingHandlerMapping handlerMapping;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        parser = new JSONParser();
    }

    /**
     * This method strips the 'http://localhost' from the provided uri and returns just the resource request.
     * e.g. /user/1
     *
     * @param uri The uri from which to remove the localhost string.
     * @return A resource uri. e.g. /user/1
     */
    protected String getResourceURI(String uri) {
        return StringUtils.remove(uri, LOCALHOST);
    }

    /**
     * This method performs a GET request to the server to retrieve records, along with the associated parameters - if any.
     *
     * @param uri        The uri on which the request will be performed.
     * @param parameters The parameters that will get passed to the request.
     * @return The response object from the server.
     */
    protected MockHttpServletResponse performGetRequest(String uri, Map parameters) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");
        request.setRequestURI(uri);

        if (parameters != null) {
            request.setParameters(parameters);
        }

        Object handler = handlerMapping.getHandler(request).getHandler();
        handlerAdapter.handle(request, response, handler);
        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
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
        request.setContentType("application/json");
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

}
