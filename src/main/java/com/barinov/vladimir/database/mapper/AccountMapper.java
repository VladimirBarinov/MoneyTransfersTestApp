package com.barinov.vladimir.database.mapper;

import com.barinov.vladimir.model.Account;
import com.barinov.vladimir.model.Currency;
import com.barinov.vladimir.model.Person;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AccontMapper class
 */
@RegisterMapper(AccountMapper.class)
public class AccountMapper implements ResultSetMapper<Account> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Account map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        return new Account(
                resultSet.getInt("accountid"),
                resultSet.getString("account"),
                resultSet.getBigDecimal("amount"),
                new Currency(
                        resultSet.getInt("cur_id"),
                        resultSet.getString("cur_code"),
                        resultSet.getString("cur_name"),
                        resultSet.getString("cur_longname")),
                new Person(
                        resultSet.getInt("per_id"),
                        resultSet.getString("per_name")));
    }
}
