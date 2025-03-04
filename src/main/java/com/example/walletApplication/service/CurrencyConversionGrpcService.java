package com.example.walletApplication.service;


import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.model.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pb.CurrencyConversionGrpc;
import pb.Server;

@Service
@RequiredArgsConstructor
public class CurrencyConversionGrpcService {
    private final CurrencyConversionGrpc.CurrencyConversionBlockingStub currencyConversionStub;
    public Money convertCurrency(MoneyDto money, String fromCurrency) {
        Server.Money fromMoney = Server.Money.newBuilder()
                .setCurrency(money.getCurrency())
                .setAmount(money.getAmount())
                .build();

        Server.CurrencyConversionRequest request = Server.CurrencyConversionRequest.newBuilder()
                .setMoney(fromMoney)
                .setFromCurrency(fromCurrency)
                .build();

        Server.CurrencyConversionResponse response = currencyConversionStub.convertCurrency(request);
        System.out.println(response.getMoney().getAmount());
        return new Money(response.getMoney().getAmount(), ECurrency.fromString(response.getMoney().getCurrency()));
    }
}
