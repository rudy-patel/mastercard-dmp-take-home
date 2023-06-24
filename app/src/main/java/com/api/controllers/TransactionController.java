package com.api.controllers;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.api.services.ExternalApiService;

@RestController
@Validated
public class TransactionController {

    @Autowired
    private final ExternalApiService externalApiService;

    public TransactionController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @PostMapping("/analyzeTransaction")
    public ResponseEntity<?> analyzeTransaction(
        final @Valid @RequestBody TransactionRequest request
    ) {
        System.out.println("analyzeTransaction: entry");

        System.out.println("analyzeTransaction: parsing request");
        final Transaction transaction = request.getTransaction();
        final long cardNum = transaction.getCardNum();
        final double amount = transaction.getAmount();

        System.out.println("analyzeTransaction: Card num: " + cardNum);
        System.out.println("analyzeTransaction: Amount: " + amount);

        // Fetch the number of times the card has been used in the last 7 days
        List<Integer> cardUsageCounts = externalApiService.fetchCardUsageCounts(cardNum);
        System.out.println("analyzeTransaction: card usage counts: " + cardUsageCounts);
        
        // Calculate the total number of times the card has been used in the last 7 days
        int totalCardUsageCount = cardUsageCounts.stream()
        .mapToInt(Integer::intValue)
        .sum();
        System.out.println("analyzeTransaction: total card usage count: " + totalCardUsageCount);

        // Implement the fraud detection logic
        boolean isFraudulent = false;

        if (amount > 50000.00) {
            isFraudulent = true;
        } else if (totalCardUsageCount > 60) {
            isFraudulent = true;
        } else if (totalCardUsageCount < 35 && amount / totalCardUsageCount > 500) {
            isFraudulent = true;
        }

        String transactionStatus = isFraudulent ? "Declined" : "Approved";

        // Create an instance of TransactionAnalysisResponse and populate it with the relevant information
        TransactionAnalysisResponse response = new TransactionAnalysisResponse();
        response.setCardNumber(obfuscateCardNumber(cardNum));
        response.setTransactionAmount(amount);
        response.setTransactionStatus(transactionStatus);
        response.setCardUsageCount(totalCardUsageCount);

        System.out.println("analyzeTransaction: returning response: " + response);
        
        // Return the TransactionAnalysisResponse as the response entity
        return ResponseEntity.ok(response);
    }

    private String obfuscateCardNumber(long cardNum) {  // change param name, confusing
        System.out.println("obfuscateCardNumber: entry");
        String cardNumber = String.valueOf(cardNum);
        if (cardNumber.length() > 12) {
            cardNumber = cardNumber.substring(0, 4) + "********" + cardNumber.substring(cardNumber.length() - 4);
        }

        System.out.println("obfuscateCardNumber: returning obfusacated cardNumber: " + cardNumber);
        return cardNumber;
    }
}
