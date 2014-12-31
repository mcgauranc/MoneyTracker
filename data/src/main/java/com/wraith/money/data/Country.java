package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the country table in the database.
 *
 * User: rowan.massey Date: 17/08/12
 */
@Document
public class Country extends BaseEntity implements Serializable {

	private String name;
	private String iso;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}
}
