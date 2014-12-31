package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the categories of the transaction in the database. e.g. Petrol, Groceries-->Bread etc
 *
 * User: rowan.massey Date: 02/09/12
 */
@Document
public class Category extends BaseEntity implements Serializable {

	@DBRef
	private Category parentCategory;
	private String name;

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
