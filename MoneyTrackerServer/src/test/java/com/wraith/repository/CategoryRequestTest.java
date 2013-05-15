package com.wraith.repository;

import com.wraith.repository.entity.Category;
import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.StringUtils;

/**
 * User: rowan.massey
 * Date: 01/05/13
 * Time: 20:40
 */
public class CategoryRequestTest extends AbstractBaseIntegrationTests {

    @Test
    public void testCreateCategoryWithNoParentRequest() throws Exception {
        String resourceRequest = createNewCategory("Groceries", null);

        //Retrieve the inserted authority record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Groceries");
    }

    @Test
    public void testUpdateCategoryWithNoParentForAdminUserRequest() throws Exception {
        String resourceRequest = createNewCategory("Banking", null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Banking");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        authenticate("Administrator", "Passw0rd");
        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
        Assert.assertNotNull(putResponse);

        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject getJSONObject = (JSONObject) parser.parse(content);

        Assert.assertEquals((String) getJSONObject.get("name"), "Updated Banking");
    }

    @Test(expected = Exception.class)
    public void testUpdateCategoryWithNoParentForCurrentUserRequest() throws Exception {

        String resourceRequest = createNewCategory("Revenue", null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Updated Revenue");

        byte[] updatedAccountBytes = mapper.writeValueAsBytes(jsonObject);

        authenticate("seventeenth.person", "Passw0rd");
        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performPutRequest(resourceRequest, updatedAccountBytes);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteCategoryWithNoParentForAdminUserRequest() throws Exception {
        String resourceRequest = createNewCategory("Computer", null);

        authenticate("Administrator", "Passw0rd");
        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest);
    }

    @Test(expected = Exception.class)
    public void testDeleteCategoryWithNoParentForCurrentUserRequest() throws Exception {
        String resourceRequest = createNewCategory("Computer", null);

        authenticate("seventeenth.person", "Passw0rd");
        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest);
    }

    @Test
    public void testCreateCategoryWithParentRequest() throws Exception {
        String resourceRequest = createNewCategory("Toothpaste", "Personal Care");

        //Retrieve the inserted authority record from the database, and ensure that values are correct.
        MockHttpServletResponse getResponse = performGetRequest(resourceRequest);
        String content = getResponse.getContentAsString();
        JSONObject jsonObject = (JSONObject) parser.parse(content);
        Assert.assertEquals((String) jsonObject.get("name"), "Toothpaste");

        //Get the category link for the returned payload.
        JSONArray links = (JSONArray) jsonObject.get("links");
        JSONObject parentCategory = getJsonObjectFromArray("rel", "category.category.parentCategory", links);

        //Retrieve the parent category link, and perform a request to get the information from the database.
        String categoryParentLink = getResourceURI(parentCategory.get("href").toString());
        MockHttpServletResponse getCategoryResponse = performGetRequest(categoryParentLink);
        JSONObject parentCategoryObject = (JSONObject) parser.parse(getCategoryResponse.getContentAsString());

        JSONObject parentCategoryName = (JSONObject) parentCategoryObject.get("content");
        Assert.assertEquals((String) parentCategoryName.get("name"), "Personal Care");
    }

    /**
     * This method creates a new category, and returns the URI location of the created category.
     *
     * @param name           The name of the category
     * @param parentCategory The name of the parent category, if any.
     * @return The URI location of the created category.
     * @throws Exception
     */
    private String createNewCategory(String name, String parentCategory) throws Exception {
        Category category = getNewCategory(name, parentCategory);
        return createNewEntity(category, Category.class);
    }

    /**
     * This method creates a new instance of the category object
     *
     * @param name           The name of the category.
     * @param parentCategory The name of the parent category, if any.
     * @return an instance of a category object.
     */
    public static Category getNewCategory(String name, String parentCategory) {
        Category category = new Category();
        category.setName(name);
        if (!StringUtils.isEmpty(parentCategory)) {
            category.setParentCategory(getNewCategory(parentCategory, null));
        }
        return category;
    }
}
