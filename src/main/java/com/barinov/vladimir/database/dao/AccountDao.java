package com.barinov.vladimir.database.dao;

import com.barinov.vladimir.database.dao.binder.BindAccount;
import com.barinov.vladimir.database.mapper.AccountMapper;
import com.barinov.vladimir.model.Account;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import java.util.Collection;

/**
 * AccountDao class
 */
@RegisterMapper(AccountMapper.class)
public interface AccountDao {
    String CREATE_ACCOUNTS_TABLE_QUERY = "CREATE TABLE accounts (" +
            "accountid BIGINT NOT NULL AUTO_INCREMENT, " +
            "account VARCHAR(100), " +
            "amount DECIMAL(20,2), " +
            "currencyid BIGINT, " +
            "personid BIGINT, " +
            "PRIMARY KEY (accountid)" +
            ");";

    String FILL_ACCOUNTS_TABLE_QUERY = "INSERT INTO accounts (account, amount, currencyid, personid) VALUES " +
            "('40802840700001400000', '120999.99', 1, 1), " +
            "('40802978800001900001', '50000.25', 2, 2), " +
            "('40802840400002300000', '20500.51', 1, 2), " +
            "('40802826000003600002', '36000.00', 3, 3)" +
            ";";

    String SELECT_ACCOUNT_BY_ID_QUERY = "SELECT a.accountid, a.account, a.amount, " +
            "c.currencyid AS cur_id, c.code AS cur_code, c.isocode AS cur_name, c.longname AS cur_longname, " +
            "p.personid as per_id, p.fullname AS per_name " +
            "FROM accounts a " +
            "LEFT JOIN currencies c ON a.currencyid = c.currencyid " +
            "LEFT JOIN persons p ON a.personid = p.personid " +
            "WHERE accountid = :accountId;";

    String SELECT_ALL_ACCOUNT_QUERY = "SELECT a.accountid, a.account, a.amount, " +
            "c.currencyid AS cur_id, c.code AS cur_code, c.isocode AS cur_name, c.longname AS cur_longname, " +
            "p.personid as per_id, p.fullname AS per_name " +
            "FROM accounts a " +
            "LEFT JOIN currencies c ON a.currencyid = c.currencyid " +
            "LEFT JOIN persons p ON a.personid = p.personid;";

    String CREATE_ACCOUNT_QUERY = "INSERT INTO accounts " +
            "(account, amount, currencyid, personid) VALUES " +
            "(:account, :amount, :currencyid, :personid);";

    String UPDATE_ACCOUNT_QUERY =  "UPDATE accounts SET " +
            "account = :account, " +
            "amount = :amount, " +
            "currencyid = :currencyid, " +
            "personid =:personid " +
            "WHERE accountid = :accountid;";

    String UPDATE_AMOUNT_QUERY = "UPDATE accounts SET " +
            "amount = :amount " +
            "WHERE accountid = :accountid;";

    String DELETE_ACCOUNT_QUERY =  "DELETE FROM accounts " +
            "WHERE accountid = :accountId;";

    String GET_ACCOUNT_BY_PERSON_ID_QUERY =  "SELECT a.accountid, a.account, a.amount, " +
            "c.currencyid AS cur_id, c.code AS cur_code, c.isocode AS cur_name, c.longname AS cur_longname, " +
            "p.personid as per_id, p.fullname AS per_name " +
            "FROM accounts a " +
            "LEFT JOIN currencies c ON a.currencyid = c.currencyid " +
            "LEFT JOIN persons p ON a.personid = p.personid " +
            "WHERE p.personid = :personId;";


    /**
     * Creating 'ACCOUNTS' table
     */
    @SqlUpdate(CREATE_ACCOUNTS_TABLE_QUERY)
    void createAccountsTable();

    /**
     * Filling 'ACCOUNTS' table
     */
    @SqlUpdate(FILL_ACCOUNTS_TABLE_QUERY)
    void fillAccountsTable();

    /**
     * Creating account
     *
     * @param account account
     * @return created account id
     */
    @SqlUpdate(CREATE_ACCOUNT_QUERY)
    @GetGeneratedKeys
    Integer createAccount(@BindAccount Account account);

    /**
     * Getting account by id
     *
     * @param accountId account id
     * @return select account
     */
    @SqlQuery(SELECT_ACCOUNT_BY_ID_QUERY)
    Account getAccount(@Bind("accountId") Integer accountId);

    /**
     *
     * @return
     */
    @SqlQuery(SELECT_ALL_ACCOUNT_QUERY)
    Collection<Account> getAccounts();

    /**
     * Updating account
     *
     * @param account account that we're trying to update
     */
    @SqlUpdate(UPDATE_ACCOUNT_QUERY)
    void updateAccount(@BindAccount Account account);

    /**
     * Updating account amount
     *
     * @param account account
     */
    @SqlUpdate(UPDATE_AMOUNT_QUERY)
    void updateAmount(@BindAccount Account account);

    /**
     * Removing account
     *
     * @param accountId account id
     */
    @SqlUpdate(DELETE_ACCOUNT_QUERY)
    void deleteAccount(@Bind("accountId") int accountId);

    /**
     * Getting account by person id
     * @param personId  person id
     * @return selected account
     */
    @SqlQuery(GET_ACCOUNT_BY_PERSON_ID_QUERY)
    Account getAccountByPersonId(@Bind("personId") Integer personId);
}
