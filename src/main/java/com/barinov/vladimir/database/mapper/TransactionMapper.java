package com.barinov.vladimir.database.mapper;

import com.barinov.vladimir.model.Currency;
import com.barinov.vladimir.model.Person;
import com.barinov.vladimir.model.Transaction;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TransactionMapper class
 */
@RegisterMapper(TransactionMapper.class)
public class TransactionMapper implements ResultSetMapper<Transaction> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new Transaction(
                resultSet.getInt("transactionid"),
                resultSet.getBigDecimal("amount"),
                resultSet.getDate("date"),
                resultSet.getString("status"),
                resultSet.getString("purpose"),
                new Person(
                        resultSet.getInt("pe_id"),
                        resultSet.getString("pe_name")
                ),
                new Person(
                        resultSet.getInt("pr_id"),
                        resultSet.getString("pr_name")
                ),
                new Currency(
                        resultSet.getInt("cur_id"),
                        resultSet.getString("cur_code"),
                        resultSet.getString("cur_name"),
                        resultSet.getString("cur_longname")

                ));
    }
}
