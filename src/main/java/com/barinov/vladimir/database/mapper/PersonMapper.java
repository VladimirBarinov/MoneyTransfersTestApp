package com.barinov.vladimir.database.mapper;

import com.barinov.vladimir.model.Person;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PersonMapper class
 */
@RegisterMapper(PersonMapper.class)
public class PersonMapper implements ResultSetMapper<Person> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Person map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Person(resultSet.getInt("personid"),
                resultSet.getString("fullname"));
    }
}
