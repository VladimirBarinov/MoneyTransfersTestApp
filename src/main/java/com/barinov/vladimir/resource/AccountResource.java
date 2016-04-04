package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.AccountDao;
import com.barinov.vladimir.database.dao.CurrencyDao;
import com.barinov.vladimir.database.dao.PersonDao;
import com.barinov.vladimir.model.Account;
import com.barinov.vladimir.model.Currency;
import com.barinov.vladimir.model.Person;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.params.IntParam;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * AccountResource class
 */
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private static final String ACCOUNT_ID = "accountId";
    private static final String BY_ID = "{accountId}";
    private static final String CREATE = "/create";
    private static final String UPDATE = BY_ID + "/update";
    private static final String DELETE = BY_ID + "/delete";

    private AccountDao accountDao;
    private CurrencyDao currencyDao;
    private PersonDao personDao;

    public AccountResource(AccountDao accountDao, CurrencyDao currencyDao, PersonDao personDao) {
        this.accountDao = accountDao;
        this.currencyDao = currencyDao;
        this.personDao = personDao;
    }

    /**
     * Getting account by id
     *
     * @param accountId account id
     * @return selected account
     */
    @GET
    @Timed
    @Transaction
    @Path(BY_ID)
    public Response getAccount(@PathParam(ACCOUNT_ID) IntParam accountId) {
        if (accountDao.getAccount(accountId.get()) == null) {
            throw new WebApplicationException("Account was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        Account account = accountDao.getAccount(accountId.get());
        return Response.ok(account).build();
    }

    /**
     * Getting all accounts
     *
     * @return all accounts
     */
    @GET
    @Timed
    @Transaction
    public Response getAccounts() {
        return Response.ok(accountDao.getAccounts()).build();
    }

    /**
     * Creating new account
     *
     * @param account account that we're trying to create
     * @return created account
     */
    @POST
    @Timed
    @Transaction
    @Path(CREATE)
    public Response createAccount(@Valid Account account) {
        Currency currency = account.getCurrency();
        Integer currencyId;
        Person owner = account.getOwner();
        Integer ownerId;
        if (currencyDao.getCurrencyIdByCode(currency.getCurrencyCode()) == null) {
            currencyId = currencyDao.createCurrency(currency);
            account.setCurrency(currencyDao.getCurrencyById(currencyId));
        } else {
            currencyId = currencyDao.getCurrencyIdByCode(currency.getCurrencyCode());
            account.getCurrency().setCurrencyId(currencyId);
        }
        if (personDao.getPersonIdByName(owner.getFullname()) == null) {
            ownerId = personDao.createPerson(owner);
            account.setOwner(personDao.getPersonById(ownerId));
        } else {
            ownerId = personDao.getPersonIdByName(owner.getFullname());
            account.getOwner().setPersonId(ownerId);
        }
        Integer createdId = accountDao.createAccount(account);
        return Response.ok(accountDao.getAccount(createdId)).build();
    }

    /**
     * Updating account
     *
     * @param accountId account id from path
     * @param account   account that we're trying to update
     * @return updated account
     */
    @PUT
    @Timed
    @Transaction
    @Path(UPDATE)
    public Response updateAccount(@PathParam(ACCOUNT_ID) IntParam accountId, @Valid Account account) {
        if (accountDao.getAccount(accountId.get()) == null) {
            throw new WebApplicationException("Account was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        account.setAccountId(accountId.get());
        Currency currency = account.getCurrency();
        Integer currencyId;
        Person owner = account.getOwner();
        Integer ownerId;
        if (currencyDao.getCurrencyIdByCode(currency.getCurrencyCode()) == null) {
            currencyId = currencyDao.createCurrency(currency);
            account.setCurrency(currencyDao.getCurrencyById(currencyId));
        } else {
            currency.setCurrencyId(currencyDao.getCurrencyIdByCode(currency.getCurrencyCode()));
            currencyDao.updateCurrency(currency);
        }
        if (personDao.getPersonIdByName(owner.getFullname()) == null) {
            ownerId = personDao.createPerson(owner);
            account.setOwner(personDao.getPersonById(ownerId));
        } else {
            owner.setPersonId(personDao.getPersonIdByName(owner.getFullname()));
            personDao.updatePerson(owner);
        }
        accountDao.updateAccount(account);
        return Response.ok(accountDao.getAccount(accountId.get())).build();
    }

    /**
     * Removing account
     *
     * @param accountId account id
     */
    @DELETE
    @Timed
    @Transaction
    @Path(DELETE)
    public Response deleteAccount(@PathParam(ACCOUNT_ID) IntParam accountId) {
        if (accountDao.getAccount(accountId.get()) == null) {
            throw new WebApplicationException("Account was not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        accountDao.deleteAccount(accountId.get());
        return Response.ok().build();
    }
}
