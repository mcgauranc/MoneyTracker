package com.wraith.money.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents the users table in the database.
 *
 * User: rowan.massey Date: 06/07/12
 */
@Document
public class Users extends BaseEntity implements Serializable {

	@Indexed(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	@JsonIgnore
	private int enabled;
	@DBRef
	private Address address;
	@DBRef
	private Set<Groups> groups = new HashSet<>();
	@DBRef
	private Set<Transaction> transactions = new HashSet<>();
	@DBRef
	private Set<Account> accounts = new HashSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//    @JsonIgnore
	public String getPassword() {
		return password;
	}

	//    @JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Set<Groups> groups) {
		this.groups = groups;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@JsonIgnore
	@Transient
	public String getUserFullName() {
		return this.getFirstName().concat(" ").concat(getLastName());
	}

	@JsonIgnore
	@Transient
	public Users clone() throws CloneNotSupportedException {
		return (Users) super.clone();
	}
}
