package com.wraith.repository.integrationTests;

import com.wraith.repository.entity.Account;
import net.minidev.json.JSONObject;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.wraith.repository.integrationTests.CurrencyRequestTest.getCurrency;

/**
 * User: rowan.massey
 * Date: 18/04/13
 * Time: 22:00
 */
public class AccountRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new instance of the account object.
     *
     * @param name        The name of the account.
     * @param balance     The account balance.
     * @param accountType The type of the account.
     * @param currency    The accounts currency.
     * @param currencyIso The ISO currency of the account.
     * @return An instance of the account object.
     */
    public static Account getNewAccount(String name, Double balance, String accountType, String currency, String currencyIso) {
        Account account = new Account();
        account.setName(name);
        account.setOpeningBalance(balance);
        account.setType(AccountTypeRequestTest.getNewAccountType(accountType));
        account.setCurrency(getCurrency(currencyIso, currency));
        return account;
    }

    @Test
    public void testCreateAccountRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAccount("Account 1", 12.43, "Banking", "Euro", "EUR");

        //Retrieve the inserted account record from the database, and ensure that values are correct.

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        org.junit.Assert.assertEquals(jsonObject.get("name"), "Account 1");
        Assert.assertEquals(jsonObject.get("openingBalance"), 12.43);
    }

    @Test
    public void testUpdateAccountRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAccount("Account 2", 12.43, "Banking", "Euro", "EUR");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Account 2");
        jsonObject.put("openingBalance", "1234.56");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals(getJSONObject.get("name"), "Updated Account 2");
        Assert.assertEquals(getJSONObject.get("openingBalance"), 1234.56);
    }

    @Test(expectedExceptions = Exception.class)
    public void testUpdateAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccount("Account 3", 12.43, "Banking", "Euro", "EUR");
        createNewUser("first.person", "Passw0rd", "first", "Person");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Account 3");
        jsonObject.put("balance", "1234.56");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        authenticate("first.person", "Passw0rd");
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void testDeleteAccountRequest() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67, "Banking", "Euro", "EUR");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);

    }

    @Test(expectedExceptions = Exception.class)
    public void testDeleteAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67, "Banking", "Euro", "EUR");
        createNewUser("second.person", "Passw0rd", "second", "Person");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("second.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);
    }

    /**
     * This method creates a new account, and returns the accounts reference URI
     *
     * @param name        The name of the account.
     * @param balance     The account balance.
     * @param accountType The type of the account.
     * @param currency    The accounts currency.
     * @param currencyIso The ISO currency of the account.
     * @return The reference URI for the created account.
     * @throws Exception
     */
    private String createNewAccount(String name, Double balance, String accountType, String currency, String currencyIso) throws Exception {
        Account account = getNewAccount(name, balance, accountType, currency, currencyIso);
        return createNewEntity(account, ACCOUNTS_PATH);
    }
}
