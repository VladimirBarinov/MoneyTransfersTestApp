package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.TransactionDao;
import com.barinov.vladimir.model.TransactionState;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.params.IntParam;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * TransactionResource class
 */
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private static final String TRANSACTION_ID = "transactionId";
    private static final String BY_ID = "{transactionId}";
    private static final String UPDATE = BY_ID + "/update";

    private TransactionDao transactionDao;

    public TransactionResource(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    /**
     * Getting transaction by id
     *
     * @param transactionId transaction id
     * @return transaction
     */
    @GET
    @Timed
    @Transaction
    @Path(BY_ID)
    public Response getTransaction(@PathParam(TRANSACTION_ID) int transactionId) {
        com.barinov.vladimir.model.Transaction transaction = transactionDao.getTransaction(transactionId);
        return Response.ok(transaction).build();
    }

    /**
     * Getting all transactions
     *
     * @return all transactions
     */
    @GET
    @Timed
    @Transaction
    public Response getTransactions() {
        return Response.ok(transactionDao.getTransactions()).build();
    }

    /**
     * Updating transaction state after some manipulations using other services (after sending correct SMS etc.)
     *
     * @param transactionId
     * @param transaction
     * @return
     */
    @PUT
    @Timed
    @Transaction
    @Path(UPDATE)
    public Response updateState(@PathParam(TRANSACTION_ID) IntParam transactionId, @Valid com.barinov.vladimir.model.Transaction transaction) {
        if (transactionDao.getTransaction(transactionId.get()) == null) {
            throw new WebApplicationException("Transaction not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        if (!checkState(transaction.getOperationState())) {
            throw new WebApplicationException("Incorrect transaction state!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        transactionDao.updateTransactionState(transactionId.get(), transaction.getOperationState());
        return Response.ok().build();
    }

    public boolean checkState(String value) {
        for (TransactionState st : TransactionState.class.getEnumConstants()) {
            if (st.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
