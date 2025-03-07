package com.example.walletApplication.controller;

import com.example.walletApplication.dto.CreateWalletRequestDto;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.service.WalletService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletService walletService;

    private CreateWalletRequestDto walletDTO;

    @BeforeEach
    void setUp() {
        walletDTO = new CreateWalletRequestDto();
        walletDTO.setCurrency(ECurrency.USD);
    }

    @Test
    void testCreateWalletSuccessfully() throws Exception {
        doNothing().when(walletService).createWallet(1L, walletDTO.getCurrency());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Wallet created successfully"));
    }

    @Test
    void testCreateWalletFailure() throws Exception {
        doThrow(new IllegalArgumentException("Wallet creation failed")).when(walletService).createWallet(1L, walletDTO.getCurrency());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Wallet creation failed"));
    }


}