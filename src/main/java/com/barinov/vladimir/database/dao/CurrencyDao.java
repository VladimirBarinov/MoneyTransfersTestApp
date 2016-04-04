package com.barinov.vladimir.database.dao;

import com.barinov.vladimir.database.mapper.CurrencyMapper;
import com.barinov.vladimir.model.Currency;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * CurrencyDao class
 */
@RegisterMapper(CurrencyMapper.class)
public interface CurrencyDao {
    String CREATE_CURRENCIES_TABLE_QUERY = "CREATE TABLE currencies (" +
            "currencyid BIGINT NOT NULL AUTO_INCREMENT, " +
            "code VARCHAR(3) NOT NULL, " +
            "isocode VARCHAR(3), " +
            "longname VARCHAR(100), " +
            "PRIMARY KEY (currencyid)" +
            ");";

    String FILL_CURRENCIES_TABLE_QUERY = "INSERT INTO currencies (code, isocode, longname) VALUES " +
            "('840', 'USD', 'United States Dollar'), " + //id 1
            "('978', 'EUR', 'EURO'), " +                 //id 2
            "('826', 'GBP', 'Pound Sterling')" +         //id 3
            ";";

    String GET_ALL_CURRENCIES_QUERY = "SELECT * FROM currencies;";

    String CREATE_CURRENCY_QUERY = "INSERT INTO currencies " +
            "(code, isocode, longname) VALUES " +
            "(:currency.currencyCode, :currency.currencyName, :currency.currencyLongName);";

    String SELECT_CURRENCY_BY_ID_QUERY = "SELECT * FROM currencies WHERE " +
            "currencyid = :currencyId;";

    String SELECT_CURRENCY_BY_CODE_QUERY = "SELECT currencyid FROM currencies WHERE " +
            "code = :currencyCode;";

    String UPDATE_CURRENCY_QUERY = "UPDATE currencies SET " +
            "code = :currency.currencyCode, " +
            "isocode = :currency.currencyName, " +
            "longname = :currency.currencyLongName " +
            "WHERE currencyid = :currency.currencyId;";

    String DELETE_CURRENCY_QUERY = "DELETE FROM currencies WHERE " +
            "currencyid = :currencyId;";

    /**
     * Creating 'CURRENCIES' table
     */
    @SqlUpdate(CREATE_CURRENCIES_TABLE_QUERY)
    void createCurrenciesTable();

    /**
     * Filling 'CURRENCIES' table with some data
     */
    @SqlUpdate(FILL_CURRENCIES_TABLE_QUERY)
    void fillCurrenciesTable();

    /**
     * Getting all currencies from 'CURRENCIES' table
     *
     * @return all currencies
     */
    @SqlQuery(GET_ALL_CURRENCIES_QUERY)
    Collection<Currency> getCurrencies();

    /**
     * Get currency by id from 'CURRENCIES' table
     *
     * @param currencyId currency id
     * @return selected currency
     */
    @SqlQuery(SELECT_CURRENCY_BY_ID_QUERY)
    Currency getCurrencyById(@Bind("currencyId") Integer currencyId);

    /**
     * Get currency by code from 'CURRENCIES' table
     *
     * @param currencyCode currency code
     * @return selected currency
     */
    @SqlQuery(SELECT_CURRENCY_BY_CODE_QUERY)
    Integer getCurrencyIdByCode(@Bind("currencyCode") String currencyCode);

    /**
     * Creating new currency in 'CURRENCIES' table
     *
     * @param currency currency that we're trying to create
     * @return created currency
     */
    @SqlUpdate(CREATE_CURRENCY_QUERY)
    @GetGeneratedKeys
    int createCurrency(@BindBean("currency") Currency currency);

    /**
     * Updating currency in 'CURRENCIES' table
     *
     * @param currency cureency
     * @return currency id
     */
    @SqlUpdate(UPDATE_CURRENCY_QUERY)
    void updateCurrency(@BindBean("currency") Currency currency);

    /**
     * Removing currency from 'CURRENCIES' table
     *
     * @param currencyId currency id
     */
    @SqlUpdate(DELETE_CURRENCY_QUERY)
    void deleteCurrency(@Bind("currencyId") Integer currencyId);

}
