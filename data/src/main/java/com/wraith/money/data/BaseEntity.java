package com.wraith.money.data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * This class facilitates all of the common properties for each of the entities.
 *
 * User: rowan.massey Date: 23/07/12
 */
@MappedSuperclass
public class BaseEntity implements Cloneable, Serializable {

	@Id
	protected String id;
	//@JsonIgnore
	@Version
	protected String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
