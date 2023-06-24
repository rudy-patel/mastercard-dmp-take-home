package com.api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAnalysisResponseTest {

    @Test
    void whenGettersAndSettersCalled_shouldReturnCorrectValues() {
        // Arrange
        String cardNumber = "1234567890123456";
        double transactionAmount = 100.0;
        String transactionStatus = "Success";
        int cardUsageCount = 5;

        TransactionAnalysisResponse response = new TransactionAnalysisResponse();

        // Act
        response.setCardNumber(cardNumber);
        response.setTransactionAmount(transactionAmount);
        response.setTransactionStatus(transactionStatus);
        response.setCardUsageCount(cardUsageCount);

        // Assert
        assertEquals(cardNumber, response.getCardNumber());
        assertEquals(transactionAmount, response.getTransactionAmount());
        assertEquals(transactionStatus, response.getTransactionStatus());
        assertEquals(cardUsageCount, response.getCardUsageCount());
    }
}
