package com.barinov.vladimir.database;

import com.barinov.vladimir.MoneyTransfersTestAppConfiguration;
import com.barinov.vladimir.database.dao.AccountDao;
import com.barinov.vladimir.database.dao.CurrencyDao;
import com.barinov.vladimir.database.dao.PersonDao;
import com.barinov.vladimir.database.dao.TransactionDao;
import com.barinov.vladimir.resource.*;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Class for initializing the H2 database
 */
public class Database {
    public static void initialize(MoneyTransfersTestAppConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "H2");
        final AccountDao accountDao = jdbi.onDemand(AccountDao.class);
        final CurrencyDao currencyDao = jdbi.onDemand(CurrencyDao.class);
        final PersonDao personDao = jdbi.onDemand(PersonDao.class);
        final TransactionDao transactionDao = jdbi.onDemand(TransactionDao.class);
        environment.jersey().register(new AccountResource(accountDao, currencyDao, personDao));
        environment.jersey().register(new CurrencyResource(currencyDao));
        environment.jersey().register(new PersonResource(personDao));
        environment.jersey().register(new TransactionResource(transactionDao));
        environment.jersey().register(new TransferResource(accountDao, personDao, transactionDao));
        createAndFillCurrenciesTable(currencyDao);
        createAndFillPersonsTable(personDao);
        createAccountsTable(accountDao);
        createAndFillTransactionsTable(transactionDao);
    }

    /**
     * Creating and filling the 'ACCOUNTS' table
     *
     * @param accountDao accountDao
     */
    public static void createAccountsTable(AccountDao accountDao) {
        accountDao.createAccountsTable();
        accountDao.fillAccountsTable();
    }

    /**
     * Creating and filling the 'CURRENCIES' table
     *
     * @param currencyDao currencyDao
     */
    public static void createAndFillCurrenciesTable(CurrencyDao currencyDao) {
        currencyDao.createCurrenciesTable();
        currencyDao.fillCurrenciesTable();
    }

    /**
     * Creating and filling the 'PERSONS' table
     *
     * @param personDao personsDao
     */
    public static void createAndFillPersonsTable(PersonDao personDao) {
        personDao.createPersonsTable();
        personDao.fillPersonsTable();
    }

    /**
     * Creating and filling the 'TRANSACTIONS' table
     *
     * @param transactionDao transactionDao
     */
    public static void createAndFillTransactionsTable(TransactionDao transactionDao) {
        transactionDao.createTransactionsTable();
        transactionDao.fillTransactionsTable();
    }
}