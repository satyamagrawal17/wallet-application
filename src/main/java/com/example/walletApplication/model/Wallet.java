package com.example.walletApplication.model;

import com.example.walletApplication.ECurrency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Money balance;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Wallet(ECurrency currency) {
//        this.user = user;
        this.balance = new Money(0, currency);
    }


}