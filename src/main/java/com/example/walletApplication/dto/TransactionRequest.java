package com.example.walletApplication.dto;

import com.example.walletApplication.enums.ECurrency;
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
    private Long recipientWalletId;
    @NotNull
    @NotBlank(message = "Amount is mandatory")
    private double amount;
    @NotNull
    @NotBlank(message = "Currency is mandatory")
    private ECurrency currency;
}
