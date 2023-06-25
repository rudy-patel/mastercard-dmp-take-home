package com.api.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Represents a request object for a transaction.
 */
public class TransactionRequest {

    @Valid
    @NotNull
    private Transaction transaction;

    /**
     * Retrieves the transaction object.
     *
     * @return The transaction object.
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the transaction object.
     *
     * @param transaction The transaction object to set.
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
