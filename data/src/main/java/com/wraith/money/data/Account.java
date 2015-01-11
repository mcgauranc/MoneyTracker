package com.wraith.money.data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class is the object representation of an account in the database.
 * <p/>
 * User: rowan.massey
 * Date: 15/08/12
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
@AuditTable(value = "Account_Audit")
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "account_id")),
        @AttributeOverride(name = "version", column = @Column(name = "account_version"))})
public class Account extends BaseEntity implements Serializable {

    private String name;
    private String number;
    private AccountType type;
    private double openingBalance;
    //This field needs to be here, would be too slow if calculating it from all the transactions.
    private double balance;
    private Date openingDate;
    private Currency currency;
    private Boolean isFavourite;
    private int financialInstitution; //This is another entity;
    private double creditLimit;
    private double interestRate;
    private Users user;


    @NaturalId
    @Column(name = "account_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_type_id")
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Column(name = "account_openingBalance", nullable = false)
    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double balance) {
        this.openingBalance = balance;
    }

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_currency_id")
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Column(name = "account_openingDate", nullable = false)
    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @Column(name = "account_favourite", nullable = false)
    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Column(name = "account_number", nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "account_balance", nullable = false)
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Column(name = "account_institution_id", nullable = false)
    public int getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(int financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Column(name = "account_credit_limit", nullable = false)
    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Column(name = "account_interest_rate", nullable = false)
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}

