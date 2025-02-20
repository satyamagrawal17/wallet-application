package com.example.walletApplication.controller;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.dto.UserDTO;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.service.TransactionService;
import com.example.walletApplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


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
    }
}
