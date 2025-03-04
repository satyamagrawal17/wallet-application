package com.example.walletApplication.service;

import com.example.walletApplication.dto.CurrencyExchangeRequestDto;
import com.example.walletApplication.dto.CurrencyExchangeResponseDto;
import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.exception.UserNotFoundException;
import com.example.walletApplication.exception.WalletNotFoundException;
import com.example.walletApplication.model.Money;
import com.example.walletApplication.model.User;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.repository.WalletRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;


    @Autowired
    private CurrencyConversionService currencyConversionService;


    public void createWallet(User user, ECurrency currency) {
        Wallet wallet = new Wallet(currency);
        wallet.setUser(user);
        walletRepository.save(wallet);
    }
public Wallet getWallet(Long walletId) throws Exception {
    return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet does not exist"));
}

    public void deposit(Long originWalletId, @NotNull @NotBlank(message = "Amount is mandatory") double amount, ECurrency currency) throws Exception {
        Wallet wallet = getWallet(originWalletId);
        double convertedAmount = amount;
        if(currency != null) {
            convertedAmount = processConversion(wallet, amount, currency);
        }
        wallet.addBalance(convertedAmount);
        walletRepository.save(wallet);
    }

    public void withdraw(Long originWalletId, @NotNull @NotBlank(message = "Amount is mandatory") double amount, ECurrency currency) throws Exception {
        Wallet wallet = getWallet(originWalletId);
        double convertedAmount = amount;
        if(currency != null) {
            convertedAmount = processConversion(wallet, amount, currency);
        }
        wallet.subtractBalance(convertedAmount);
        walletRepository.save(wallet);
    }
    public boolean doesUserBelongTo(Long walletId, Long userId) throws Exception {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet does not exist"));
        return Objects.equals(wallet.getUser().getId(), userId);
    }

    public double processConversion(Wallet wallet, double amount, ECurrency currency) {
        if(wallet.getBalance().getCurrency() != currency) {
            CurrencyExchangeRequestDto request = new CurrencyExchangeRequestDto(new MoneyDto(amount, wallet.getBalance().getCurrency().toString()), currency.toString());
            CurrencyExchangeResponseDto response = currencyConversionService.getConvertedAmount(request);
            if(response != null) {
                amount = response.getMoney().getAmount();
            }
            else {
                throw new RuntimeException("Could not convert Money");
            }
        }
        return amount;
    }
}
