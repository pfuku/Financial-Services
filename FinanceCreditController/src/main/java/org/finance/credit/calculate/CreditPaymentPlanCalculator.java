package org.finance.credit.calculate;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.Installment;
import org.finance.credit.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreditPaymentPlanCalculator implements ICreditCalculator<CreditRequest, Payment> {

    @Autowired
    ICreditCalculator<CreditRequest, Double> creditMonthlyPaymentCalculator;

    @Override
    public Payment calculate(CreditRequest param) {
        double monthlyInterestRate = param.getInterestRate() / 12 / 100;
        int totalPayments = param.getTermCount() * 12;
        Double monthlyPayment = creditMonthlyPaymentCalculator.calculate(param);

        List<Installment> installments = new ArrayList<>();
        double remainingAmount = param.getAmount();

        for(int i = 1; i <= totalPayments; i++) {
            double interestPayment = remainingAmount * monthlyInterestRate;
            double principalPayment = monthlyPayment - interestPayment;
            remainingAmount -= principalPayment;
            remainingAmount = (remainingAmount < 0.1) ? 0 : remainingAmount;

            Installment installment = new Installment(i, param.getAmount(), interestPayment, principalPayment, remainingAmount);
            installments.add(installment);
        }

        return new Payment(param, installments);
    }
}
