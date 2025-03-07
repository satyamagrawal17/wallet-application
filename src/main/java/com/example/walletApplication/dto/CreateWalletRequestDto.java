package com.example.walletApplication.dto;

import com.example.walletApplication.enums.ECurrency;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletRequestDto {
    @NotNull
    @NotBlank(message = "Currency is mandatory")
    private ECurrency currency;
}
