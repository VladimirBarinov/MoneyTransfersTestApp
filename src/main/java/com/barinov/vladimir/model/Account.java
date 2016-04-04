package com.barinov.vladimir.model;

import java.math.BigDecimal;

/**
 * Account class
 */
public class Account {
    /**
     * Account Id
     */
    private Integer accountId;

    /**
     * Account number
     */
    private String accountNumber;

    /**
     * Account amount
     */
    private BigDecimal amount;

    /**
     * Account currency
     */
    private Currency currency;

    /**
     * Person who owns account
     */
    private Person owner;

    public Account(){

    }

    public Account(Integer accountId, String accountNumber, BigDecimal amount, Currency currency, Person owner) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
        this.owner = owner;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
