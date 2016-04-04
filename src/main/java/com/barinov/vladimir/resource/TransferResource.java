package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.AccountDao;
import com.barinov.vladimir.database.dao.CurrencyDao;
import com.barinov.vladimir.database.dao.PersonDao;
import com.barinov.vladimir.database.dao.TransactionDao;
import com.barinov.vladimir.model.Account;
import com.barinov.vladimir.model.Person;
import com.barinov.vladimir.model.TransactionState;
import com.barinov.vladimir.model.Transfer;
import com.barinov.vladimir.utils.CurrencyConverter;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;

/**
 * TransferResource class
 */
@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {
    private static final String CREATE = "/create";

    private PersonDao personDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public TransferResource(AccountDao accountDao, PersonDao personDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.personDao = personDao;
        this.transactionDao = transactionDao;
    }

    /**
     * Transfer money from one user to another
     *
     * @param transfer transfer information
     * @return new transaction
     */
    @POST
    @Timed
    @Transaction
    @Path(CREATE)
    public Response createTransfer(@Valid Transfer transfer) {

        if (personDao.getPersonIdByName(transfer.getPayer()) == null) {
            throw new WebApplicationException("Payer not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        Person payer = new Person(personDao.getPersonIdByName(transfer.getPayer()), transfer.getPayer());

        if (personDao.getPersonIdByName(transfer.getPayee()) == null) {
            throw new WebApplicationException("Payee not found!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        Person payee = new Person(personDao.getPersonIdByName(transfer.getPayee()), transfer.getPayee());

        Account payerAccount = accountDao.getAccountByPersonId(payer.getPersonId());
        Account payeeAccount = accountDao.getAccountByPersonId(payee.getPersonId());

        String payerCurrencyCode = payerAccount.getCurrency().getCurrencyCode();
        String payeeCurrencyCode = payeeAccount.getCurrency().getCurrencyCode();
        if (StringUtils.isBlank(payerCurrencyCode) || StringUtils.isBlank(payeeCurrencyCode)) {
            throw new WebApplicationException("Incorrect currency codes!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }
        BigDecimal exchangeRate = BigDecimal.valueOf(1.0); //default rate
        if (!payerCurrencyCode.equals(payeeCurrencyCode)) {
            exchangeRate = CurrencyConverter.convert(payerCurrencyCode, payeeCurrencyCode);
        }

        BigDecimal payerAmount = payerAccount.getAmount();
        BigDecimal payeeAmount = payeeAccount.getAmount();
        BigDecimal operationAmount = transfer.getAmount();
        if ((payerAmount.subtract(operationAmount)).compareTo(BigDecimal.ZERO) < 0) {
            throw new WebApplicationException("Payer has not enough money!", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }

        payerAccount.setAmount(payerAmount.subtract(operationAmount));
        payeeAccount.setAmount(payeeAmount.add((operationAmount.multiply(exchangeRate, new MathContext(2)))));
        accountDao.updateAmount(payerAccount);
        accountDao.updateAmount(payeeAccount);

        com.barinov.vladimir.model.Transaction transaction = new com.barinov.vladimir.model.Transaction(operationAmount,
                payeeAccount.getCurrency(),
                transfer.getPurpose(),
                TransactionState.CREATED,
                payer,
                payee
                );
        Integer transactionId = transactionDao.createTransaction(transaction);
        transaction = transactionDao.getTransaction(transactionId);
        URI transactionResource = URI.create(String.format("/transactions/%d", transaction.getOperationId()));
        return Response.created(transactionResource).entity(transaction).status(Response.Status.CREATED).build();
    }
}
