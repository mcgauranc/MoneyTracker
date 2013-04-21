package com.wraith.repository;

import com.wraith.repository.entity.Account;
import com.wraith.repository.entity.AccountType;
import com.wraith.repository.entity.Currency;
import com.wraith.repository.entity.Users;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 18/04/13
 * Time: 22:00
 */
public class AccountRequestTest extends AbstractBaseIntegrationTests {

    @Test
    public void testCreateAccountRequest() throws Exception {
        String resourceRequest = createNewAccount("Account 1", 12.43, "Banking", "Euro", "EUR");

        //Retrieve the inserted account record from the database, and ensure that values are correct.
        authenticate("Administrator", "Passw0rd");
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Account 1");
        Assert.assertEquals(jsonObject.get("balance"), 12.43);
    }

    @Test
    public void testUpdateAccountRequest() throws Exception {
        String resourceRequest = createNewAccount("Account 2", 12.43, "Banking", "Euro", "EUR");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Account 2");
        jsonObject.put("balance", "1234.56");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        authenticate("Administrator", "Passw0rd");
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Account 2");
        Assert.assertEquals(getJSONObject.get("balance"), 1234.56);
    }

    @Test(expected = Exception.class)
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

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteAccountRequest() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67, "Banking", "Euro", "EUR");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("Administrator", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);

    }

    @Test(expected = Exception.class)
    public void testDeleteAccountRequestWithCUrrentUser() throws Exception {
        String resourceRequest = createNewAccount("Account 4", 1245.67, "Banking", "Euro", "EUR");
        createNewUser("second.person", "Passw0rd", "second", "Person");

        //Delete the inserted account record from the database, and ensure that values are correct.
        authenticate("second.person", "Passw0rd");
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);
    }

    /**
     * This method creates a new user to test account requests.
     *
     * @param userName  The user name of the created user
     * @param password  The password for the created user.
     * @param firstName The users first name.
     * @param lastName  The users last name.
     * @throws Exception
     */
    private void createNewUser(String userName, String password, String firstName, String lastName) throws Exception {
        Users user = UserRequestTest.getNewUser(userName, password, firstName, lastName);
        createNewEntity(user, Users.class);

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
    public String createNewAccount(String name, Double balance, String accountType, String currency, String currencyIso) throws Exception {
        Account account = getNewAccount(name, balance, accountType, currency, currencyIso);
        return createNewEntity(account, Account.class);
    }

    /**
     * This method creates a new account type class.
     *
     * @param name The name of the account type.
     * @return An instance of the Account Type.
     */
    private static AccountType getNewAccountType(String name) {
        AccountType accountType = new AccountType();
        accountType.setName(name);
        return accountType;
    }

    /**
     * This method creates and returns a currency object.
     *
     * @param iso  The ISO code of the currency.
     * @param name The name of the currency.
     * @return An instance of the currency object.
     */
    private static Currency getCurrency(String iso, String name) {
        Currency currency = new Currency();
        currency.setIso(iso);
        currency.setName(name);
        return currency;
    }

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
        account.setBalance(balance);
        account.setType(getNewAccountType(accountType));
        account.setCurrency(getCurrency(currencyIso, currency));
        return account;
    }
}
