package org.finance.deposit.calculate;

import org.finance.deposit.model.DepositRequest;
import org.springframework.stereotype.Component;

@Component
public class TermDepositCalculator implements IDepositCalculator<DepositRequest, Double>{
    @Override
    public Double calculate(DepositRequest param) {
        return Double.valueOf(Math.round(param.getAmount() * Math.pow((1 + (param.getInterestRate() / 12 / 100)), param.getTermCount())));
    }
}
