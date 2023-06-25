package com.api.controllers;

import com.api.models.MonitoringStats;
import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.api.models.TransactionStatus;
import com.api.services.ExternalApiService;
import com.api.services.TransactionAnalysisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling transaction-related requests.
 */
@RestController
@Validated
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionAnalysisService transactionAnalysisService;
    private final ExternalApiService externalApiService;
    private int transactionCount = 0;
    private double totalTransactionAmount = 0.0;
    private int approvedTransactionCount = 0;

    @Autowired
    public TransactionController(
        TransactionAnalysisService transactionAnalysisService,
        ExternalApiService externalApiService
    ) {
        this.transactionAnalysisService = transactionAnalysisService;
        this.externalApiService = externalApiService;
    }

    /**
     * Analyzes a transaction and returns the analysis response.
     *
     * @param request The transaction request.
     * @return The analysis response.
     */
    @PostMapping("/analyzeTransaction")
    public ResponseEntity<TransactionAnalysisResponse> analyzeTransaction(
            @Valid @RequestBody TransactionRequest request) {
        // Increment the transaction count
        transactionCount++;

        Transaction transaction = request.getTransaction();
        long cardNum = transaction.getCardNum();

        List<Integer> cardUsageCounts = new ArrayList<>();
        try {
            cardUsageCounts = externalApiService.fetchCardUsageCounts(cardNum);
        } catch (ServiceUnavailableException e) {
            logger.error("External API service is unavailable", e);
            return ResponseEntity.internalServerError().build();
        } catch (IOException e) {
            logger.error("Error occurred while fetching card usage counts from external API", e);
            return ResponseEntity.internalServerError().build();
        }

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        // Add the transaction amount to the total transaction amount
        totalTransactionAmount += transaction.getAmount();
        if (response.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
            approvedTransactionCount += 1;
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the monitoring statistics.
     *
     * @return The monitoring statistics.
     */
    @GetMapping("/monitoringStats")
    public ResponseEntity<MonitoringStats> getMonitoringStats() {
        MonitoringStats stats = new MonitoringStats();

        // Calculate the percentage of approved transactions
        double percentageApproved = 0.0;
        if (transactionCount > 0) {
            percentageApproved = (double) approvedTransactionCount / transactionCount * 100;
        }

        stats.setPercentageApproved(percentageApproved);
        stats.setTransactionCount(transactionCount);
        stats.setTotalTransactionAmount(totalTransactionAmount);

        return ResponseEntity.ok(stats);
    }

    /**
     * Sets the transaction count.
     *
     * @param transactionCount The transaction count to set.
     */
    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    /**
     * Sets the total transaction amount.
     *
     * @param totalTransactionAmount The total transaction amount to set.
     */
    public void setTotalTransactionAmount(double totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

    /**
     * Sets the approved transaction count.
     *
     * @param approvedTransactionCount The approved transaction count to set.
     */
    public void setApprovedTransactionCount(int approvedTransactionCount) {
        this.approvedTransactionCount = approvedTransactionCount;
    }
}
