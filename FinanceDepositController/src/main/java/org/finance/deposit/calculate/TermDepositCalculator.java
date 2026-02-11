package org.finance.deposit.calculate;

import org.finance.deposit.model.DepositRequest;
import org.finance.deposit.model.TermDepositResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TermDepositCalculator implements IDepositCalculator<DepositRequest, TermDepositResponse> {

    @Override
    public TermDepositResponse calculate(DepositRequest param) {
        double initialAmount = param.getAmount();
        double interestRate = param.getInterestRate();
        long termCount = param.getTermCount();
        double taxRatePct = Objects.requireNonNullElse(param.getTaxRate(), 0.0);

        // Basit faiz: Faiz = Ana para × (yıllık oran/100) × (vade ay / 12)
        double grossIncome = initialAmount * (interestRate / 100) * (termCount / 12.0);
        double taxAmount = grossIncome * (taxRatePct / 100);
        double netIncome = grossIncome - taxAmount;

        return TermDepositResponse.builder()
                .initialAmount(initialAmount)
                .interestRate(interestRate)
                .grossIncome(Math.round(grossIncome * 100) / 100.0)
                .taxAmount(Math.round(taxAmount * 100) / 100.0)
                .netIncome(Math.round(netIncome * 100) / 100.0)
                .build();
    }
}
