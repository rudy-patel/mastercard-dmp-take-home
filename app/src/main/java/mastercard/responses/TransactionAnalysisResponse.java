package main.java.mastercard.dmp.responses;

public class TransactionAnalysisResponse {
    private String cardNumber;
    private double transactionAmount;
    private String transactionStatus;
    private int cardUsageCount;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public int getCardUsageCount() {
        return cardUsageCount;
    }

    public void setCardUsageCount(int cardUsageCount) {
        this.cardUsageCount = cardUsageCount;
    }
}
