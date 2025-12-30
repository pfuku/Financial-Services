package org.finance.wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Long fromWalletId;
    private Long toWalletId;
    private Long userId;
    private String transactionType;
    private BigDecimal amount;
    private String currencyCode;
    private String description;
    private Long createDate;
    private Integer status;
}

