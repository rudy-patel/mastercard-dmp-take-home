package com.api.controllers;

import com.api.models.MonitoringStats;
import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.api.services.ExternalApiService;
import com.api.services.TransactionAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.naming.ServiceUnavailableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Mock
    private TransactionAnalysisService transactionAnalysisService;

    @Mock
    private ExternalApiService externalApiService;

    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController(transactionAnalysisService, externalApiService);
    }

    @Test
    public void testAnalyzeTransaction_WithValidTransaction_ReturnsApprovedResponse() throws ServiceUnavailableException, IOException {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);
        request.setTransaction(transaction);

        List<Integer> cardUsageCounts = Collections.singletonList(5);
        when(externalApiService.fetchCardUsageCounts(anyLong())).thenReturn(cardUsageCounts);

        TransactionAnalysisResponse analysisResponse = new TransactionAnalysisResponse();
        analysisResponse.setTransactionStatus("Approved");
        when(transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts)).thenReturn(analysisResponse);

        // Act
        ResponseEntity<TransactionAnalysisResponse> responseEntity = transactionController.analyzeTransaction(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        TransactionAnalysisResponse response = responseEntity.getBody();
        assertEquals("Approved", response.getTransactionStatus());
        verify(externalApiService, times(1)).fetchCardUsageCounts(anyLong());
        verify(transactionAnalysisService, times(1)).analyzeTransaction(transaction, cardUsageCounts);
    }

    @Test
    public void testAnalyzeTransaction_WithFraudulentTransaction_ReturnsDeclinedResponse() throws ServiceUnavailableException, IOException {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(60000.0);
        request.setTransaction(transaction);

        List<Integer> cardUsageCounts = Collections.singletonList(70);
        when(externalApiService.fetchCardUsageCounts(anyLong())).thenReturn(cardUsageCounts);

        TransactionAnalysisResponse analysisResponse = new TransactionAnalysisResponse();
        analysisResponse.setTransactionStatus("Declined");
        when(transactionAnalysisService.analyzeTransaction(transaction, cardUsageCounts)).thenReturn(analysisResponse);

        // Act
        ResponseEntity<TransactionAnalysisResponse> responseEntity = transactionController.analyzeTransaction(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        TransactionAnalysisResponse response = responseEntity.getBody();
        assertEquals("Declined", response.getTransactionStatus());
        verify(externalApiService, times(1)).fetchCardUsageCounts(anyLong());
        verify(transactionAnalysisService, times(1)).analyzeTransaction(transaction, cardUsageCounts);
    }

    @Test
    public void testAnalyzeTransaction_externalApiServiceThrowsException_internalServerError() throws ServiceUnavailableException, IOException {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);
        request.setTransaction(transaction);

        when(externalApiService.fetchCardUsageCounts(anyLong())).thenThrow(ServiceUnavailableException.class);

        // Act
        ResponseEntity<TransactionAnalysisResponse> responseEntity = transactionController.analyzeTransaction(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(externalApiService, times(1)).fetchCardUsageCounts(anyLong());
        verify(transactionAnalysisService, never()).analyzeTransaction(any(Transaction.class), any(List.class));
    }

    @Test
    public void testAnalyzeTransaction_externalApiServiceThrowsIOException_internalServerError() throws ServiceUnavailableException, IOException {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);
        request.setTransaction(transaction);

        when(externalApiService.fetchCardUsageCounts(anyLong())).thenThrow(IOException.class);

        // Act
        ResponseEntity<TransactionAnalysisResponse> responseEntity = transactionController.analyzeTransaction(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(externalApiService, times(1)).fetchCardUsageCounts(anyLong());
        verify(transactionAnalysisService, never()).analyzeTransaction(any(Transaction.class), any(List.class));
    }

    @Test
    public void testGetMonitoringStats_ReturnsCorrectStats() {
        // Arrange
        transactionController.setTransactionCount(10);
        transactionController.setTotalTransactionAmount(1000.50);
        transactionController.setApprovedTransactionCount(8);

        // Act
        ResponseEntity<MonitoringStats> responseEntity = transactionController.getMonitoringStats();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MonitoringStats stats = responseEntity.getBody();
        assertEquals(10, stats.getTransactionCount());
        assertEquals(1000.50, stats.getTotalTransactionAmount());
        assertEquals(80.0, stats.getPercentageApproved());
    }
}
