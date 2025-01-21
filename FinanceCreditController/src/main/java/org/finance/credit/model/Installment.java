package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Installment {
    private int term;
    private double amount;
    private double interestAmount;
    private double principalAmount;
    private double remainingAmount;
}
