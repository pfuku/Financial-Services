package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest {
    private Double amount;          // Credit amount
    private Double interestRate;     // Annual interest rate (as percentage, e.g., 12.5)
    private Long termCount;          // Term count (in months)
    private Double kkdfRate;         // KKDF (Resource Utilization Support Fund) rate (as percentage, default 15.0 for consumer credits in Turkey)
    private Double bsmvRate;         // BSMV (Banking and Insurance Transaction Tax) rate (as percentage, default 5.0 for consumer credits in Turkey)
}

