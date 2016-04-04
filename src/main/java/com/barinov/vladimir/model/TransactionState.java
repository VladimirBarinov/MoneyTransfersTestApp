package com.barinov.vladimir.model;

/**
 * Transaction states
 */
public enum TransactionState {
    /**
     * The condition means that transaction was created in the system
     */
    CREATED,
    /**
     * The condition means that this operation was confirmed (sms confirmation etc.)
     */
    CONFIRMED,
    /**
     * The condition means that payer pushed the 'submit' button
     */
    COMMITED,
    /**
     * The condition means that something was wrong during operation
     */
    FAILED,
    /**
     * The condition means that payer rejected the payment
     */
    REJECTED


}
