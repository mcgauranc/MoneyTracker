package com.wraith.money.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is a representation of the groups entity, it manages all of the groups that the relevant user belongs to.
 *
 * User: rowan.massey Date: 12/08/12
 */
@Document
public class Groups extends BaseEntity implements Serializable {

	private String name;
	@DBRef
	private Set<Authorities> authorities = new HashSet<>();

	public Groups() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Authorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}
}
