package com.barinov.vladimir.resource;

import com.barinov.vladimir.database.dao.AccountDao;
import com.barinov.vladimir.database.dao.PersonDao;
import com.barinov.vladimir.database.dao.TransactionDao;
import com.barinov.vladimir.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * Test class for TransferResource class
 */
@RunWith(MockitoJUnitRunner.class)
public class TransferResourceTest {

    //todo: add all necessary tests, hr is asking to commit right now...


    private static final AccountDao accountDao = mock(AccountDao.class);
    private static final PersonDao personDao = mock(PersonDao.class);
    private static final TransactionDao transactionDao = mock(TransactionDao.class);
    private TransferResource transferResource;


    @Before
    public void setUp() {
        transferResource = new TransferResource(accountDao, personDao, transactionDao);
    }

    @After
    public void tearDown() {
        reset(accountDao);
        reset(personDao);
        reset(transactionDao);
        validateMockitoUsage();
    }

    @Test
    public void testSimpleTransfer() {
        Person payer = new Person(1, "John Smith");
        Person payee = new Person(2, "Mary Smith");
        Currency usd_currency = new Currency(1, "840", "USD", "United States Dollar");
        Account payerAccount = new Account(1, "40802840700001400000", BigDecimal.valueOf(10100.0), usd_currency, payer);
        Account payeeAccount = new Account(2, "40802840700001400777", BigDecimal.valueOf(9900), usd_currency, payee);
        Transfer transfer = new Transfer(BigDecimal.valueOf(1000), "John Smith", "Mary Smith", "Testing simple transfer");
        Transaction transaction = new Transaction(transfer.getAmount(),
                payeeAccount.getCurrency(),
                transfer.getPurpose(),
                TransactionState.CREATED,
                payer,
                payee);
        transaction.setOperationId(1);
        Mockito.when(personDao.getPersonIdByName(transfer.getPayer())).thenReturn((1));
        Mockito.when(personDao.getPersonIdByName(transfer.getPayee())).thenReturn((2));
        Mockito.when(accountDao.getAccountByPersonId(payer.getPersonId())).thenReturn(payerAccount);
        Mockito.when(accountDao.getAccountByPersonId(payee.getPersonId())).thenReturn(payeeAccount);
        Mockito.when(transactionDao.getTransaction(any(Integer.class))).thenReturn(transaction);
        Response response = transferResource.createTransfer(transfer);
        transaction = (Transaction) response.getEntity();
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

    }
}
