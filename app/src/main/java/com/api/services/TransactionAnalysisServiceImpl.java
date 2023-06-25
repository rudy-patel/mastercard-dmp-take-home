package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for analyzing a transaction.
 */
@Service
public class TransactionAnalysisServiceImpl implements TransactionAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAnalysisServiceImpl.class);

    @Override
    public TransactionAnalysisResponse analyzeTransaction(Transaction transaction, List<Integer> cardUsageCounts) {
        double amount = transaction.getAmount();
        int totalCardUsageCount = cardUsageCounts.stream()
                .mapToInt(Integer::intValue)
                .sum();

        boolean isFraudulent = false;

        if (amount > 50000.00) {
            isFraudulent = true;
        } else if (totalCardUsageCount > 60) {
            isFraudulent = true;
        } else if (totalCardUsageCount < 35 && amount / totalCardUsageCount > 500) {
            isFraudulent = true;
        }

        String transactionStatus = isFraudulent ? "Declined" : "Approved";

        TransactionAnalysisResponse response = new TransactionAnalysisResponse();
        response.setCardNumber(obfuscateCardNumber(transaction.getCardNum()));
        response.setTransactionAmount(amount);
        response.setTransactionStatus(transactionStatus);
        response.setCardUsageCount(totalCardUsageCount);

        // Log transaction information for debugging
        logTransactionInfo(transaction.getCardNum(), amount, totalCardUsageCount);

        return response;
    }

    /**
     * Obfuscates the card number for privacy.
     *
     * @param cardNum The card number to obfuscate.
     * @return The obfuscated card number.
     */
    String obfuscateCardNumber(long cardNum) {
        String cardNumber = String.valueOf(cardNum);
        if (cardNumber.length() > 12) {
            cardNumber = cardNumber.substring(0, 4) + "********" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }

    private void logTransactionInfo(long cardNum, double amount, int cardUsageCount) {
        String obfuscatedCardNumber = obfuscateCardNumber(cardNum);
        logger.debug("Transaction information: Card Number={}, Amount={}, Card Usage Count={}",
                obfuscatedCardNumber, amount, cardUsageCount);
    }
}
