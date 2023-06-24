package com.api.controllers;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.api.services.ExternalApiService;
import com.configuration.TestConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfiguration.class)
// @RunWith(MockitoJUnitRunner.class)
public class TransactionControllerIntegrationTest {
    private static final int VALID_AMOUNT = 5000;
    private static final double AMOUNT_OVER_LIMIT = 50000.1;
    private static final Integer VALID_CARD_USAGE_COUNT = 40;
    private static final String DECLINED = "Declined";
    private static final String APPROVED = "Approved";
    private static final long CARD_NUM = 5206840000000001L;
    private static final String OBFUSCATED_CARD_NUM = "5206********0001";
    
    private TransactionRequest request;
    private Transaction transaction;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExternalApiService testExternalApiService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        request = new TransactionRequest();
        objectMapper = new ObjectMapper();
        transaction = new Transaction();
        transaction.setCardNum(CARD_NUM);
        request.setTransaction(transaction);

        // Set the mock implementation of ExternalApiService
        when(testExternalApiService.fetchCardUsageCounts(anyLong()))
        .thenReturn(Collections.singletonList(VALID_CARD_USAGE_COUNT));
    }

    @Test
    public void testAnalyzeTransaction_happyCase_TransactionSuccessful() throws Exception {
        // Prepare the request payload
        transaction.setAmount(VALID_AMOUNT);

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();

        // Validate the response
        assertEquals(OBFUSCATED_CARD_NUM, response.getCardNumber());
        assertEquals(VALID_AMOUNT, response.getTransactionAmount());
        assertEquals(APPROVED, response.getTransactionStatus());
    }

    @Test
    public void testAnalyzeTransaction_AmountOverLimit_TransactionDeclined() throws Exception {
        // Prepare the request payload
        transaction.setAmount(AMOUNT_OVER_LIMIT);

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();
        
        // Validate the response
        assertEquals(OBFUSCATED_CARD_NUM, response.getCardNumber());
        assertEquals(AMOUNT_OVER_LIMIT, response.getTransactionAmount());
        assertEquals(DECLINED, response.getTransactionStatus());
    }

    @Test
    public void testAnalyzeTransaction_cardUsageTooLow_transactionDeclined() throws Exception {
        // Prepare the request payload
        transaction.setAmount(VALID_AMOUNT);
        int cardUsageTooLow = 30;

        // Configure the mock behavior
        when(testExternalApiService.fetchCardUsageCounts(anyLong()))
                .thenReturn(Collections.singletonList(cardUsageTooLow));

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();

        // Validate the response
        assertEquals(DECLINED, response.getTransactionStatus());
    }

    @Test
    public void testAnalyzeTransaction_cardUsageTooHigh_transactionDeclined() throws Exception {
        // Prepare the request payload
        transaction.setAmount(VALID_AMOUNT);
        int cardUsageTooHigh = 70;

        // Configure the mock behavior
        when(testExternalApiService.fetchCardUsageCounts(anyLong()))
                .thenReturn(Collections.singletonList(cardUsageTooHigh));

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();

        // Validate the response
        assertEquals(DECLINED, response.getTransactionStatus());
    }

    private TransactionAnalysisResponse sendRequestAndGetResponse()
            throws Exception, JsonProcessingException, UnsupportedEncodingException, JsonMappingException {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/analyzeTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Extract the response body and assert its contents
        String responseBody = mvcResult.getResponse().getContentAsString();
        TransactionAnalysisResponse response = objectMapper.readValue(responseBody, TransactionAnalysisResponse.class);
        return response;
    }
}
