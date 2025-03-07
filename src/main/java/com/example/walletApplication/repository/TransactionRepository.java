package com.example.walletApplication.repository;

import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Optional<Transaction>> findAllByOriginWallet_Id(Long walletId);

}
