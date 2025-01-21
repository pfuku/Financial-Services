package org.finance.credit.calculate;

import org.finance.credit.model.CreditRequest;
import org.springframework.stereotype.Component;

@Component
public class CreditMonthlyPaymentCalculator implements ICreditCalculator<CreditRequest, Double> {
    @Override
    public Double calculate(CreditRequest param) {
        if (param.getInterestRate() == 0) {
            return param.getAmount() / param.getTermCount();
        }

        double monthlyInterestRate = param.getInterestRate() / 12 / 100;
        int totalPayments = param.getTermCount();
        double numerator = monthlyInterestRate * Math.pow( 1 + monthlyInterestRate, totalPayments);
        double denominator = Math.pow(1 + monthlyInterestRate, totalPayments) - 1;
        return param.getAmount() * (numerator / denominator);
    }
}
