package com.api.controllers;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.configuration.TestConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfiguration.class)
public class TransactionControllerIntegrationTest {
    private static final String DECLINED = "Declined";
    private static final long CARD_NUM = 5206840000000001L;
    private static final String OBFUSCATED_CARD_NUM = "5206********0001";
    
    private TransactionRequest request;
    private Transaction transaction;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        request = new TransactionRequest();
        transaction = new Transaction();
        transaction.setCardNum(CARD_NUM);
        request.setTransaction(transaction);
    }

    @Test
    void testAnalyzeTransaction_happyCase_Successful() throws Exception {
        // Prepare the request payload
        double expectedTransactionAmount = 3.99;
        transaction.setAmount(expectedTransactionAmount);

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();

        // Validate the response
        assertEquals(OBFUSCATED_CARD_NUM, response.getCardNumber());
        assertEquals(expectedTransactionAmount, response.getTransactionAmount());

        // how do we want to approach this? this value can change.
        // assertThat(response.getTransactionStatus()).isEqualTo("Approved");
    }

    @Test
    void testAnalyzeTransaction_AmountOverLimit_Declined() throws Exception {
        // Prepare the request payload
        transaction.setAmount(50000.1); // amount is > 50000

        // Send the request
        TransactionAnalysisResponse response = sendRequestAndGetResponse();
        
        // Validate the response
        assertEquals(DECLINED, response.getTransactionStatus());
    }

    // @Test
    // void testAnalyzeTransaction_CardUsageOverLimit_Declined() throws Exception {
    //     System.out.println("Running test: testAnalyzeTransaction_CardUsageOverLimit_Declined");

    //     // Prepare the request payload
    //     TransactionRequest request = new TransactionRequest();
    //     Transaction transaction = new Transaction();
    //     transaction.setCardNum(5206840000000001L);
    //     transaction.setAmount(100.0);
    //     request.setTransaction(transaction);

    //     // Send multiple requests to simulate card usage over limit
    //     for (int i = 0; i < 61; i++) {
    //         mockMvc.perform(MockMvcRequestBuilders
    //                 .post("/analyzeTransaction")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(objectMapper.writeValueAsString(request)));
    //     }

    //     // Send the request and validate the response
    //     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
    //             .post("/analyzeTransaction")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(request)))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andReturn();

    //     // Extract the response body and assert its contents
    //     String responseBody = mvcResult.getResponse().getContentAsString();
    //     TransactionAnalysisResponse response = objectMapper.readValue(responseBody, TransactionAnalysisResponse.class);
    //     assertThat(response.getTransactionStatus()).isEqualTo("Declined");

    //     System.out.println("Test succeeded!");
    // }

    // @Test
    // void testAnalyzeTransaction_CardUsageUnderLimitButAmountOverThreshold_Declined() throws Exception {
    //     System.out.println("Running test: testAnalyzeTransaction_CardUsageUnderLimitButAmountOverThreshold_Declined");

    //     // Prepare the request payload
    //     TransactionRequest request = new TransactionRequest();
    //     Transaction transaction = new Transaction();
    //     transaction.setCardNum(5206840000000001L);
    //     transaction.setAmount(9000.0);
    //     request.setTransaction(transaction);

    //     // Send multiple requests to simulate card usage under limit
    //     for (int i = 0; i < 34; i++) {
    //         mockMvc.perform(MockMvcRequestBuilders
    //                 .post("/analyzeTransaction")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(objectMapper.writeValueAsString(request)));
    //     }

    //     // Send the request and validate the response
    //     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
    //             .post("/analyzeTransaction")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(request)))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andReturn();

    //     // Extract the response body and assert its contents
    //     String responseBody = mvcResult.getResponse().getContentAsString();
    //     TransactionAnalysisResponse response = objectMapper.readValue(responseBody, TransactionAnalysisResponse.class);
    //     assertThat(response.getTransactionStatus()).isEqualTo("Declined");

    //     System.out.println("Test succeeded!");
    // }

    // @Test
    // void testAnalyzeTransaction_AllOtherTransactions_Approved() throws Exception {
    //     System.out.println("Running test: testAnalyzeTransaction_AllOtherTransactions_Approved");

    //     // Prepare the request payload
    //     TransactionRequest request = new TransactionRequest();
    //     Transaction transaction = new Transaction();
    //     transaction.setCardNum(5206840000000001L);
    //     transaction.setAmount(100.0);
    //     request.setTransaction(transaction);

    //     // Send the request and validate the response
    //     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
    //             .post("/analyzeTransaction")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(request)))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andReturn();

    //     // Extract the response body and assert its contents
    //     String responseBody = mvcResult.getResponse().getContentAsString();
    //     TransactionAnalysisResponse response = objectMapper.readValue(responseBody, TransactionAnalysisResponse.class);
    //     assertThat(response.getTransactionStatus()).isEqualTo("Approved");

    //     System.out.println("Test succeeded!");
    // }

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
