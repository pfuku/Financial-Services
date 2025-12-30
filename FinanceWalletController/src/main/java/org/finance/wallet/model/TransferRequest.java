package org.finance.wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {
    private Long userId;
    private Long fromWalletId;
    private Long toWalletId;
    private BigDecimal amount;
    private String description;
}

