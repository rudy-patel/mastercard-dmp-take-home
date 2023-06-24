package com.api.models;

import javax.validation.Valid;

import org.jetbrains.annotations.NotNull;

public class TransactionRequest {

    @Valid
    @NotNull
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
