package org.finance.wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletResponse {
    private Long id;
    private Long userId;
    private String currencyCode;
    private BigDecimal balance;
    private Long createDate;
    private Long updateDate;
    private Integer status;
    private Integer state;
}

