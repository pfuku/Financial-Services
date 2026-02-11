package org.finance.deposit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private Double amount;
    private Double interestRate;
    private Long termCount;
    /** Vergi oranı (yüzde, örn: 10 = %10) */
    private Double taxRate;
}
