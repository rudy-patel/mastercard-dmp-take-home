package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;

import java.util.List;

/**
 * Interface for analyzing a transaction.
 */
public interface TransactionAnalysisService {
    /**
     * Analyzes the given transaction based on the provided card usage counts.
     *
     * @param transaction     The transaction to analyze.
     * @param cardUsageCounts The card usage counts for the card associated with the transaction.
     * @return The analysis response containing the card number, transaction amount, transaction status,
     *         and card usage count.
     */
    TransactionAnalysisResponse analyzeTransaction(final Transaction transaction, final List<Integer> cardUsageCounts);
}
