package com.example.walletApplication.config;

import pb.CurrencyConversionGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel grpcChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
    }

    @Bean
    public CurrencyConversionGrpc.CurrencyConversionBlockingStub currencyConversionStub(ManagedChannel grpcChannel) {
        return CurrencyConversionGrpc.newBlockingStub(grpcChannel);
    }
}





