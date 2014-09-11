package com.wraith.repository.integrationTests;

import com.wraith.repository.entity.*;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Calendar;

/**
 * User: rowan.massey
 * Date: 12/05/13
 * Time: 00:04
 */
public class TransactionRequestTests extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new instance of a transaction for the given details.
     *
     * @param amount       The name of the account to which this transaction belongs.
     * @param category     The category of the transaction.
     * @param chequeNumber The transactions cheque number - if any.
     * @param currency     The transaction currency.
     * @param notes        Any extra notes associated with the transaction.
     * @param payee        The name of the payee for this transaction.
     * @param quantity     The quantity of the transaction.
     * @param user         The user associated with this transaction.
     * @return The URI to the created transaction.
     * @throws Exception
     */
    public static Transaction getNewTransaction(Account account, Double amount, Category category, String chequeNumber, Currency currency,
                                                String notes, Payee payee, Integer quantity, Users user) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setCheckNumber(chequeNumber);
        transaction.setCurrency(currency);
        transaction.setNotes(notes);
        transaction.setNumber(0);
        transaction.setPayee(payee);
        transaction.setQuantity(quantity);
        transaction.setTransactionDate(Calendar.getInstance().getTime());
        transaction.setUser(user);

        return transaction;
    }

    @Test(expected = Exception.class)
    public void testCreatePayeeWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceLocation = createNewTransaction("Current", 12.65, "Toothpaste", "12345", "Euro", "EUR", "This is the note",
                "Superquinn", 1, "");
        performGetRequest(resourceLocation);
    }

    @Test
    public void testCreateTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = createNewTransaction("Credit Card", 12.65, "Toothpaste", "12345", "Euro", "EUR", "This is the note",
                "Superquinn", 1, "Administrator");
        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 12.65);
    }

    @Test
    public void testCreateTransactionWithNormalUser() throws Exception {
        createNewUser("eightieth.person", "Passw0rd", "Eightieth", "Person");
        authenticate("eightieth.person", "Passw0rd");

        String resourceLocation = createNewTransaction("Current", 1245.65, "Wine", "", "Euro", "EUR", "This is the Wine note",
                "Superquinn", 1, "eightieth.person");
        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 1245.65);
    }

    @Test
    public void testUpdateTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = createNewTransaction("Wallet", 65.54, "Chocolate", "", "Euro", "EUR", "This is the Chocolate bar",
                "Spar", 1, "Administrator");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 154.65);
        byte[] updatedTransactionBytes = mapper.writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceLocation, updatedTransactionBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 154.65);
    }

    @Test
    public void testUpdateTransactionWithNormalUser() throws Exception {
        createNewUser("eightyfirst.person", "Passw0rd", "Eighty First", "Person");
        authenticate("eightyfirst.person", "Passw0rd");

        String resourceLocation = createNewTransaction("Wallet", 12.54, "Beer", "", "Euro", "EUR", "This is a six pack",
                "Spar", 1, "eightyfirst.person");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 6.00);
        byte[] updatedTransactionBytes = mapper.writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceLocation, updatedTransactionBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 6.00);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = createNewTransaction("Wallet", 0.85, "Crisps", "", "Euro", "EUR", "This is a bag of crisps",
                "Spar", 1, "Administrator");

        //Delete the inserted transaction record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceLocation);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceLocation);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteTransactionWithNormalUser() throws Exception {
        createNewUser("eightysecond.person", "Passw0rd", "Eighty Second", "Person");
        authenticate("eightysecond.person", "Passw0rd");

        String resourceLocation = createNewTransaction("Wallet", 50.00, "Money Found", "", "Euro", "EUR", "Found money on street",
                "Unknown", 1, "eightysecond.person");

        //Delete the inserted transaction record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceLocation);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceLocation);
    }

    /**
     * This method creates a new transaction for the given details.
     *
     * @param amount       The name of the account to which this transaction belongs.
     * @param categoryName The category of the transaction.
     * @param chequeNumber The transactions cheque number - if any.
     * @param currencyName The transaction currency.
     * @param currencyISO  The transaction currency's ISO name.
     * @param notes        Any extra notes associated with the transaction.
     * @param payeeName    The name of the payee for this transaction.
     * @param quantity     The quantity of the transaction.
     * @param userName     The user associated with this transaction.
     * @return The URI to the created transaction.
     * @throws Exception
     */
    private String createNewTransaction(String accountName, Double amount, String categoryName, String chequeNumber, String currencyName, String currencyISO,
                                        String notes, String payeeName, Integer quantity, String userName) throws Exception {
        Users user = getUser(userName);
        Account account = getAccount(accountName, 1234.56, "Current", "Euro", "EUR");
        Category category = getCategory(categoryName);
        Currency currency = getCurrency(currencyName, currencyISO);
        Payee payee = getPayee(payeeName);

        Transaction transaction = getNewTransaction(account, amount, category, chequeNumber, currency, notes, payee, quantity, user);
        return createNewEntity(transaction, TRANSACTIONS_PATH);
    }

    /**
     * This method searches for an existing account, if not found, it creates a new one and returns it.
     *
     * @param accountName    The name of the account.
     * @param accountBalance The opening balance of the account.
     * @param accountType    The type of the account.
     * @param currencyISO    The currency ISO for the account.
     * @param currencyName   The name of the currency for the account.
     * @return An instance of the found or created account.
     */
    private Account getAccount(String accountName, Double accountBalance, String accountType, String currencyName, String currencyISO) {
        if (accountRepository.findByName(accountName).size() == 0) {
            //Create a new account with default values, and return it from the database.
            Account account = AccountRequestTest.getNewAccount(accountName, accountBalance, accountType, currencyName, currencyISO);
            return accountRepository.save(account);
        } else {
            return accountRepository.findByName(accountName).get(0);
        }
    }

    /**
     * This method searches for an existing category, if not found, it creates a new one and returns it.
     *
     * @param categoryName The name of the category.
     * @return An instance of the found or created category.
     */
    private Category getCategory(String categoryName) {
        if (categoryRepository.findByName(categoryName).size() == 0) {
            //Create a new category and return it from the database.
            Category category = CategoryRequestTest.getNewCategory(categoryName, null);
            return categoryRepository.save(category);
        } else {
            return categoryRepository.findByName(categoryName).get(0);
        }
    }

    /**
     * This method searches for an existing currency, if not found, it creates a new one and returns it.
     *
     * @param currencyName The name of the currency.
     * @param currencyISO  THe currencies ISO code.
     * @return An instance of the found or created currency.
     */
    private Currency getCurrency(String currencyName, String currencyISO) {
        if (currencyRepository.findByName(currencyName).size() == 0) {
            //Create a new currency and return it from the database.
            Currency currency = CurrencyRequestTest.getCurrency(currencyISO, currencyName);
            return currencyRepository.save(currency);
        } else {
            return currencyRepository.findByName(currencyName).get(0);
        }
    }

    /**
     * This method searches for an existing payee, if not found, it creates a new one and returns it.
     *
     * @param payeeName The name of the payee.
     * @return An instance of the found or created payee.
     */
    private Payee getPayee(String payeeName) {
        if (payeeRepository.findByName(payeeName).size() == 0) {
            //Create a new payee and return it from the database.
            Payee payee = PayeeRequestTests.getNewPayee(payeeName);
            return payeeRepository.save(payee);
        } else {
            return payeeRepository.findByName(payeeName).get(0);
        }
    }

    /**
     * This method searches for an existing user, and returns it.
     *
     * @param userName The username of the user.
     * @return An instance of the found user.
     */
    private Users getUser(String userName) {
        return usersRepository.findByUserName(userName).get(0);
    }

}
