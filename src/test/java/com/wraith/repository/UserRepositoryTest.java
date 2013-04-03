package com.wraith.repository;

import com.wraith.repository.entity.Users;
import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

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
        Users user = getNewUser("first.person", "Passw0rd", "First", "Person");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Retrieve the inserted user record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("userName"), "first.person");
        Assert.assertEquals((String) jsonObject.get("firstName"), "First");
        Assert.assertEquals((String) jsonObject.get("lastName"), "Person");
    }

    @Test
    public void testUserPutRequest() throws Exception {
        Users user = getNewUser("second.person", "Passw0rd", "Second", "Person");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Update the previously created record.
        Users updatedUser = getNewUser("second.person", "Passw0rd", "Second_Updated", "Person_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) parser.parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), "second.person");
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUserDeleteRequest() throws Exception {
        Users user = getNewUser("third.person", "Passw0rd", "Third", "Person");
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
        Users user = getNewUser("fourth.person", "Passw0rd", "Fourth", "Person");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Encode the password for the created user
        Encoding encoding = new Encoding();
        String password = encoding.encodePassword("Passw0rd", "fourth.person");

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);

        //Ensure that the password retrieved from the database, and the one encoded match.
        Assert.assertEquals((String) jsonObject.get("password"), password);
    }

    @Test
    public void testCreatedUserAssignedToUserGroup() throws Exception {
        Users user = getNewUser("fifth.person", "Passw0rd", "Fifth", "Person");
        byte[] userBytes = mapper.writeValueAsBytes(user);

        //Insert new user record.
        MockHttpServletResponse postResponse = performPostRequest("/user/", userBytes);
        Assert.assertNotNull(postResponse);
        String resourceRequest = getResourceURI(postResponse.getHeader("Location"));

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);

        //Get the groups link for the returned payload.
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject groups = getJsonObjectFromArray("rel", "user.users.groups", links);

        //Retrieve the groups link, and perform a request to get the information from the database.
        String groupsLink = getResourceURI(groups.get("href").toString());
        MockHttpServletResponse getGroupResponse = performGetRequest(groupsLink);
        String groupsContent = getGroupResponse.getContentAsString();
        JSONObject jsonGroupObject = (JSONObject) parser.parse(groupsContent);

        //Ensure that the response contains the name users.
        JSONObject groupContent = getJsonObjectFromArray("name", "Users", (JSONArray) jsonGroupObject.get("content"));

        Assert.assertNotNull(groupContent);
    }

    @Test
    public void testFindUserByUserName() throws Exception {
        Users sixthPerson = getNewUser("sixth.person", "Passw0rd", "Sixth", "Person");
        byte[] sixthUserBytes = mapper.writeValueAsBytes(sixthPerson);
        //Insert new user record.
        performPostRequest("/user/", sixthUserBytes);

        Users seventhPerson = getNewUser("seventh.person", "Passw0rd", "Seventh", "Person");
        byte[] seventhUserBytes = mapper.writeValueAsBytes(seventhPerson);
        //Insert new user record.
        performPostRequest("/user/", seventhUserBytes);

        //Retrieve created user from the database using search.
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", "sixth.person");
        MockHttpServletResponse getResponse = performGetRequest("/user/search/findByUserName", parameters);
        String content = getResponse.getContentAsString();
        Assert.assertNotNull(content);
        JSONObject sixPerson = (JSONObject) parser.parse(content);
        JSONObject sixthUserContent = getJsonObjectFromArray("userName", "sixth.person", (JSONArray) sixPerson.get("content"));
        Assert.assertEquals((String) sixthUserContent.get("userName"), "sixth.person");

        //Retrieve created user from the database using search.
        parameters.clear();
        parameters.put("username", "seventh.person");
        MockHttpServletResponse getSeventhResponse = performGetRequest("/user/search/findByUserName", parameters);
        String seventhPersonContent = getSeventhResponse.getContentAsString();
        Assert.assertNotNull(seventhPersonContent);
        JSONObject seventhUser = (JSONObject) parser.parse(seventhPersonContent);
        JSONObject seventhUserContent = getJsonObjectFromArray("userName", "seventh.person", (JSONArray) seventhUser.get("content"));
        Assert.assertEquals((String) seventhUserContent.get("userName"), "seventh.person");
    }


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
