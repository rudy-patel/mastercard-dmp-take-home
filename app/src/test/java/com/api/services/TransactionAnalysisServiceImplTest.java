package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionAnalysisServiceImplTest {

    private TransactionAnalysisService transactionAnalysisService;

    @BeforeEach
    public void setup() {
        transactionAnalysisService = new TransactionAnalysisServiceImpl();
    }

    @Test
    public void testAnalyzeTransaction_ValidTransaction_ReturnsApprovedResponse() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);
        List<Integer> cardUsageCounts = Arrays.asList(5, 5, 5, 5, 5, 5, 5);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        assertEquals(TransactionStatus.APPROVED, response.getTransactionStatus());
        assertEquals("1234********3456", response.getCardNumber());
        assertEquals(100.0, response.getTransactionAmount());
        assertEquals(35, response.getCardUsageCount());
    }

    @Test
    public void testAnalyzeTransaction_AmountOverThreshold_ReturnsDeclinedResponse() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(60000.0);
        List<Integer> cardUsageCounts = Arrays.asList(10, 15, 20, 10, 5, 15, 20);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        assertEquals(TransactionStatus.DECLINED, response.getTransactionStatus());
        assertEquals("1234********3456", response.getCardNumber());
        assertEquals(60000.0, response.getTransactionAmount());
        assertEquals(95, response.getCardUsageCount());
    }

    @Test
    public void testAnalyzeTransaction_CardUsageOverThreshold_ReturnsDeclinedResponse() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);
        List<Integer> cardUsageCounts = Arrays.asList(70, 15, 20, 10, 5, 15, 20);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        assertEquals(TransactionStatus.DECLINED, response.getTransactionStatus());
        assertEquals("1234********3456", response.getCardNumber());
        assertEquals(100.0, response.getTransactionAmount());
        assertEquals(155, response.getCardUsageCount());
    }

    @Test
    public void testAnalyzeTransaction_CardUsageUnderThresholdAndAmountRatioOverThreshold_ReturnsDeclinedResponse() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(9000.0);
        List<Integer> cardUsageCounts = Arrays.asList(2, 0, 0, 0, 0, 0, 0);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        assertEquals(TransactionStatus.DECLINED, response.getTransactionStatus());
        assertEquals("1234********3456", response.getCardNumber());
        assertEquals(9000.0, response.getTransactionAmount());
        assertEquals(2, response.getCardUsageCount());
    }

    @Test
    public void testAnalyzeTransaction_CardUsageUnderThresholdAndAmountRatioUnderThreshold_ReturnsApprovedResponse() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(2000.0);
        List<Integer> cardUsageCounts = Arrays.asList(2, 5, 8, 3, 4, 1, 7);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        assertEquals(TransactionStatus.APPROVED, response.getTransactionStatus());
        assertEquals("1234********3456", response.getCardNumber());
        assertEquals(2000.0, response.getTransactionAmount());
        assertEquals(30, response.getCardUsageCount());
    }

    @Test
    public void testObfuscateCardNumber_LongCardNumber_ReturnsObfuscatedCardNumber() {
        TransactionAnalysisServiceImpl transactionAnalysisService = new TransactionAnalysisServiceImpl();
        long cardNumber = 1234567890123456L;

        String obfuscatedCardNumber = transactionAnalysisService.obfuscateCardNumber(cardNumber);

        assertEquals("1234********3456", obfuscatedCardNumber);
    }

    @Test
    public void testObfuscateCardNumber_ShortCardNumber_ReturnsObfuscatedCardNumber() {
        TransactionAnalysisServiceImpl transactionAnalysisService = new TransactionAnalysisServiceImpl();
        long cardNumber = 123456789L;

        String obfuscatedCardNumber = transactionAnalysisService.obfuscateCardNumber(cardNumber);

        assertEquals("123456789", obfuscatedCardNumber);
    }
}
