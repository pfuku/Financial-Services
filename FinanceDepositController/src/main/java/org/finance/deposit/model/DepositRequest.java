package org.finance.deposit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepositRequest {
    private Double amount;
    private Double interestRate;
    private Long termCount;
}
