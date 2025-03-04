package com.example.walletApplication.model;

import com.example.walletApplication.dto.CurrencyExchangeRequestDto;
import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.enums.ECurrency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wallet(ECurrency currency) {
        this.balance = new Money(0, Objects.requireNonNullElse(currency, ECurrency.INR));
    }


    public void addBalance(@NotNull @NotBlank(message = "Amount is mandatory") double amount) {
        this.balance.setAmount(this.balance.getAmount() + amount);
    }

    public void subtractBalance(@NotNull @NotBlank(message = "Amount is mandatory") double amount) {
            if(this.balance.getAmount() < amount) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            this.balance.setAmount(this.balance.getAmount() - amount);
    }
}