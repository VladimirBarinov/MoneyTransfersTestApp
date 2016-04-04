package com.barinov.vladimir.database.dao;

import com.barinov.vladimir.database.mapper.PersonMapper;
import com.barinov.vladimir.model.Person;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * PersonDao class
 */
@RegisterMapper(PersonMapper.class)
public interface PersonDao {
    String CREATE_PERSONS_TABLE_QUERY = "CREATE TABLE persons (" +
            "personid BIGINT NOT NULL AUTO_INCREMENT, " +
            "fullname VARCHAR(100) NOT NULL, " +
            "PRIMARY KEY (personid)" +
            ");";

    String FILL_PERSONS_TABLE_QUERY = "INSERT INTO persons (fullname) VALUES " +
            "('Vladimir Barinov'), " + //id 1
            "('John Doe'), " +         //id 2
            "('Martin Freeman')" +     //id 3
            ";";

    String CREATE_ACCOUNT_QUERY = "INSERT INTO persons (fullname) VALUES " +
            "(:person.fullname);";

    String SELECT_PERSON_BY_ID_QUERY = "SELECT * FROM persons WHERE " +
            "personid = :personId;";

    String SELECT_PERSON_BY_NAME_QUERY =  "SELECT personid FROM persons WHERE " +
            "fullname = :fullname;";

    String GET_ALL_PERSONS_QUERY = "SELECT * FROM persons;";

    String UPDATE_PERSON_QUERY = "UPDATE persons SET " +
            "fullname = :person.fullname WHERE " +
            "personid = :person.personId;";

    String DELETE_PERSON_QUERY = "DELETE FROM persons WHERE " +
            "personid = :personId;";
    /**
     * Creating 'PERSONS' table
     */
    @SqlUpdate(CREATE_PERSONS_TABLE_QUERY)
    void createPersonsTable();

    /**
     * Filling 'PERSONS' table with some data
     */
    @SqlUpdate(FILL_PERSONS_TABLE_QUERY)
    void fillPersonsTable();

    /**
     * Creating new person
     *
     * @param person person that we're trying to create
     * @return id of the created person
     */
    @SqlUpdate(CREATE_ACCOUNT_QUERY)
    @GetGeneratedKeys
    @Transaction
    Integer createPerson(@BindBean("person") Person person);

    /**
     * Getting person by id
     *
     * @param personId person id
     * @return selected person
     */
    @SqlQuery(SELECT_PERSON_BY_ID_QUERY)
    Person getPersonById(@Bind("personId") Integer personId);

    /**
     * Getting person id by name
     *
     * @param fullname name
     * @return person id
     */
    @SqlQuery(SELECT_PERSON_BY_NAME_QUERY)
    Integer getPersonIdByName(@Bind("fullname") String fullname);

    /**
     * Getting all persons
     *
     * @return all persons
     */
    @SqlQuery(GET_ALL_PERSONS_QUERY)
    List<Person> getPersons();

    /**
     * Updating person
     *
     * @param person person that we're trying to update
     */
    @SqlUpdate(UPDATE_PERSON_QUERY)
    void updatePerson(@BindBean("person") Person person);

    /**
     * Removing person by id
     *
     * @param personid person id
     */
    @SqlUpdate(DELETE_PERSON_QUERY)
    void deletePerson(@Bind("personId") Integer personid);
}
