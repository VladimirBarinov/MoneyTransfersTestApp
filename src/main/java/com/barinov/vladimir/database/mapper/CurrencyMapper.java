package com.barinov.vladimir.database.mapper;

import com.barinov.vladimir.model.Currency;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CurrencyMapper class
 */
@RegisterMapper(CurrencyMapper.class)
public class CurrencyMapper implements ResultSetMapper<Currency> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Currency map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Currency(resultSet.getInt("currencyid"),
                resultSet.getString("code"),
                resultSet.getString("isocode"),
                resultSet.getString("longname"));
    }
}
