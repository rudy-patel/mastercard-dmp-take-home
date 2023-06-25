package com.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.api.validation.CardNumber;

/**
 * Represents a transaction to be analyzed.
 */
public class Transaction {
    @NotNull(message = "Card number is required")
    @CardNumber
    private Long cardNum;

    @NotNull(message = "Transaction amount is required")
    @Min(value = 0, message = "Transaction amount must be greater than or equal to 0")
    private Double amount;

    /**
     * Retrieves the card number associated with the transaction.
     *
     * @return The card number.
     */
    public long getCardNum() {
        return cardNum;
    }

    /**
     * Sets the card number associated with the transaction.
     *
     * @param cardNum The card number to set.
     */
    public void setCardNum(long cardNum) {
        this.cardNum = cardNum;
    }

    /**
     * Retrieves the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount The transaction amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
