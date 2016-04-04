package com.barinov.vladimir.model;

/**
 * Currency class
 */
public class Currency {
    /**
     * Currency id
     */
    private Integer currencyId;
    /**
     * Currency code (978, 840 etc.)
     */
    private String currencyCode;

    /**
     * Currency name (EUR, USD, GBP etc)
     */
    private String currencyName;

    /**
     * Currency description - United States dollar, EURO, British pound
     */
    private String currencyLongName;

    public Currency(){
    }

    public Currency(Integer currencyId, String currencyCode, String currencyName, String currencyLongName) {
        this.currencyCode = currencyCode;
        this.currencyId = currencyId;
        this.currencyLongName = currencyLongName;
        this.currencyName = currencyName;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyLongName() {
        return currencyLongName;
    }

    public void setCurrencyLongName(String currencyLongName) {
        this.currencyLongName = currencyLongName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
