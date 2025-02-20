package com.example.walletApplication.dto;

import com.example.walletApplication.enums.ETransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionRequest {
    @NotNull
    @NotBlank(message = "Transaction type is mandatory")
    @Enumerated(EnumType.STRING)
    private ETransactionType transactionType;
    @NotNull
    @NotBlank(message = "Origin wallet id is mandatory")
    private String originWalletId;
    private String toWalletId;
    @NotNull
    @NotBlank(message = "Amount is mandatory")
    private double amount;
}
