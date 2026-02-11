package org.finance.deposit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TermDepositResponse {
    /** İlk tutar */
    private Double initialAmount;
    /** Yıllık faiz oranı (%) */
    private Double interestRate;
    /** Brüt kazanç (vergi öncesi) */
    private Double grossIncome;
    /** Hesaplanan gelir vergisi tutarı */
    private Double taxAmount;
    /** Vergiden sonra net kazanç */
    private Double netIncome;
}
