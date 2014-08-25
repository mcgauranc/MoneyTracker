package com.wraith.repository.integrationTests;

import junit.framework.Assert;
import net.minidev.json.JSONObject;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import com.wraith.repository.entity.AccountType;

/**
 * User: rowan.massey
 * Date: 23/04/13
 * Time: 19:19
 */
public class AccountTypeRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new account type class.
     *
     * @param name The name of the account type.
     * @return An instance of the Account Type.
     */
    public static AccountType getNewAccountType(String name) {
        AccountType accountType = new AccountType();
        accountType.setName(name);
        return accountType;
    }

    @Test
    public void testCreateAccountTypeRequest() throws Exception {
        String resourceRequest = createNewAccountType("Banking 1");

        //Retrieve the inserted account record from the database, and ensure that values are correct.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Banking 1");
    }

    @Test
    public void testUpdateAccountTypeRequest() throws Exception {
        String resourceRequest = createNewAccountType("Banking 2");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Banking 2");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Banking 2");
    }

    @Test(expected = Exception.class)
    public void testUpdateAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccountType("Banking 3");
        createNewUser("first.person", "Passw0rd", "first", "Person");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Banking 3");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        authenticate("first.person", "Passw0rd");
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);
    }

    @Test
    public void testDeleteAccountRequest() throws Exception {
        String resourceRequest = createNewAccountType("Banking 4");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest, null, HttpStatus.NOT_FOUND);
    }

    @Test(expected = Exception.class)
    public void testDeleteAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccountType("Banking 5");
        createNewUser("second.person", "Passw0rd", "second", "Person");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("second.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);
    }

    /**
     * This method creates a new account, and returns the accounts reference URI
     *
     * @param name The name of the account.
     * @return The reference URI for the created account.
     * @throws Exception
     */
    public String createNewAccountType(String name) throws Exception {
        AccountType accountType = getNewAccountType(name);
        return createNewEntity(accountType, ACCOUNT_TYPES_PATH);
    }

}
