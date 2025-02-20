package com.example.walletApplication.model;

import com.example.walletApplication.enums.ECurrency;
import jakarta.persistence.Embeddable;
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
    private ECurrency currency;
}
