package com.api.models;

/**
 * Represents a response from the transaction analysis API.
 */
public class TransactionAnalysisResponse {
    private String cardNumber;
    private double transactionAmount;
    private String transactionStatus;
    private int cardUsageCount;

    /**
     * Constructs an empty TransactionAnalysisResponse object.
     */
    public TransactionAnalysisResponse() {}

    /**
     * Retrieves the card number associated with the transaction.
     *
     * @return The card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the card number associated with the transaction.
     *
     * @param cardNumber The card number to set.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Retrieves the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param transactionAmount The transaction amount to set.
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Retrieves the status of the transaction.
     *
     * @return The transaction status.
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets the status of the transaction.
     *
     * @param transactionStatus The transaction status to set.
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Retrieves the usage count of the card associated with the transaction.
     *
     * @return The card usage count.
     */
    public int getCardUsageCount() {
        return cardUsageCount;
    }

    /**
     * Sets the usage count of the card associated with the transaction.
     *
     * @param cardUsageCount The card usage count to set.
     */
    public void setCardUsageCount(int cardUsageCount) {
        this.cardUsageCount = cardUsageCount;
    }
}
