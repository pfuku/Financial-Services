package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer paymentNumber;   // Installment number
    private Double principalAmount;  // Principal payment
    private Double interestAmount;   // Interest payment
    private Double kkdfAmount;       // KKDF (Resource Utilization Support Fund) amount
    private Double bsmvAmount;       // BSMV (Banking and Insurance Transaction Tax) amount
    private Double totalPayment;     // Total payment (principal + interest + KKDF + BSMV)
    private Double remainingBalance; // Remaining balance
}

