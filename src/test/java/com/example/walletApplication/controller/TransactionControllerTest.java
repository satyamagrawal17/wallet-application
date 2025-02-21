package com.example.walletApplication.controller;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.dto.TransactionResponse;
import com.example.walletApplication.dto.UserDTO;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.service.TransactionService;
import com.example.walletApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    TransactionRequest transactionRequest;
    @MockitoBean
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(ETransactionType.DEPOSIT);
        transactionRequest.setAmount(100.0);
        transactionRequest.setCurrency(ECurrency.INR);
    }

    @Test
    public void testTransactionControllerCreateTransactionSuccessfully() throws Exception {
        doNothing().when(transactionService).createTransaction(transactionRequest, 1L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/wallets/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction created successfully"));
    }

    @Test public void testTransactionControllerCreateTransactionWithInvalidTransactionType() throws Exception {
        doThrow(new IllegalArgumentException("Invalid transaction")).when(transactionService).createTransaction(transactionRequest, 1L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/wallets/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid transaction"));
    }
}
