package com.example.walletApplication.entity;

import com.example.walletApplication.ECurrency;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Money {
    private double value;
    private ECurrency currency;
}
