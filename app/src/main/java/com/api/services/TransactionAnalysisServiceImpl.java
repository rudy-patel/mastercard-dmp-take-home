package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionAnalysisServiceImpl implements TransactionAnalysisService {

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

        return response;
    }

    String obfuscateCardNumber(long cardNum) {
        String cardNumber = String.valueOf(cardNum);
        if (cardNumber.length() > 12) {
            cardNumber = cardNumber.substring(0, 4) + "********" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
}
