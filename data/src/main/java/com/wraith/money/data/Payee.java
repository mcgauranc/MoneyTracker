package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the payee of the transaction in the database.
 *
 * User: rowan.massey Date: 16/08/12
 */
@Document
public class Payee extends BaseEntity implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
