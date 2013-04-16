package com.wraith.repository.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rowan.massey
 * Date: 15/08/12
 * Time: 21:25
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@AuditTable(value = "Account_Audit")
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "account_id")),
        @AttributeOverride(name = "version", column = @Column(name = "account_version"))})
public class Account extends BaseEntity implements Serializable {

    private String name;
    private AccountType type;
    private double balance;
    private Currency currency;

    @NaturalId
    @Column(name = "account_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_type_id")
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Column(name = "account_balance", nullable = false)
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_currency_id")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

