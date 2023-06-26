package com.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAnalysisResponseTest {

    @Test
    void whenGettersAndSettersCalled_shouldReturnCorrectValues() {
        String cardNumber = "1234567890123456";
        double transactionAmount = 100.0;
        int cardUsageCount = 5;

        TransactionAnalysisResponse response = new TransactionAnalysisResponse();

        response.setCardNumber(cardNumber);
        response.setTransactionAmount(transactionAmount);
        response.setTransactionStatus(TransactionStatus.APPROVED);
        response.setCardUsageCount(cardUsageCount);

        assertEquals(cardNumber, response.getCardNumber());
        assertEquals(transactionAmount, response.getTransactionAmount());
        assertEquals(TransactionStatus.APPROVED, response.getTransactionStatus());
        assertEquals(cardUsageCount, response.getCardUsageCount());
    }
}
