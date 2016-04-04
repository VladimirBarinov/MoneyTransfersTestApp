package com.barinov.vladimir.database.dao.binder;

import com.barinov.vladimir.model.Transaction;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Custom JDBI binding for Transaction
 */
@BindingAnnotation(BindTransaction.TransactionBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTransaction {
    public static class TransactionBinderFactory implements BinderFactory {
        @Override
        public Binder build(Annotation annotation) {
            return new Binder<BindTransaction, Transaction>() {
                @Override
                public void bind(SQLStatement<?> q, BindTransaction bind, Transaction transaction) {
                    q.bind("transactionid", transaction.getOperationId());
                    q.bind("payerid", transaction.getPayer().getPersonId());
                    q.bind("payeeid", transaction.getPayee().getPersonId());
                    q.bind("amount", transaction.getOperationAmount());
                    q.bind("currencyid", transaction.getOperationCurrency().getCurrencyId());
                    q.bind("date", transaction.getOperationDate());
                    q.bind("status", transaction.getOperationState());
                    q.bind("purpose", transaction.getOperationPurpose());
                }
            };
        }
    }
}
