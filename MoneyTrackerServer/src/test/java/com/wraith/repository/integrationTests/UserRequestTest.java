package com.wraith.repository.integrationTests;

import com.wraith.repository.entity.Account;
import com.wraith.repository.entity.Users;
import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: rowan.massey
 * Date: 01/04/13
 * Time: 14:09
 */
public class UserRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new user object.
     *
     * @param userName  The users username.
     * @param password  The password for the user.
     * @param firstName The users first name.
     * @param lastName  The users last name
     * @return A user object, containing provided information.
     */
    public static Users getNewUser(String userName, String password, String firstName, String lastName) {
        Users user = new Users();

        user.setUserName(userName);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    @Test
    public void testResponseContentType() throws Exception {
        MockHttpServletResponse response = performGetRequest("/users/");
        Assert.assertTrue(response != null);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
        Assert.assertEquals(response.getContentType(), "application/json");
    }

    @Test
    public void testGetUserRequest() throws Exception {
        String content = performGetRequest("/users/1").getContentAsString();
        Object object = parser.parse(content);
        JSONObject jsonObject = (JSONObject) object;
        Assert.assertEquals((String) jsonObject.get("userName"), "Administrator");
        Assert.assertEquals((String) jsonObject.get("firstName"), "Admin");
        Assert.assertEquals((String) jsonObject.get("lastName"), "User");
    }

    @Test
    public void testCreateUserRequest() throws Exception {
        String resourceRequest = createNewUser("first.person", "Password", "First", "Person");

        //Retrieve the inserted user record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("userName"), "first.person");
        Assert.assertEquals((String) jsonObject.get("firstName"), "First");
        Assert.assertEquals((String) jsonObject.get("lastName"), "Person");
    }

    @Test
    public void testUpdateUserRequest() throws Exception {
        String resourceRequest = createNewUser("second.person", "Passw0rd", "Second", "Person");

        //Update the previously created record.
        authenticate("second.person", "Passw0rd");
        Users updatedUser = getNewUser("second.person", "Passw0rd", "Second_Updated", "Person_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) parser.parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test
    public void testUserUpdateRequestWithAdminPermission() throws Exception {
        String resourceRequest = createNewUser("third.person", "Passw0rd", "Third", "Person");

        //Update the previously created record.
        authenticate("Administrator", "Passw0rd");
        Users updatedUser = getNewUser("third.person", "Passw0rd", "Third_Updated", "Person_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) parser.parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test
    public void testUserUpdateRequestWithCurrentUserPermission() throws Exception {
        String resourceRequest = createNewUser("fourth.person", "Passw0rd", "Fourth", "Person");

        //Update the previously created record.
        authenticate("fourth.person", "Passw0rd");
        Users updatedUser = getNewUser("fourth.person", "Passw0rd", "Fourth_Updated", "Person_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) parser.parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = Exception.class)
    public void testUserUpdateRequestWithOtherUserPermission() throws Exception {
        String resourceRequest = createNewUser("fifth.person", "Passw0rd", "Fifth", "Person");
        createNewUser("sixth.person", "Passw0rd", "Sixth", "Person");

        //Update the previously created record.
        authenticate("sixth.person", "Passw0rd");
        Users updatedUser = getNewUser("Fifth.person", "Passw0rd", "Fifth_Updated", "Person_Updated");
        byte[] updatedUserBytes = mapper.writeValueAsBytes(updatedUser);
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedUserBytes);
        Assert.assertNotNull(putResponse);

        //Retrieve the previously updated user, and ensure the details were updated correctly.
        MockHttpServletResponse getUpdatedResponse = performGetRequest(resourceRequest);
        String updatedContent = getUpdatedResponse.getContentAsString();
        JSONObject updatedJsonObject = (JSONObject) parser.parse(updatedContent);
        Assert.assertEquals((String) updatedJsonObject.get("userName"), updatedUser.getUserName());
        Assert.assertEquals((String) updatedJsonObject.get("firstName"), updatedUser.getFirstName());
        Assert.assertEquals((String) updatedJsonObject.get("lastName"), updatedUser.getLastName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserRequestWithCurrentUserPermission() throws Exception {
        String resourceRequest = createNewUser("seventh.person", "Passw0rd", "Seventh", "Person");

        //Delete the created user.
        authenticate("seventh.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserRequestWithAdminPermission() throws Exception {
        String resourceRequest = createNewUser("eighth.person", "Passw0rd", "Eighth", "Person");

        //Delete the created user.
        authenticate("Administrator", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteUserRequestWithOtherPermission() throws Exception {
        String resourceRequest = createNewUser("ninth.person", "Passw0rd", "Ninth", "Person");
        createNewUser("tenth.person", "Passw0rd", "Tenth", "Person");

        //Delete the created user.
        authenticate("tenth.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted user can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreatedUserAssignedToUserGroup() throws Exception {
        String resourceRequest = createNewUser("twelfth.person", "Passw0rd", "Twelfth", "Person");

        //Retrieve created user from the database.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);

        //Get the groups link for the returned payload.
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject groups = getJsonObjectFromArray("rel", "users.users.groups", links);

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
        createNewUser("thirteenth.person", "Passw0rd", "Thirteenth", "Person");
        createNewUser("fourteenth.person", "Passw0rd", "Fourteenth", "Person");

        //Retrieve created user from the database using search.
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", "thirteenth.person");
        MockHttpServletResponse getResponse = performGetRequest("/users/search/findByUserName", parameters);
        String content = getResponse.getContentAsString();
        Assert.assertNotNull(content);
        JSONObject sixPerson = (JSONObject) parser.parse(content);
        JSONObject sixthUserContent = getJsonObjectFromArray("userName", "thirteenth.person", (JSONArray) sixPerson.get("content"));
        Assert.assertEquals((String) sixthUserContent.get("userName"), "thirteenth.person");

        //Retrieve created user from the database using search.
        parameters.clear();
        parameters.put("username", "fourteenth.person");
        MockHttpServletResponse getSeventhResponse = performGetRequest("/users/search/findByUserName", parameters);
        String seventhPersonContent = getSeventhResponse.getContentAsString();
        Assert.assertNotNull(seventhPersonContent);
        JSONObject seventhUser = (JSONObject) parser.parse(seventhPersonContent);
        JSONObject seventhUserContent = getJsonObjectFromArray("userName", "fourteenth.person", (JSONArray) seventhUser.get("content"));
        Assert.assertEquals((String) seventhUserContent.get("userName"), "fourteenth.person");
    }

    @Test
    public void testUserPasswordIsNotReturned() throws Exception {
        String resourceRequest = createNewUser("fifteenth.person", "Passw0rd", "Fifteenth", "Person");

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertNull(jsonObject.get("password"));
    }

    @Test
    public void testUserCreateRequestWithAccount() throws Exception {
        authenticate("Administrator", "Passw0rd");
        Users user = getNewUser("sixteenth.person", "Passw0rd", "Sixteenth", "Person");

        Set<Account> accounts = new HashSet<>();
        accounts.add(AccountRequestTest.getNewAccount("Current", 12.65, "Banking", "Euro", "EUR"));
        user.setAccounts(accounts);

        String resourceRequest = createNewUser(user);

        //Retrieve the previously create user.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        JSONObject content = (JSONObject) parser.parse(getResponse.getContentAsString());

        //Get the account link for the returned payload.
        JSONArray links = (JSONArray) content.get("links");
        JSONObject account = getJsonObjectFromArray("rel", "users.users.accounts", links);

        //Retrieve the account link, and perform a request to get the information from the database.
        String accountsLink = getResourceURI(account.get("href").toString());
        MockHttpServletResponse getAccountResponse = performGetRequest(accountsLink);
        JSONObject accountObject = (JSONObject) parser.parse(getAccountResponse.getContentAsString());

        //Ensure that the response contains the name Current.
        JSONObject accountName = getJsonObjectFromArray("name", "Current", (JSONArray) accountObject.get("content"));

        Assert.assertEquals((String) accountName.get("name"), "Current");
        Assert.assertEquals(accountName.get("openingBalance"), 12.65);
    }

    /**
     * This method creates a new user for a give user object.
     *
     * @return The REST query location of the created user.
     * @throws Exception
     */
    private String createNewUser(Users user) throws Exception {
        return createNewEntity(user, Users.class);
    }
}

