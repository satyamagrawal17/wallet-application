package com.example.walletApplication.model.transaction;

import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.Wallet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_wallet_id", nullable = false)
    private Wallet sourceWallet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETransactionType transactionType;

    @Column(nullable = false)
    private double amount;



}
