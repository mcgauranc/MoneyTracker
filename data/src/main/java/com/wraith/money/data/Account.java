package com.wraith.money.data;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * This class is the object representation of an account in the database.
 *
 * User: rowan.massey
 * Date: 15/08/12
 */
@Document
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "account_id")),
        @AttributeOverride(name = "version", column = @Column(name = "account_version"))})
public class Account extends BaseEntity implements Serializable {

    private String name;
    @DBRef
    private AccountType type;
    private double openingBalance;
    @DBRef
    private Currency currency;

    @NaturalId
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double balance) {
        this.openingBalance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

