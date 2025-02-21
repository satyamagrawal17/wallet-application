package com.example.walletApplication.service;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.handler.ITransactionHandler;
import com.example.walletApplication.model.User;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.model.transaction.Transfer;
import com.example.walletApplication.registry.TransactionHandlerRegistry;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionHandlerRegistry transactionHandlerRegistry;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionRequest transactionRequest;
    private Wallet originWallet;
    private Wallet recipientWallet;
    private ITransactionHandler transactionHandler;

    @BeforeEach
    void setUp() {
        transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(ETransactionType.DEPOSIT);
        transactionRequest.setAmount(100.0);
        transactionRequest.setCurrency(ECurrency.INR);
        transactionRequest.setRecipientWalletId(2L);

        originWallet = new Wallet();
        originWallet.setId(1L);
        originWallet.setUser(new User());
        originWallet.getUser().setId(1L);

        recipientWallet = new Wallet();
        recipientWallet.setId(2L);
        recipientWallet.setUser(new User());
        recipientWallet.getUser().setId(2L);

        transactionHandler = mock(ITransactionHandler.class);
    }

    @Test
    void testCreateTransactionWithDepositSuccessfully() throws Exception {
        transactionRequest.setRecipientWalletId(null);
        when(walletService.getWallet(1L)).thenReturn(originWallet);
//        when(walletService.getWallet(2L)).thenReturn(recipientWallet);
        when(transactionHandlerRegistry.getHandler(ETransactionType.DEPOSIT)).thenReturn(transactionHandler);

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequest, 1L, 1L));
        verify(walletService, times(1)).getWallet(1L);
        verify(walletService, never()).getWallet(2L);
        verify(transactionHandler, times(1)).process(transactionRequest, 1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    @Test
    void testCreateTransactionWithInvalidOriginWalletId() throws Exception {
        when(walletService.getWallet(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(transactionRequest, 1L, 1L));

        verify(transactionHandlerRegistry, never()).getHandler(any(ETransactionType.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    @Test
    void testCreateTransactionWithInvalidRecipientWalletId() throws Exception {
        transactionRequest.setTransactionType(ETransactionType.TRANSFER);
        when(walletService.getWallet(1L)).thenReturn(originWallet);
        when(walletService.getWallet(2L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(transactionRequest, 1L, 1L));

        verify(transactionHandlerRegistry, never()).getHandler(any(ETransactionType.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    @Test
    void testCreateTransactionWithInvalidUser() throws Exception {
        when(walletService.getWallet(1L)).thenReturn(originWallet);
        when(walletService.getWallet(2L)).thenReturn(recipientWallet);

        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(transactionRequest, 2L, 1L));

        verify(transactionHandlerRegistry, never()).getHandler(any(ETransactionType.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    @Test
    void testCreateTransactionWithTransferSuccessfully() throws Exception {
        transactionRequest.setTransactionType(ETransactionType.TRANSFER);
        when(walletService.getWallet(1L)).thenReturn(originWallet);
        when(walletService.getWallet(2L)).thenReturn(recipientWallet);
        when(transactionHandlerRegistry.getHandler(ETransactionType.TRANSFER)).thenReturn(transactionHandler);

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequest, 1L, 1L));

        verify(transactionHandler, times(1)).process(transactionRequest, 1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void testCreateTransactionWithWithdrawSuccessfully() throws Exception {
        transactionRequest.setRecipientWalletId(null);
        transactionRequest.setTransactionType(ETransactionType.WITHDRAW);
        when(walletService.getWallet(1L)).thenReturn(originWallet);
        when(transactionHandlerRegistry.getHandler(ETransactionType.WITHDRAW)).thenReturn(transactionHandler);

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequest, 1L, 1L));

        verify(walletService, times(1)).getWallet(1L);
        verify(walletService, never()).getWallet(2L);
        verify(transactionHandler, times(1)).process(transactionRequest, 1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }

}