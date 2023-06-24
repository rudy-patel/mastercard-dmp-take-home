package com.api.controllers;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.api.services.ExternalApiService;
import com.api.services.TransactionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class TransactionController {

    private final TransactionAnalysisService transactionAnalysisService;
    private final ExternalApiService externalApiService;

    @Autowired
    public TransactionController(
        TransactionAnalysisService transactionAnalysisService,
        ExternalApiService externalApiService
    ) {
        this.transactionAnalysisService = transactionAnalysisService;
        this.externalApiService = externalApiService;
    }

    @PostMapping("/analyzeTransaction")
    public ResponseEntity<TransactionAnalysisResponse> analyzeTransaction(
            @Valid @RequestBody TransactionRequest request) {

        Transaction transaction = request.getTransaction();
        long cardNum = transaction.getCardNum();

        List<Integer> cardUsageCounts = externalApiService.fetchCardUsageCounts(cardNum);

        TransactionAnalysisResponse response = transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts);

        return ResponseEntity.ok(response);
    }
}
