package com.wraith.repository.integrationTests;

import static com.wraith.repository.integrationTests.AuthorityRequestTest.getNewAuthoritySet;

import java.util.Set;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;

import com.wraith.repository.entity.Authorities;
import com.wraith.repository.entity.Groups;

/**
 * User: rowan.massey
 * Date: 06/05/13
 * Time: 13:17
 */
public class GroupsRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method returns a new instance of a group object with a given name, and list of authorities.
     *
     * @param name        The name of the group.
     * @param authorities A list of associated authorities.
     * @return A new instance of the groups object.
     */
    private static Groups getNewGroup(String name, Set<Authorities> authorities) {
        Groups groups = new Groups();
        groups.setName(name);
        if (authorities != null) {
            groups.setAuthorities(authorities);
        }
        return groups;
    }

    @Test(expected = Exception.class)
    public void testCreateGroupsWithNoAuthenticationRequest() throws Exception {
        authenticate("", "");
        String resourceRequest = createNewGroup("Reporters", null);
        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateGroupsRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewGroup("Developers", getNewAuthoritySet("ROLE_DEVELOPER", "ROLE_REPORTER"));

        //Retrieve the inserted group record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Developers");

        //Get the groups link for the returned payload.
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject authorities = getJsonObjectFromArray("rel", "groups.groups.authorities", links);

        //Retrieve the groups link, and perform a request to get the information from the database.
        String authoritiesLink = getResourceURI(authorities.get("href").toString());
        MockHttpServletResponse getGroupResponse = performGetRequest(authoritiesLink);
        String groupsContent = getGroupResponse.getContentAsString();
        JSONObject jsonGroupObject = (JSONObject) parser.parse(groupsContent);

        //Ensure that the response contains the name users.
        JSONObject reporterAuthority = getJsonObjectFromArray("authority", "ROLE_REPORTER", (JSONArray) jsonGroupObject.get("content"));
        JSONObject developerAuthority = getJsonObjectFromArray("authority", "ROLE_DEVELOPER", (JSONArray) jsonGroupObject.get("content"));

        Assert.assertNotNull(reporterAuthority);
        Assert.assertNotNull(developerAuthority);
    }

    @Test(expected = Exception.class)
    public void testCreateGroupsWithOrdinaryUserRequest() throws Exception {
        createNewUser("fiftieth.person", "Passw0rd", "Fiftieth", "Person");
        authenticate("fiftieth.person", "Passw0rd");

        createNewGroup("Quality Assurance", null);
    }

    @Test
    public void testUpdateGroupsRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewGroup("Testers", getNewAuthoritySet("ROLE_TESTER"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Testers");
        byte[] updatedGroupsBytes = mapper.writeValueAsBytes(jsonObject);

        //Retrieve the updated group record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedGroupsBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String getContent = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(getContent);
        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Testers");
    }

    @Test(expected = Exception.class)
    public void testUpdateGroupsWithOrdinaryUserRequest() throws Exception {
        authenticate("Admin", "Passw0rd");

        String resourceRequest = createNewGroup("Marketing", getNewAuthoritySet("ROLE_MARKETING"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Marketing");
        byte[] updatedGroupsBytes = mapper.writeValueAsBytes(jsonObject);

        createNewUser("fiftyfirst.person", "Passw0rd", "Fifty First", "Person");
        authenticate("fiftyfirst.person", "Passw0rd");

        //Retrieve the updated group record from the database, and ensure that values are correct.
        performPutRequest(resourceRequest, updatedGroupsBytes);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteCategoryWithAdminUserRequest() throws Exception {
        authenticate("Admin", "Passw0rd");
        String resourceRequest = createNewGroup("Publishers", getNewAuthoritySet("ROLE_PUBLISHERS"));

        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteCategoryWithNoParentForCurrentUserRequest() throws Exception {
        createNewUser("fiftysecond.person", "Passw0rd", "Fifty Second", "Person");
        authenticate("fiftysecond.person", "Passw0rd");

        String resourceRequest = createNewGroup("Editors", getNewAuthoritySet("ROLE_EDITORS"));

        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest);
    }

    /**
     * This method creates a new group in the database, along with associated authorities, if any.
     *
     * @param name        The name of the group.
     * @param authorities A list of associated authorities.
     * @return The URI to the created group.
     * @throws Exception
     */
    private String createNewGroup(String name, Set<Authorities> authorities) throws Exception {
        Groups groups = getNewGroup(name, authorities);
        return createNewEntity(groups, GROUPS_PATH);
    }
}
