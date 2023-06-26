package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionStatus;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for analyzing a transaction.
 */
@Service
public class TransactionAnalysisServiceImpl implements TransactionAnalysisService {

    private static final double FRAUDULENT_AMOUNT_THRESHOLD = 50000.00;

    private static final Logger logger = LoggerFactory.getLogger(TransactionAnalysisServiceImpl.class);

    @Override
    public TransactionAnalysisResponse analyzeTransaction(
        final Transaction transaction, 
        final List<Integer> cardUsageCounts
    ) {
        final double amount = transaction.getAmount();
        int totalCardUsageCount = cardUsageCounts.stream()
                .mapToInt(Integer::intValue)
                .sum();

        boolean isFraudulent = false;

        if (amount > FRAUDULENT_AMOUNT_THRESHOLD) {
            isFraudulent = true;
        } else if (totalCardUsageCount > 60) {
            isFraudulent = true;
        } else if (totalCardUsageCount < 35 && amount / totalCardUsageCount > 500) {
            isFraudulent = true;
        }

        TransactionStatus transactionStatus = isFraudulent ? TransactionStatus.DECLINED : TransactionStatus.APPROVED;

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
    String obfuscateCardNumber(final long cardNum) {
        String cardNumber = String.valueOf(cardNum);
        if (cardNumber.length() > 12) {
            cardNumber = cardNumber.substring(0, 4) + "********" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }

    /**
     * Logs the transaction information, including the obfuscated card number, amount, and card usage count.
     *
     * @param cardNum        The card number associated with the transaction.
     * @param amount         The amount of the transaction.
     * @param cardUsageCount The usage count of the card associated with the transaction.
     */
    private void logTransactionInfo(
        final long cardNum, 
        final double amount, 
        final int cardUsageCount
    ) {
        String obfuscatedCardNumber = obfuscateCardNumber(cardNum);
        logger.debug("Transaction information: Card Number={}, Amount={}, Card Usage Count={}",
                obfuscatedCardNumber, amount, cardUsageCount);
    }
}
