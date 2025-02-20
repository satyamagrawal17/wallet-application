package com.example.walletApplication.model;

import com.example.walletApplication.enums.ECurrency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Money {
    @NotNull
    @NotBlank(message = "amount is required")
    private double amount;
    @NotNull
    @NotBlank(message = "Currency is required")
    @Enumerated(EnumType.STRING)
    private ECurrency currency;
}
