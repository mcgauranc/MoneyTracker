package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the currency used in the transaction, in the database. User: rowan.massey Date: 15/08/12
 */
@Document
public class Currency extends BaseEntity implements Serializable {

	private String name;
	private String iso;

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
