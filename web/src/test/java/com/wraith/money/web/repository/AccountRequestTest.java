package com.wraith.money.web.repository;

import com.wraith.money.data.Account;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

/**
 * User: rowan.massey
 * Date: 18/04/13
 * Time: 22:00
 */
public class AccountRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new instance of the account object.
     *
     * @param name    The name of the account.
     * @param balance The account balance.
     * @return An instance of the account object.
     */
    public static Account getNewAccount(String name, Double balance) {
        Account account = new Account();
        account.setName(name);
        account.setOpeningBalance(balance);
        return account;
    }

    @Test
    public void testCreateAccountRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        //Create a new currency.
        String currencyLocation = createNewEntity(CurrencyRequestTest.getCurrency("EUR", "Euro"), CURRENCY_PATH);

        //Create a new account type.
        String accountTypeLocation = createNewEntity(AccountTypeRequestTest.getNewAccountType("Current"), ACCOUNT_TYPES_PATH);

        //Create a new account.
        String accountLocation = createNewAccount("Account 1", 12.43);

        //Associate the created currency with the account.
        MockHttpServletResponse accountUpdateResponse = performPutRequest(accountLocation.concat("/").concat("currency"), "text/uri-list", currencyLocation.getBytes());
        Assert.assertEquals(accountUpdateResponse.getStatus(), HttpStatus.NO_CONTENT.value());

        //Associate the created account type with the account.
//        MockHttpServletResponse accountTypeUpdateResponse = performPutRequest(accountLocation.concat("/").concat("accountTypes"), "text/uri-list", accountTypeLocation.getBytes());
//        Assert.assertEquals(accountTypeUpdateResponse.getStatus(), HttpStatus.NO_CONTENT.value());

        //Retrieve the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse accountResponse = performGetRequest(accountLocation);
        String content = accountResponse.getContentAsString();
        JSONObject accountContent = (JSONObject) parser.parse(content);

        Assert.assertEquals(accountContent.get("name"), "Account 1");
        Assert.assertEquals(accountContent.get("openingBalance"), 12.43);

        String accountCurrencyLocation = ((HashMap) ((HashMap) accountContent.get("_links")).get("currency")).get("href").toString();

        MockHttpServletResponse accountCurrencyResponse = performGetRequest(accountCurrencyLocation);
        String currencyContent = accountCurrencyResponse.getContentAsString();
        JSONObject currencyResponseObject = (JSONObject) parser.parse(currencyContent);

        Assert.assertTrue(currencyResponseObject.get("name").toString().equalsIgnoreCase("Euro"));
        Assert.assertTrue(currencyResponseObject.get("iso").toString().equalsIgnoreCase("EUR"));
    }

    @Test
    public void testUpdateAccountRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAccount("Account 2", 12.43);

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

    @Test(expected = Exception.class)
    public void testUpdateAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccount("Account 3", 12.43);
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

    @Test(expected = Exception.class)
    public void testDeleteAccountRequest() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67);

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("Admin", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);

    }

    @Test(expected = Exception.class)
    public void testDeleteAccountRequestWithCurrentUser() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67);
        createNewUser("second.person", "Passw0rd", "second", "Person");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("second.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);
    }

    /**
     * This method creates a new account, and returns the accounts reference URI
     *
     * @param name    The name of the account.
     * @param balance The account balance.
     * @return The reference URI for the created account.
     * @throws Exception
     */
    private String createNewAccount(String name, Double balance) throws Exception {
        Account account = getNewAccount(name, balance);
        return createNewEntity(account, ACCOUNTS_PATH);
    }
}
