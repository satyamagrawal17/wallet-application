package com.example.walletApplication.repository;

import com.example.walletApplication.model.transaction.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
