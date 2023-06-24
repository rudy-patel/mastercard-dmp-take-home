package com.api.controllers;

import com.api.models.Transaction;
import com.api.models.TransactionAnalysisResponse;
import com.api.models.TransactionRequest;
import com.configuration.TestConfiguration;
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

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfiguration.class)
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Perform any setup tasks before each test
    }

    @Test
    void testAnalyzeTransaction_happyCase_Successful() throws Exception {
        System.out.println("Running test: testAnalyzeTransaction_Successful");
        
        // Prepare the request payload
        TransactionRequest request = new TransactionRequest();
        Transaction transaction = new Transaction();
        transaction.setCardNum(5206840000000001L);
        transaction.setAmount(3.99);
        request.setTransaction(transaction);

        // Send the request and validate the response
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/analyzeTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Extract the response body and assert its contents
        String responseBody = mvcResult.getResponse().getContentAsString();
        TransactionAnalysisResponse response = objectMapper.readValue(responseBody, TransactionAnalysisResponse.class);
        assertThat(response.getCardNumber()).isEqualTo("5206********0001");
        assertThat(response.getTransactionAmount()).isEqualTo(3.99);

        // how do we want to approach this? this value can change.
        // assertThat(response.getTransactionStatus()).isEqualTo("Approved");
        
        // Add more assertions as needed

        System.out.println("Test succeeded!");
    }

    // Add more test methods to cover different scenarios

}
