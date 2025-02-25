package com.example.walletApplication.service;

import currency.CurrencyConversionGrpc;
import currency.Server;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class grpcCurrencyConversionService {
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        CurrencyConversionGrpc.CurrencyConversionBlockingStub stub = CurrencyConversionGrpc.newBlockingStub(channel);

        Server.CurrencyConversionRequest request = Server.CurrencyConversionRequest.newBuilder()
                .setFromCurrency(fromCurrency)
                .setToCurrency(toCurrency)
                .setAmount(amount)
                .build();

        Server.CurrencyConversionResponse response = stub.convertCurrency(request);
        channel.shutdown();
        return response.getMoney().getAmount();
    }
}
