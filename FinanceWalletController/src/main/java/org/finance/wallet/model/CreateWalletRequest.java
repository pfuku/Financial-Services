package org.finance.wallet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {
    private Long userId;
    private String currencyCode; // USD, EUR, TRY, etc.
}

