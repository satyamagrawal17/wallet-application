package com.example.walletApplication.repository;

import com.example.walletApplication.model.transaction.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Optional<Transfer>> findAllByToWallet_Id(Long walletId);
}
