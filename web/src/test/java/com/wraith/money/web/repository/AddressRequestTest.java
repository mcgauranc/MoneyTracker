package com.wraith.money.web.repository;

import com.wraith.money.data.Address;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 30/04/13
 * Time: 18:27
 */
public class AddressRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates and instance of the address class, with default values.
     *
     * @param address1   The first address line.
     * @param address2   The second address line.
     * @param city       The addresses city.
     * @param county     The addresses county.
     * @param country    The addresses country.
     * @param countryISO The ISO code of the country.
     * @return an instance of the created Address object.
     */
    public static Address getNewAddress(String address1, String address2, String city, String county, String country, String countryISO) {
        Address address = new Address();
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setCounty(county);
        address.setCountry(CountryRequestTest.getNewCountry(countryISO, country));
        return address;
    }

    @Test(expected = Exception.class)
    public void testCreateAddressWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewAddress("Address 1", "Address 2", "Dublin", "Dublin", "Ireland", "IRE");
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateAddressRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAddress("Address 1", "Address 2", "Belfast", "Antrim", "Northern Ireland", "NI");
        //Retrieve the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("address1"), "Address 1");
        Assert.assertEquals((String) jsonObject.get("address2"), "Address 2");
        Assert.assertEquals((String) jsonObject.get("city"), "Belfast");
    }

    @Test
    public void testUpdateAccountTypeRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAddress("Address 1", "Address 2", "Cork", "Cork", "Ireland", "IRE");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address1", "Updated Address 1");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        //Update the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("address1"), "Updated Address 1");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteAccountRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewAddress("Address 1", "Address 2", "Galway", "Galway", "Ireland", "IRE");

        //Delete the inserted account record from the database, and ensure that values are correct.
        MockHttpServletResponse deleteResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(deleteResponse);

        //Ensure that the deleted account can't be retrieved from the database.
        performGetRequest(resourceRequest);
    }

    /**
     * This method creates a new address, saves it to the database, and returns the location of the created record.
     *
     * @param address1   The first address line.
     * @param address2   The second address line.
     * @param city       The addresses city.
     * @param county     The addresses county.
     * @param country    The addresses country.
     * @param countryISO The ISO code of the country.
     * @return The URI location of the created record.
     * @throws Exception
     */
    public String createNewAddress(String address1, String address2, String city, String county, String country, String countryISO) throws Exception {
        Address address = getNewAddress(address1, address2, city, county, country, countryISO);
        return createNewEntity(address, ADDRESSES_PATH);
    }
}
