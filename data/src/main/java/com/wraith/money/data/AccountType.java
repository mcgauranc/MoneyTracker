package com.wraith.money.data;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is the object representation of an account type in the database. They include "Current", "Wallet" etc
 *
 * User: rowan.massey
 * Date: 15/08/12
 */
@Document
public class AccountType extends BaseEntity implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
