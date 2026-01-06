package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPlanResponse {
    private Double totalAmount;           // Total credit amount
    private Double monthlyPayment;        // Monthly payment amount
    private Double totalInterest;         // Total interest amount
    private Double totalKkdf;             // Total KKDF amount
    private Double totalBsmv;             // Total BSMV amount
    private Double totalPaymentAmount;    // Total payment amount (principal + interest + KKDF + BSMV)
    private Long termCount;                // Term count
    private Double interestRate;          // Interest rate
    private Double kkdfRate;              // KKDF rate
    private Double bsmvRate;              // BSMV rate
    private Double monthlyCostRate;       // Monthly cost rate (as percentage)
    private Double effectiveAnnualRate;   // Effective Annual Interest Rate (EAR)
    private List<Payment> payments;       // Payment plan details
}

