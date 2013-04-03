package com.wraith.repository;

import com.wraith.repository.entity.Users;
import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 01/04/13
 * Time: 14:09
 */
public class UserRepositoryTest extends BaseIntegrationTests {

    @Test
    public void testResponseContentType() throws Exception {
        MockHttpServletResponse response = performGetRequest("/user/");
        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(response.getContentType(), "application/json");
    }

    @Test
    public void testUserGetRequest() throws Exception {
        String content = performGetRequest("/user/1").getContentAsString();
        Object object = parser.parse(content);
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals((String) jsonObject.get("userName"), "Administrator");
        Assert.assertEquals((String) jsonObject.get("firstName"), "Admin");
        Assert.assertEquals((String) jsonObject.get("lastName"), "User");
    }

    @Test
    public void testUserPostRequest() throws Exception {
        Users user = getNewUser("rowan.massey", "Passw0rd", "Rowan", "Massey");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Retrieve the inserted user record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        Object object = parser.parse(content);
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals((String) jsonObject.get("userName"), "rowan.massey");
        Assert.assertEquals((String) jsonObject.get("firstName"), "Rowan");
        Assert.assertEquals((String) jsonObject.get("lastName"), "Massey");
    }

    @Test
    public void testUserPutRequest() throws Exception {
        Users user = getNewUser("sarah.massey", "Passw0rd", "Sarah", "Massey");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Update the previously created record.
        Users updatedUser = getNewUser("sarah.massey", "Passw0rd", "Sarah_Updated", "Massey_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        Object updatedObject = parser.parse(updatedContent);
        JSONObject updatedJsonObject = (JSONObject) updatedObject;
        Assert.assertEquals((String) updatedJsonObject.get("userName"), "sarah.massey");
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUserDeleteRequest() throws Exception {
        Users user = getNewUser("samuel.massey", "Passw0rd", "Samuel", "Massey");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Delete the created user.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreatedUserPasswordEncoding() throws Exception {
        Users user = getNewUser("jim.massey", "Passw0rd", "Jim", "Massey");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        Object object = parser.parse(content);

        //Encode the password for the created user
        Encoding encoding = new Encoding();
        String password = encoding.encodePassword("Passw0rd", "jim.massey");

        //Ensure that the password retrieved from the database, and the one encoded match.
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals((String) jsonObject.get("password"), password);
    }

    @Test
    public void testCreatedUserAssignedToUserGroup() throws Exception {
        Users user = getNewUser("scott.fell", "Passw0rd", "Scott", "Fell");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        Object object = parser.parse(content);

        //Get the groups link for the returned payload.
        JSONObject jsonObject = (JSONObject) object;
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject groups = getJsonObjectFromArray("rel", "user.users.groups", links);

        //Retrieve the groups link, and perform a request to get the information from the database.
        String groupsLink = getResourceURI(groups.get("href").toString());
        MockHttpServletResponse getGroupResponse = performGetRequest(groupsLink);
        String groupsContent = getGroupResponse.getContentAsString();
        Object groupObject = parser.parse(groupsContent);

        //Ensure that the response contains the name users.
        JSONObject jsonGroupObject = (JSONObject) groupObject;
        JSONObject groupContent = getJsonObjectFromArray("name", "Users", (JSONArray) jsonGroupObject.get("content"));

        Assert.assertNotNull(groupContent);
    }

    //TODO: Test custom searches.

    /**
     * This method creates a new user object.
     *
     * @param userName  The users username.
     * @param password  The password for the user.
     * @param firstName The users first name.
     * @param lastName  The users last name
     * @return A user object, containing provided information.
     */
    private Users getNewUser(String userName, String password, String firstName, String lastName) {
        Users user = new Users();

        user.setUserName(userName);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }
}
