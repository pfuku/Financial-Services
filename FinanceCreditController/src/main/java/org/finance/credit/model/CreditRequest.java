package org.finance.credit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditRequest {
    private double amount;
    private double interestRate;
    private int termCount;
}
