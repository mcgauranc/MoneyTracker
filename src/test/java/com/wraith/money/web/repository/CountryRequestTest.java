package com.wraith.money.web.repository;

import com.wraith.money.web.repository.entity.Country;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 05/05/13
 * Time: 21:07
 */
public class CountryRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new country object
     *
     * @param countryISO The countries ISO code.
     * @param name       The name of the country.
     * @return An instance of the created country.
     */
    public static Country getNewCountry(String countryISO, String name) {
        Country country = new Country();
        country.setName(name);
        country.setIso(countryISO);
        return country;
    }

    @Test(expected = Exception.class)
    public void testCreateCountryWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewCountry("IRE", "Ireland");
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateCountryRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCountry("UK", "United Kingdom");

        //Retrieve the inserted authority record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "United Kingdom");
    }

    @Test(expected = Exception.class)
    public void testCreateCountryWithOrdinaryUserRequest() throws Exception {
        createNewUser("thirtieth.person", "Passw0rd", "Thirtieth", "Person");
        authenticate("thirtieth.person", "Passw0rd");

        createNewCountry("FR", "France");
    }

    @Test
    public void testUpdateCountryRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCountry("DE", "Germany");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Germany");

        byte[] updatedCountryBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted country record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedCountryBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Germany");
    }

    @Test(expected = Exception.class)
    public void testUpdateCountryWithOrdinaryUserRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCountry("US", "United States of America");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated United States of America");

        byte[] updatedCountryBytes = mapper.writeValueAsBytes(jsonObject);

        createNewUser("thirtyfirst.person", "Passw0rd", "Thirty First", "Person");
        authenticate("thirtyfirst.person", "Passw0rd");
        //Update the inserted country record from the database, and ensure that values are correct.
        performPutRequest(resourceRequest, updatedCountryBytes);
    }

    @Test
    public void testDeleteCountryRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewCountry("CA", "Canada");

        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest, null, HttpStatus.NOT_FOUND);
    }

    @Test(expected = Exception.class)
    public void testDeleteCountryWithOrdinaryUserRequest() throws Exception {
        createNewUser("thirtysecond.person", "Passw0rd", "Thirty Second", "Person");

        authenticate("Admin", "Passw0rd");
        String resourceRequest = createNewCountry("AU", "Australia");

        authenticate("thirtysecond.person", "Passw0rd");
        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    /**
     * This method creates a new country in the database for a given name and ISO code.
     *
     * @param countryISO The country's ISO code.
     * @param name       The name of the country.
     * @return The URI location of the created country.
     * @throws Exception
     */
    private String createNewCountry(String countryISO, String name) throws Exception {
        Country country = getNewCountry(countryISO, name);
        return createNewEntity(country, COUNTRIES_PATH);
    }
}
