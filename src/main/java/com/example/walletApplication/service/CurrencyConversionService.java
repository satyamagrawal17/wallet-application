package com.example.walletApplication.service;

import com.example.walletApplication.dto.CurrencyExchangeRequestDto;
import com.example.walletApplication.dto.CurrencyExchangeResponseDto;
import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyConversionService {

    @Value("${currency.conversion.api.url}")
    private String apiUrl;
    private RestTemplate restTemplate;
    @Autowired
    public CurrencyConversionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CurrencyExchangeResponseDto getConvertedAmount(CurrencyExchangeRequestDto request) {

        try {
            return restTemplate.postForObject(apiUrl, request, CurrencyExchangeResponseDto.class);
        } catch (Exception e) {
            // Handle the exception (e.g., log, throw a custom exception)
            e.printStackTrace(); // For debugging, replace with proper logging
            return null; // Or throw a custom Exception.
        }
    }
}
