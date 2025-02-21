package com.example.walletApplication.service;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;
    @Test
    void testCreateTransactionSuccessfully() {
        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setTransactionType(ETransactionType.DEPOSIT);
        transactionRequest.setAmount(100.0);
        transactionRequest.setCurrency(ECurrency.INR);

        Transaction transaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequest, 1L, 1L));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }



}