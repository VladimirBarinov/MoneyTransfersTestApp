package com.barinov.vladimir.database.dao.binder;

import com.barinov.vladimir.model.Account;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

/**
 * Custom JDBI binding for Account
 */
@BindingAnnotation(BindAccount.AccountBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindAccount {
    public static class AccountBinderFactory implements BinderFactory {
        @Override
        public Binder build(Annotation annotation) {
            return new Binder<BindAccount, Account>() {
                @Override
                public void bind(SQLStatement<?> q, BindAccount bind, Account account) {
                    q.bind("accountid", account.getAccountId());
                    q.bind("account", account.getAccountNumber());
                    q.bind("amount", account.getAmount());
                    q.bind("currencyid", account.getCurrency().getCurrencyId());
                    q.bind("personid", account.getOwner().getPersonId());
                }
            };
        }
    }
}
