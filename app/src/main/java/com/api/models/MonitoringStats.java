package com.api.models;

/**
 * Represents the monitoring statistics of transactions.
 */
public class MonitoringStats {
    private int transactionCount;
    private double totalTransactionAmount;
    private double percentageApproved;

    /**
     * Retrieves the percentage of approved transactions.
     *
     * @return The percentage of approved transactions.
     */
    public double getPercentageApproved() {
        return percentageApproved;
    }

    /**
     * Sets the percentage of approved transactions.
     *
     * @param percentageApproved The percentage of approved transactions to set.
     */
    public void setPercentageApproved(final double percentageApproved) {
        this.percentageApproved = percentageApproved;
    }

    /**
     * Retrieves the total count of transactions.
     *
     * @return The transaction count.
     */
    public int getTransactionCount() {
        return transactionCount;
    }

    /**
     * Sets the total count of transactions.
     *
     * @param transactionCount The transaction count to set.
     */
    public void setTransactionCount(final int transactionCount) {
        this.transactionCount = transactionCount;
    }

    /**
     * Retrieves the total transaction amount.
     *
     * @return The total transaction amount.
     */
    public double getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    /**
     * Sets the total transaction amount.
     *
     * @param totalTransactionAmount The total transaction amount to set.
     */
    public void setTotalTransactionAmount(final double totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }
}
