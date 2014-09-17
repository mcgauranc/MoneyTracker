package com.wraith.money.web.repository;

import com.wraith.money.web.repository.entity.Currency;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 06/05/13
 * Time: 11:44
 */
public class CurrencyRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates and returns a currency object.
     *
     * @param iso  The ISO code of the currency.
     * @param name The name of the currency.
     * @return An instance of the currency object.
     */
    public static Currency getCurrency(String iso, String name) {
        Currency currency = new Currency();
        currency.setIso(iso);
        currency.setName(name);
        return currency;
    }

    @Test(expected = Exception.class)
    public void testCreateCurrencyWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewCurrency("GBP", "British Pound");
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateCurrencyRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCurrency("EUR", "Euro");

        //Retrieve the inserted authority record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Euro");
    }

    @Test(expected = Exception.class)
    public void testCreateCurrencyWithOrdinaryUserRequest() throws Exception {
        createNewUser("fourtieth.person", "Passw0rd", "Fourtieth", "Person");
        authenticate("fourtieth.person", "Passw0rd");

        createNewCurrency("USD", "Dollar");
    }

    @Test
    public void testUpdateCountryRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCurrency("ZAR", "South African Rand");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Rand");

        byte[] updatedCountryBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted currency record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedCountryBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Rand");
    }

    @Test(expected = Exception.class)
    public void testUpdateCountryWithOrdinaryUserRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCurrency("CAD", "Canadian Dollar");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Canadian Dollar");

        byte[] updatedCountryBytes = mapper.writeValueAsBytes(jsonObject);

        createNewUser("fourtyfirst.person", "Passw0rd", "Fourty First", "Person");
        authenticate("fourtyfirst.person", "Passw0rd");
        //Update the inserted country record from the database, and ensure that values are correct.
        performPutRequest(resourceRequest, updatedCountryBytes);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteCountryRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCurrency("DKK", "Danish Krone");

        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteCountryWithOrdinaryUserRequest() throws Exception {
        createNewUser("fourtysecond.person", "Passw0rd", "Fourty Second", "Person");

        authenticate("Admin", "Passw0rd");
        String resourceRequest = createNewCurrency("NZD", "New Zealand Dollar");

        authenticate("fourtysecond.person", "Passw0rd");
        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    /**
     * This method creates a new currency record in the database, and returns the URI location of the created record.
     *
     * @param iso  The ISO code of the currency.
     * @param name The name of the currency.
     * @return The URI location of the created currency.
     * @throws Exception
     */
    private String createNewCurrency(String iso, String name) throws Exception {
        Currency currency = getCurrency(iso, name);
        return createNewEntity(currency, CURRENCY_PATH);
    }

}
