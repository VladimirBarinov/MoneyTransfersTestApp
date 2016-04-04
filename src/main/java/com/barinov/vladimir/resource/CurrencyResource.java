package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.CurrencyDao;
import com.barinov.vladimir.model.Currency;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.params.IntParam;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * CurrencyResource class
 */
@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyResource {
    private static final String CURRENCY_ID = "currencyId";
    private static final String BY_ID = "{currencyId}";
    private static final String CREATE = "/create";
    private static final String UPDATE = BY_ID + "/update";
    private static final String DELETE = BY_ID + "/delete";

    private final CurrencyDao currencyDao;

    public CurrencyResource(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    /**
     * Getting currency by id
     *
     * @param currencyId currency id
     * @return selected currency
     */
    @GET
    @Timed
    @Transaction
    @Path(BY_ID)
    public Response getCurrencyById(@PathParam(CURRENCY_ID) IntParam currencyId) {
        if (currencyDao.getCurrencyById(currencyId.get()) == null) {
            throw new WebApplicationException("Currency was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        return Response.ok(currencyDao.getCurrencyById(currencyId.get())).build();
    }

    /**
     * Getting all currencies
     *
     * @return all currencies
     */
    @GET
    @Timed
    @Transaction
    public Response getCurrencies() {
        return Response.ok(currencyDao.getCurrencies()).build();
    }

    /**
     * Creating new currency
     *
     * @param currency currency that we're trying to create
     * @return created currency
     */
    @POST
    @Timed
    @Transaction
    @Path(CREATE)
    public Response createCurrency(@Valid Currency currency) throws Exception {
        if (currencyDao.getCurrencyIdByCode(currency.getCurrencyCode()) != null) {
            throw new Exception("Currency already exists");
        }
        Integer createdId = currencyDao.createCurrency(currency);
        return Response.ok(currencyDao.getCurrencyById(createdId)).build();
    }

    /**
     * Updating currency
     *
     * @param currencyId currency id from path
     * @param currency currency that we're trying to update
     * @return updated currency
     */
    @PUT
    @Timed
    @Transaction
    @Path(UPDATE)
    public Response updateCurrency(@PathParam(CURRENCY_ID) IntParam currencyId, @Valid Currency currency) throws Exception {
        if (currencyDao.getCurrencyById(currencyId.get()) == null) {
            throw new WebApplicationException("Currency was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        currency.setCurrencyId(currencyId.get());
        currencyDao.updateCurrency(currency);
        return Response.ok(currencyDao.getCurrencyById(currencyId.get())).build();
    }

    /**
     * Removing currency
     *
     * @param currencyId currency if from path
     */
    @DELETE
    @Timed
    @Transaction
    @Path(DELETE)
    public Response deleteCurrency(@PathParam(CURRENCY_ID) IntParam currencyId) {
        if (currencyDao.getCurrencyById(currencyId.get()) == null) {
            throw new WebApplicationException("Currency was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        currencyDao.deleteCurrency(currencyId.get());
        return Response.ok().build();
    }
}
