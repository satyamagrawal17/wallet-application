package com.example.walletApplication.service;

import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.model.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pb.CurrencyConversionGrpc;
import pb.Server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class CurrencyConversionGrpcServiceTest {
    @Mock
    private CurrencyConversionGrpc.CurrencyConversionBlockingStub currencyConversionStub;

    @InjectMocks
    private CurrencyConversionGrpcService currencyConversionGrpcService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testConvertCurrency_Success() {
        MoneyDto moneyDto = new MoneyDto(100.0, "USD");
        Server.Money fromMoney = Server.Money.newBuilder()
                .setCurrency("USD")
                .setAmount(100.0)
                .build();
        Server.CurrencyConversionRequest request = Server.CurrencyConversionRequest.newBuilder()
                .setMoney(fromMoney)
                .setFromCurrency("EUR")
                .build();
        Server.Money toMoney = Server.Money.newBuilder()
                .setCurrency("EUR")
                .setAmount(90.0)
                .build();
        Server.CurrencyConversionResponse response = Server.CurrencyConversionResponse.newBuilder()
                .setMoney(toMoney)
                .build();

        when(currencyConversionStub.convertCurrency(any(Server.CurrencyConversionRequest.class))).thenReturn(response);

        Money result = currencyConversionGrpcService.convertCurrency(moneyDto, "EUR");

        assertEquals(90.0, result.getAmount());
        assertEquals(ECurrency.EUR, result.getCurrency());
    }

    @Test
    void testConvertCurrency_Failure() {
        MoneyDto moneyDto = new MoneyDto(100.0, "USD");

        when(currencyConversionStub.convertCurrency(any(Server.CurrencyConversionRequest.class)))
                .thenThrow(new RuntimeException("gRPC call failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConversionGrpcService.convertCurrency(moneyDto, "EUR");
        });

        assertEquals("gRPC call failed", exception.getMessage());
    }

}