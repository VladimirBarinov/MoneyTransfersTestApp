package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.PersonDao;
import com.barinov.vladimir.model.Person;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.params.IntParam;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * PersonResource class
 */
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private static final String PERSON_ID = "personId";
    private static final String BY_ID = "{personId}";
    private static final String CREATE = "/create";
    private static final String UPDATE = BY_ID + "/update";
    private static final String DELETE = BY_ID + "/delete";

    private PersonDao personDao;

    public PersonResource(PersonDao personDao) {
        this.personDao = personDao;
    }

    /**
     * Getting person by id
     *
     * @param personId person id
     * @return selected person
     */
    @GET
    @Timed
    @Transaction
    @Path(BY_ID)
    public Response getPersonById(@PathParam(PERSON_ID) IntParam personId) {
        if (personDao.getPersonById(personId.get()) == null) {
            throw new WebApplicationException("Person was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        return Response.ok(personDao.getPersonById(personId.get())).build();
    }

    /**
     * Getting all persons
     *
     * @return all persons
     */
    @GET
    @Timed
    @Transaction
    public Response getPersons() {
        return Response.ok(personDao.getPersons()).build();
    }

    /**
     * Creating person
     *
     * @param person person that we're trying to create
     * @return created person
     */
    @POST
    @Timed
    @Transaction
    @Path(CREATE)
    public Response createPerson(@Valid Person person) throws Exception {
        String name = person.getFullname();
        if (StringUtils.isEmpty(name)) {
            throw new WebApplicationException("Error in the name - the name cannot be empty or null!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        if (personDao.getPersonIdByName(name) != null) {
            throw new WebApplicationException("Person already exists!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }

        Integer createdId = personDao.createPerson(person);
        return Response.ok(personDao.getPersonById(createdId)).build();

    }

    /**
     * Updating person
     *
     * @param personId person id from path
     * @param person person that we're trying to update
     * @return updated person
     */
    @PUT
    @Timed
    @Transaction
    @Path(UPDATE)
    public Response updatePerson(@PathParam(PERSON_ID) IntParam personId, @Valid Person person) throws Exception {
        if (personDao.getPersonById(personId.get()) == null) {
            throw new WebApplicationException("Person was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        String name = person.getFullname();
        if (StringUtils.isEmpty(name)) {
            throw new Exception("Error in the name - the name cannot be empty or null!");
        }
        person.setPersonId(personId.get());
        personDao.updatePerson(person);
        return Response.ok(personDao.getPersonById(personId.get())).build();
    }

    /**
     * Removing person
     *
     * @param personId person id from path
     */
    @DELETE
    @Timed
    @Transaction
    @Path(DELETE)
    public Response deletePerson(@PathParam(PERSON_ID) IntParam personId) {
        if (personDao.getPersonById(personId.get()) == null) {
            throw new WebApplicationException("Person was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        personDao.deletePerson(personId.get());
        return Response.ok().build();
    }
}
