package com.example.walletApplication.dto;

import com.example.walletApplication.model.Money;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeRequestDto {
    private MoneyDto money;
    @NotNull
    private String from_currency;
}
