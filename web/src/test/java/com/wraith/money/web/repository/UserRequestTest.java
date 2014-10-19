package com.wraith.money.web.repository;

import com.wraith.money.data.Users;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * User: rowan.massey Date: 01/04/13 Time: 14:09
 */
public class UserRequestTest extends AbstractBaseIntegrationTests {

    @Test
    public void testResponseContentType() throws Exception {
        MockHttpServletResponse response = entityRepositoryHelper.getEntity("/users/");
        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(response.getContentType(), "application/hal+json");
    }

    @Test
    public void testGetUserRequest() throws Exception {
        String content = entityRepositoryHelper.getEntity("/users/1").getContentAsString();
        Object object = entityRepositoryHelper.getParser().parse(content);
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals(jsonObject.get("userName"), "Administrator");
        Assert.assertEquals(jsonObject.get("firstName"), "Admin");
        Assert.assertEquals(jsonObject.get("lastName"), "User");
    }

    @Test
    public void testCreateUserRequest() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("first.person", "Password", "First", "Person");

        //Retrieve the inserted user record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(content);
        Assert.assertEquals(jsonObject.get("userName"), "first.person");
        Assert.assertEquals(jsonObject.get("firstName"), "First");
        Assert.assertEquals(jsonObject.get("lastName"), "Person");
    }

    @Test
    public void testUpdateUserRequest() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("second.person", "Passw0rd", "Second", "Person");

        //Update the previously created record.
        authenticate("second.person", "Passw0rd");
        Users updatedUser = null;//getUser("second.person", "Passw0rd", "Second_Updated", "Person_Updated");
        byte[] updatedUserBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test
    public void testUserUpdateRequestWithAdminPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("third.person", "Passw0rd", "Third", "Person");

        //Update the previously created record.
        authenticate("Admin", "Passw0rd");
        Users updatedUser = null;//getUser("third.person", "Passw0rd", "Third_Updated", "Person_Updated");
        byte[] updatedUserBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test
    public void testUserUpdateRequestWithCurrentUserPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("fourth.person", "Passw0rd", "Fourth", "Person");

        //Update the previously created record.
        authenticate("fourth.person", "Passw0rd");
        Users updatedUser = null; //getUser("fourth.person", "Passw0rd", "Fourth_Updated", "Person_Updated");
        byte[] updatedUserBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = Exception.class)
    public void testUserUpdateRequestWithOtherUserPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("fifth.person", "Passw0rd", "Fifth", "Person");
        entityRepositoryHelper.createUser("sixth.person", "Passw0rd", "Sixth", "Person");

        //Update the previously created record.
        authenticate("sixth.person", "Passw0rd");
        Users updatedUser = null;//getUser("Fifth.person", "Passw0rd", "Fifth_Updated", "Person_Updated");
        byte[] updatedUserBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserRequestWithCurrentUserPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("seventh.person", "Passw0rd", "Seventh", "Person");

        //Delete the created user.
        authenticate("seventh.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = entityRepositoryHelper.deleteEntity(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        entityRepositoryHelper.getEntity(resourceRequest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserRequestWithAdminPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("eighth.person", "Passw0rd", "Eighth", "Person");

        //Delete the created user.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse deleteResponse = entityRepositoryHelper.deleteEntity(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        entityRepositoryHelper.getEntity(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteUserRequestWithOtherPermission() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("ninth.person", "Passw0rd", "Ninth", "Person");
        entityRepositoryHelper.createUser("tenth.person", "Passw0rd", "Tenth", "Person");

        //Delete the created user.
        authenticate("tenth.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = entityRepositoryHelper.deleteEntity(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        entityRepositoryHelper.getEntity(resourceRequest);
    }

    @Test
    public void testCreatedUserAssignedToUserGroup() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("twelfth.person", "Passw0rd", "Twelfth", "Person");

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(content);

        //Get the groups link for the returned payload.
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject groups = entityRepositoryHelper.getJsonObjectFromArray("rel", "users.users.groups", links);

        //Retrieve the groups link, and perform a request to get the information from the database.
        String groupsLink = groups.get("href").toString();
        MockHttpServletResponse getGroupResponse = entityRepositoryHelper.getEntity(groupsLink);
        String groupsContent = getGroupResponse.getContentAsString();
        JSONObject jsonGroupObject = (JSONObject) entityRepositoryHelper.getParser().parse(groupsContent);

        //Ensure that the response contains the name users.
        JSONObject groupContent = entityRepositoryHelper.getJsonObjectFromArray("name", "Users", (JSONArray) jsonGroupObject.get("content"));

        Assert.assertNotNull(groupContent);
    }

    @Test
    public void testFindUserByUserName() throws Exception {
        entityRepositoryHelper.createUser("thirteenth.person", "Passw0rd", "Thirteenth", "Person");
        entityRepositoryHelper.createUser("fourteenth.person", "Passw0rd", "Fourteenth", "Person");

        //Retrieve created user from the database using search.
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", "thirteenth.person");
        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity("/users/search/findByUserName", parameters);
        String content = getResponse.getContentAsString();
        Assert.assertNotNull(content);
        JSONObject sixPerson = (JSONObject) entityRepositoryHelper.getParser().parse(content);
        JSONObject sixthUserContent = entityRepositoryHelper.getJsonObjectFromArray("userName", "thirteenth.person", (JSONArray) sixPerson.get("content"));
        Assert.assertEquals((String) sixthUserContent.get("userName"), "thirteenth.person");

        //Retrieve created user from the database using search.
        parameters.clear();
        parameters.put("username", "fourteenth.person");
        MockHttpServletResponse getSeventhResponse = entityRepositoryHelper.getEntity("/users/search/findByUserName", parameters);
        String seventhPersonContent = getSeventhResponse.getContentAsString();
        Assert.assertNotNull(seventhPersonContent);
        JSONObject seventhUser = (JSONObject) entityRepositoryHelper.getParser().parse(seventhPersonContent);
        JSONObject seventhUserContent = entityRepositoryHelper.getJsonObjectFromArray("userName", "fourteenth.person", (JSONArray) seventhUser.get("content"));
        Assert.assertEquals(seventhUserContent.get("userName"), "fourteenth.person");
    }

    @Test
    public void testUserPasswordIsNotReturned() throws Exception {
        String resourceRequest = entityRepositoryHelper.createUser("fifteenth.person", "Passw0rd", "Fifteenth", "Person");

        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) entityRepositoryHelper.getParser().parse(content);
        Assert.assertNull(jsonObject.get("password"));
    }
}