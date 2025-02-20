package com.example.walletApplication.model;

import com.example.walletApplication.enums.ECurrency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wallet() {
        this.balance = new Money(0, ECurrency.INR);
    }


}