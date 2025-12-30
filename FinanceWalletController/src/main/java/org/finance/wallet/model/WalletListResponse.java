package org.finance.wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WalletListResponse {
    private Long userId;
    private List<WalletResponse> wallets;
}

