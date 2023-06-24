package com.api.services;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;

import java.util.List;

public interface TransactionAnalysisService {
    TransactionAnalysisResponse analyzeTransaction(Transaction transaction, List<Integer> cardUsageCounts);
}
