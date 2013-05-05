package com.wraith.repository;

import com.wraith.repository.entity.Authorities;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 01/05/13
 * Time: 19:49
 */
public class AuthorityRequestTest extends AbstractBaseIntegrationTests {

    @Test(expected = Exception.class)
    public void testCreateAuthorityWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewAuthority("ROLE_TESTER");
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateAuthorityRequest() throws Exception {
        authenticate("Administrator", "Passw0rd");

        String resourceRequest = createNewAuthority("ROLE_REPORTER");

        //Retrieve the inserted authority record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("authority"), "ROLE_REPORTER");
    }

    @Test(expected = Exception.class)
    public void testCreateAuthorityWithOrdinaryUserRequest() throws Exception {
        createNewUser("twentieth.person", "Passw0rd", "Twentieth", "Person");
        authenticate("twentieth.person", "Passw0rd");

        createNewAuthority("ROLE_REPORTER");

    }

    @Test
    public void testUpdateAuthorityRequest() throws Exception {
        authenticate("Administrator", "Passw0rd");

        String resourceRequest = createNewAuthority("ROLE_DEVELOPER");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authority", "UPDATED_ROLE_DEVELOPER");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("authority"), "UPDATED_ROLE_DEVELOPER");
    }

    @Test(expected = Exception.class)
    public void testUpdateAuthorityWithOrdinaryUserRequest() throws Exception {
        authenticate("Administrator", "Passw0rd");
        String resourceRequest = createNewAuthority("ROLE_DEVELOPER");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authority", "UPDATED_ROLE_DEVELOPER");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        createNewUser("twentyfirst.person", "Passw0rd", "Twenty First", "Person");
        authenticate("twentyfirst.person", "Passw0rd");
        //Update the inserted account record from the database, and ensure that values are correct.
        performPutRequest(resourceRequest, updatedAccountBytes);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteAuthorityRequest() throws Exception {
        authenticate("Administrator", "Passw0rd");

        String resourceRequest = createNewAuthority("ROLE_SUPPORTER");

        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteAuthorityWithOrdinaryUserRequest() throws Exception {
        authenticate("Administrator", "Passw0rd");

        String resourceRequest = createNewAuthority("ROLE_SUPPORTER");

        createNewUser("twentysecond.person", "Passw0rd", "Twenty Second", "Person");
        authenticate("twentysecond.person", "Passw0rd");

        //Delete the inserted account record from the database, and ensure that values are correct.
        performDeleteRequest(resourceRequest);
    }

    /**
     * This method creates a new authority in the database, and returns the URI location.
     *
     * @param authorityName The name of the authority.
     * @return The location of the created authority.
     * @throws Exception
     */
    private String createNewAuthority(String authorityName) throws Exception {
        Authorities authority = getNewAuthority(authorityName);
        return createNewEntity(authority, Authorities.class);
    }

    /**
     * This method returns a new instance of the created authority.
     *
     * @param authorityName The name of the authority.
     * @return An instance of the Authorities class for the given authority name.
     */
    public static Authorities getNewAuthority(String authorityName) {
        Authorities authorities = new Authorities();
        authorities.setAuthority(authorityName);
        return authorities;
    }
}
