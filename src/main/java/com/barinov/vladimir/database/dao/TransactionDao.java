package com.barinov.vladimir.database.dao;

import com.barinov.vladimir.database.dao.binder.BindTransaction;
import com.barinov.vladimir.database.mapper.TransactionMapper;
import com.barinov.vladimir.model.Transaction;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

/**
 * TransactionDao class
 */
@RegisterMapper(TransactionMapper.class)
public interface TransactionDao {
    String CREATE_TRANSACTIONS_TABLE_QUERY = "CREATE TABLE transactions (" +
            "transactionid BIGINT NOT NULL AUTO_INCREMENT, " +
            "payerid BIGINT NOT NULL, " +
            "payeeid BIGINT NOT NULL, " +
            "amount DECIMAL(20,2), " +
            "currencyid BIGINT, " +
            "date DATE, " +
            "status VARCHAR(100), " +
            "purpose VARCHAR(100), " +
            "PRIMARY KEY (transactionid)" +
            ");";

    String FILL_TRANSACTIONS_TABLE_QUERY = "INSERT INTO transactions " +
            "(payerid, payeeid, amount, currencyid, date, status, purpose) VALUES " +
            "('1', '2', 100, '1', CURRENT_DATE(), 'COMMITED', 'Test purpose') " + //id 1
            ";";

    String SELECT_TRANSACTION_BY_ID_QUERY = "SELECT t.transactionid, t.amount, t.date, t.status, t.purpose, " +
            "pr.personid as pr_id, pr.fullname AS pr_name, " +
            "pe.personid as pe_id, pe.fullname AS pe_name, " +
            "c.currencyid AS cur_id, c.code AS cur_code, c.isocode AS cur_name, c.longname AS cur_longname " +
            "FROM transactions t " +
            "LEFT JOIN currencies c ON t.currencyid = c.currencyid " +
            "LEFT JOIN persons pr ON t.payerid = pr.personid " +
            "LEFT JOIN persons pe ON t.payeeid = pe.personid " +
            "WHERE transactionid = :transactionId;";

    String GET_ALL_TRANSACTIONS_QUERY = "SELECT t.transactionid, t.amount, t.date, t.status, t.purpose, " +
            "pr.personid as pr_id, pr.fullname AS pr_name, " +
            "pe.personid as pe_id, pe.fullname AS pe_name, " +
            "c.currencyid AS cur_id, c.code AS cur_code, c.isocode AS cur_name, c.longname AS cur_longname " +
            "FROM transactions t " +
            "LEFT JOIN currencies c ON t.currencyid = c.currencyid " +
            "LEFT JOIN persons pr ON t.payerid = pr.personid " +
            "LEFT JOIN persons pe ON t.payeeid = pe.personid;";

    String CREATE_TRANSACTION_QUERY = "INSERT INTO transactions " +
            "(payerid, payeeid, amount, currencyid, date, status, purpose) VALUES " +
            "(:payerid, :payeeid, :amount, :currencyid, CURRENT_DATE(), :status, :purpose);";


    String UPDATE_TRANSACTION_STATUS_QUERY = "UPDATE transactions SET " +
            "status = :status " +
            "WHERE transactionid = :transactionId;";
    /**
     * Creating 'TRANSACTIONS' table
     */
    @SqlUpdate(CREATE_TRANSACTIONS_TABLE_QUERY)
    void createTransactionsTable();

    /**
     * Filling 'TRANSACTIONS' table
     */
    @SqlUpdate(FILL_TRANSACTIONS_TABLE_QUERY)
    void fillTransactionsTable();

    /**
     * Getting transaction by id
     */
    @SqlQuery(SELECT_TRANSACTION_BY_ID_QUERY)
    Transaction getTransaction(@Bind("transactionId") Integer transactionId);

    /**
     * Getting all transactions
     *
     * @return all transactions
     */
    @SqlQuery(GET_ALL_TRANSACTIONS_QUERY)
    Collection<Transaction> getTransactions();

    /**
     * Creating new transaction
     *
     * @param transaction transaction that we're trying to create
     * @return id of the created transaction
     */
    @SqlUpdate(CREATE_TRANSACTION_QUERY)
    @GetGeneratedKeys
    Integer createTransaction(@BindTransaction Transaction transaction);

    /**
     * Removing transaction status
     *
     * @param transactionId transaction id
     * @param status new status
     */
    @SqlUpdate(UPDATE_TRANSACTION_STATUS_QUERY)
    void updateTransactionState(@Bind("transactionId") Integer transactionId, @Bind("status") String status);
}