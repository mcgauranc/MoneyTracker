package com.wraith.money.web.repository;

import net.minidev.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import com.wraith.money.data.Category;

/**
 * User: rowan.massey
 * Date: 01/05/13
 * Time: 20:40
 */
public class CategoryRequestTest extends AbstractBaseIntegrationTests {

    /**
     * This method creates a new instance of the category object
     *
     * @param name           The name of the category.
     * @return an instance of a category object.
     */
	public static Category getNewCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

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

        authenticate("Admin", "Passw0rd");
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

    @Test
    public void testDeleteCategoryWithNoParentForAdminUserRequest() throws Exception {
        String resourceRequest = createNewCategory("Computer", null);

        authenticate("Admin", "Passw0rd");
        //Update the inserted category record from the database, and ensure that values are correct.
        MockHttpServletResponse putResponse = performDeleteRequest(resourceRequest);
        Assert.assertNotNull(putResponse);

        performGetRequest(resourceRequest, null, HttpStatus.NOT_FOUND);
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
		Assert.assertEquals(jsonObject.get("name"), "Toothpaste");

        //Get the category link for the returned payload.
		JSONObject links = (JSONObject) jsonObject.get(LINKS);
		JSONObject parentCategory = (JSONObject) links.get(PARENT_CATEGORY);

        //Retrieve the parent category link, and perform a request to get the information from the database.
		String categoryParentLink = parentCategory.get(HREF).toString();
        MockHttpServletResponse getCategoryResponse = performGetRequest(categoryParentLink);
        JSONObject parentCategoryObject = (JSONObject) parser.parse(getCategoryResponse.getContentAsString());

        JSONObject parentCategoryName = (JSONObject) parentCategoryObject.get("content");
		Assert.assertEquals(parentCategoryName.get("name"), "Personal Care");
    }

    /**
	 * This method creates a new category, and returns the URI location of the created category.
	 *
	 * @param name
	 *            The name of the category
	 * @return The URI location of the created category.
	 * @throws Exception
	 */
	private String createNewCategory(String name, String parentCategoryName) throws Exception {
		String parentCategoryLocation = "";
		if (!parentCategoryName.isEmpty()) {
			Category parentCategory = getNewCategory(parentCategoryName);
			parentCategoryLocation = createNewEntity(parentCategory, CATEGORIES_PATH);
		}
		Category category = getNewCategory(name);

		String categoryLocation = createNewEntity(category, CATEGORIES_PATH);

		if (!parentCategoryName.isEmpty()) {
			associateEntities(parentCategoryLocation.concat("/").concat("parentCategory"), categoryLocation);
		}

		return categoryLocation;
    }
}
