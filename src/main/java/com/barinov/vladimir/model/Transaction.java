package com.barinov.vladimir.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Transaction class
 */
public class Transaction {
    /**
     * Transaction unique id
     */
    private Integer operationId;

    /**
     * Operation date
     */
    private Date operationDate;

    /**
     * Payer
     */
    private Person payer;

    /**
     * Payee
     */
    private Person payee;

    /**
     * Operation amount
     */
    private BigDecimal operationAmount;

    /**
     * Operation currency
     */
    private Currency operationCurrency;
    /**
     * Operation state {CREATED, CONFIRMED, COMMITED, FAILED, REJECTED}
     */
    private TransactionState operationState;
    /**
     * Operation purpose
     */
    private String operationPurpose;

    public Transaction(){

    }

    public Transaction(BigDecimal operationAmount,
                       Currency operationCurrency,
                       String operationPurpose,
                       TransactionState operationState,
                       Person payer,
                       Person payee) {
        this.operationAmount = operationAmount;
        this.operationCurrency = operationCurrency;
        this.operationPurpose = operationPurpose;
        this.operationState = operationState;
        this.payer = payer;
        this.payee = payee;
    }

    //for mapper
    public Transaction(Integer operationId,  //difference
                       BigDecimal operationAmount,
                       Date operationDate,
                       String operationState,
                       String operationPurpose,
                       Person payee,
                       Person payer,
                       Currency operationCurrency) {
        this.operationAmount = operationAmount;
        this.operationCurrency = operationCurrency;
        this.operationDate = operationDate;
        this.operationId = operationId;
        this.operationPurpose = operationPurpose;
        this.operationState = TransactionState.valueOf(operationState);
        this.payer = payer;
        this.payee = payee;
    }

    public BigDecimal getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(BigDecimal operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Currency getOperationCurrency() {
        return operationCurrency;
    }

    public void setOperationCurrency(Currency operationCurrency) {
        this.operationCurrency = operationCurrency;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationPurpose() {
        return operationPurpose;
    }

    public void setOperationPurpose(String operationPurpose) {
        this.operationPurpose = operationPurpose;
    }

    public String getOperationState() {
        return operationState.name();
    }

    public void setOperationState(String operationState) {
        this.operationState = TransactionState.valueOf(operationState);
    }

    public Person getPayee() {
        return payee;
    }

    public void setPayee(Person payee) {
        this.payee = payee;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }
}
