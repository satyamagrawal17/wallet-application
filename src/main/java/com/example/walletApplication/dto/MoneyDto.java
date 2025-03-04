package com.example.walletApplication.dto;

import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.enums.ETransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MoneyDto {
    @NotNull
    @NotBlank(message = "Amount is mandatory")
    private double amount;
    @NotNull
    @NotBlank(message = "Currency is mandatory")
    private String currency;
}
