package com.wraith.money.web.repository;

import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey Date: 12/05/13 Time: 00:04
 */
public class TransactionRequestTests extends AbstractBaseIntegrationTests {

    @Test(expected = Exception.class)
    public void testCreatePayeeWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceLocation = entityRepositoryHelper.createTransaction("Current", 12.65, "Toothpaste", "12345", "Euro", "EUR",
                "This is the note", "Superquinn", 1, "");
        entityRepositoryHelper.getEntity(resourceLocation);
    }

    @Test
    public void testCreateTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Credit Card", 12.65, "Toothpaste", "12345", "Euro", "EUR",
                "This is the note", "Superquinn", 1, "Admin");
        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) entityRepositoryHelper.getParser().parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 12.65);
    }

    @Test
    public void testCreateTransactionWithNormalUser() throws Exception {
        entityRepositoryHelper.createUser("eightieth.person", "Passw0rd", "Eightieth", "Person");
        authenticate("eightieth.person", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Current", 1245.65, "Wine", "", "Euro", "EUR", "This is the Wine note",
                "Superquinn", 1, "eightieth.person");
        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) entityRepositoryHelper.getParser().parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 1245.65);
    }

    @Test
    public void testUpdateTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Wallet", 65.54, "Chocolate", "", "Euro", "EUR",
                "This is the Chocolate bar", "Spar", 1, "Administrator");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 154.65);
        byte[] updatedTransactionBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceLocation, updatedTransactionBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) entityRepositoryHelper.getParser().parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 154.65);
    }

    @Test
    public void testUpdateTransactionWithNormalUser() throws Exception {
        entityRepositoryHelper.createUser("eightyfirst.person", "Passw0rd", "Eighty First", "Person");
        authenticate("eightyfirst.person", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Wallet", 12.54, "Beer", "", "Euro", "EUR", "This is a six pack", "Spar",
                1, "eightyfirst.person");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", 6.00);
        byte[] updatedTransactionBytes = entityRepositoryHelper.getMapper().writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = entityRepositoryHelper.updateEntity(resourceLocation, updatedTransactionBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = entityRepositoryHelper.getEntity(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) entityRepositoryHelper.getParser().parse(getContent);
        Assert.assertEquals(getJSONObject.get("amount"), 6.00);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteTransactionWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Wallet", 0.85, "Crisps", "", "Euro", "EUR", "This is a bag of crisps",
                "Spar", 1, "Administrator");

        //Delete the inserted transaction record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = entityRepositoryHelper.deleteEntity(resourceLocation);
        Assert.assertNotNull(putResponse);

        entityRepositoryHelper.getEntity(resourceLocation);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteTransactionWithNormalUser() throws Exception {
        entityRepositoryHelper.createUser("eightysecond.person", "Passw0rd", "Eighty Second", "Person");
        authenticate("eightysecond.person", "Passw0rd");

        String resourceLocation = entityRepositoryHelper.createTransaction("Wallet", 50.00, "Money Found", "", "Euro", "EUR",
                "Found money on street", "Unknown", 1, "eightysecond.person");

        //Delete the inserted transaction record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = entityRepositoryHelper.deleteEntity(resourceLocation);
        Assert.assertNotNull(putResponse);

        entityRepositoryHelper.getEntity(resourceLocation);
    }
}
