package com.api.models;

public class MonitoringStats {
    private int transactionCount;
    private double totalTransactionAmount;
    private double percentageApproved;

    public double getPercentageApproved() {
        return percentageApproved;
    }

    public void setPercentageApproved(double percentageApproved) {
        this.percentageApproved = percentageApproved;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public double getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(double totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }
}
