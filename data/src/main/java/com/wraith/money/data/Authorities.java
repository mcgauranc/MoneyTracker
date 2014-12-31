package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the roles that the group is assigned. Currently ROLE_ADMIN or ROLE_USER
 *
 * User: rowan.massey Date: 12/08/12
 */
@Document
public class Authorities extends BaseEntity implements Serializable {

	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
