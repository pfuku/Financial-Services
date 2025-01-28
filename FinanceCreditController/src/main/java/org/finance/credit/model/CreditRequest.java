package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreditRequest {
    private double amount;
    private double interestRate;
    private int termCount;

    public CreditRequest(String payload) {
        String[] params = payload.split(",");
        amount =  Double.valueOf(params[0].split("=")[1]);
        interestRate = Double.valueOf(params[1].split("=")[1]);
        termCount = Integer.valueOf(params[2].split("=")[1]);
    }
}
