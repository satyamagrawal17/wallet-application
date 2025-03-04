package com.example.walletApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeResponseDto {
    private MoneyDto money;
}
