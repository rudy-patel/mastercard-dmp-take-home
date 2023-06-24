package com.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Transaction {
    @NotNull(message = "Card number is required")
    private long cardNum;

    @NotNull(message = "Transaction amount is required")
    @Min(value = 0, message = "Transaction amount must be greater than or equal to 0")
    private double amount;

    public long getCardNum() {
        return cardNum;
    }

    public void setCardNum(long cardNum) {
        this.cardNum = cardNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
