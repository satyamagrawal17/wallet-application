package com.example.walletApplication;

public enum ECurrency {
    INR(1.0), USD(0.014), EUR(0.012);

    private final double conversionRate;

    ECurrency(double conversionRate){
        this.conversionRate = conversionRate;
    }

    public double getConversionRate(){
        return this.conversionRate;
    }
    public double convertToINR(double amount){
        return amount * this.conversionRate;
    }
    public double convertFromINR(double amount){
        return amount / this.conversionRate;
    }
}
