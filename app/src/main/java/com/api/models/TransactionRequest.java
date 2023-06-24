package com.api.models;

// import javax.validation.constraints.Min;
import org.jetbrains.annotations.NotNull;

public class TransactionRequest {
    // @NotNull(value = "Card number is required")
    // private Long cardNum;

    // @NotNull(value = "Transaction amount is required")
    // @Min(value = 0, message = "Transaction amount must be greater than or equal to 0")
    // private Double amount;

    @NotNull(value = "A transaction is required")
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // public long getCardNum() {
    //     return cardNum;
    // }

    // public void setCardNum(long cardNum) {
    //     this.cardNum = cardNum;
    // }

    // public double getAmount() {
    //     return amount;
    // }

    // public void setAmount(double amount) {
    //     this.amount = amount;
    // }
}