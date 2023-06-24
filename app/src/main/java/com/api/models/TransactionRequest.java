package com.api.models;

import org.jetbrains.annotations.NotNull;

public class TransactionRequest {

    @NotNull(value = "A transaction is required")
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
