package com.wraith.money.web.repository;

import com.wraith.money.web.repository.entity.Payee;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * User: rowan.massey
 * Date: 11/05/13
 * Time: 23:07
 */
public class PayeeRequestTests extends AbstractBaseIntegrationTests {

    /**
     * @param name
     * @return
     */
    public static Payee getNewPayee(String name) {
        Payee payee = new Payee();
        payee.setName(name);
        return payee;
    }

    @Test(expected = Exception.class)
    public void testCreatePayeeWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewPayee("Superquinn");
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreatePayeeWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");
        String resourceLocation = createNewPayee("Spar");
        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals((String) getJSONObject.get("name"), "Spar");
    }

    @Test
    public void testCreatePayeeWithNormalUser() throws Exception {
        createNewUser("sixtieth.person", "Passw0rd", "Sixtieth", "Person");
        authenticate("sixtieth.person", "Passw0rd");

        String resourceLocation = createNewPayee("Tesco");
        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals((String) getJSONObject.get("name"), "Tesco");
    }

    @Test
    public void testUpdatePayeeWithAdminUser() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceLocation = createNewPayee("Statoil");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Statoil");
        byte[] updatedGroupsBytes = mapper.writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceLocation, updatedGroupsBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Statoil");

    }

    @Test
    public void testUpdatePayeeWithNormalUser() throws Exception {
        createNewUser("sixtyfirst.person", "Passw0rd", "Sixty First", "Person");
        authenticate("sixtyfirst.person", "Passw0rd");

        String resourceLocation = createNewPayee("Tesco");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Tesco");
        byte[] updatedGroupsBytes = mapper.writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceLocation, updatedGroupsBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceLocation);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Tesco");
    }

    @Test
    public void testDeleteCategoryWithAdminUserRequest() throws Exception {
        authenticate("Admin", "Passw0rd");
        String resourceRequest = createNewPayee("Art of Coffee");

        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest, null, HttpStatus.NOT_FOUND);
    }

    @Test(expected = Exception.class)
    public void testDeleteCategoryWithNoParentForCurrentUserRequest() throws Exception {
        createNewUser("sixtysecond.person", "Passw0rd", "Sixty Second", "Person");
        authenticate("sixtysecond.person", "Passw0rd");

        String resourceRequest = createNewPayee("Starbucks");

        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest);
    }

    /**
     * @param name
     * @return
     * @throws Exception
     */
    private String createNewPayee(String name) throws Exception {
        Payee payee = getNewPayee(name);
        return createNewEntity(payee, PAYEES_PATH);
    }
}
