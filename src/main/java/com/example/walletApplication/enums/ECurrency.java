package com.example.walletApplication.enums;

public enum ECurrency {
    INR, USD, EUR;

    public static ECurrency fromString(String currency) {
        try {
            return ECurrency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency: " + currency);
        }
    }
}