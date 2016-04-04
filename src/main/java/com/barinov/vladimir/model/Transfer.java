package com.barinov.vladimir.model;

import java.math.BigDecimal;

/**
 * Transfer class
 */
public class Transfer {
    /**
     * Payer
     */
    private String payer;
    /**
     * Payee
     */
    private String payee;
    /**
     * Amount
     */
    private BigDecimal amount;
    /**
     * Purpose
     */
    private String purpose;

    public Transfer(){
    }

    public Transfer(BigDecimal amount, String payee, String payer, String purpose) {
        this.amount = amount;
        this.payee = payee;
        this.payer = payer;
        this.purpose = purpose;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
