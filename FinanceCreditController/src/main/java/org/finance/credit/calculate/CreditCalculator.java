package org.finance.credit.calculate;

import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.Payment;
import org.finance.credit.model.PaymentPlanResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreditCalculator implements ICreditCalculator<CreditRequest, PaymentPlanResponse> {

    @Override
    public PaymentPlanResponse calculate(CreditRequest request) {
        Double principal = request.getAmount();
        Double annualInterestRate = request.getInterestRate();
        Long termCount = request.getTermCount();
        
        // Get KKDF rate, default to 15.0% for consumer credits in Turkey if not specified
        Double kkdfRate = request.getKkdfRate() != null ? request.getKkdfRate() : 15.0;
        Double kkdfMultiplier = kkdfRate / 100.0;
        
        // Get BSMV rate, default to 5.0% for consumer credits in Turkey if not specified
        Double bsmvRate = request.getBsmvRate() != null ? request.getBsmvRate() : 5.0;
        Double bsmvMultiplier = bsmvRate / 100.0;

        // Calculate monthly interest rate (annual interest / 12 / 100)
        Double monthlyInterestRate = annualInterestRate / 12.0 / 100.0;

        // First pass: Calculate total interest, KKDF, and BSMV to determine fixed monthly payment
        Double remainingBalance = principal;
        Double totalInterest = 0.0;
        Double totalKkdf = 0.0;
        Double totalBsmv = 0.0;

        // Calculate total interest, KKDF, and BSMV for all installments
        for (int i = 1; i <= termCount; i++) {
            // This month's interest = Remaining balance * monthly interest rate
            Double interestAmount = remainingBalance * monthlyInterestRate;
            
            // Calculate KKDF: KKDF = Interest * (KKDF Rate / 100)
            Double kkdfAmount = interestAmount * kkdfMultiplier;
            
            // Calculate BSMV: BSMV = Interest * (BSMV Rate / 100)
            Double bsmvAmount = interestAmount * bsmvMultiplier;
            
            // Calculate principal payment using equal installment formula (principal + interest only)
            Double baseMonthlyPayment = calculateMonthlyPayment(principal, monthlyInterestRate, termCount);
            Double principalAmount = baseMonthlyPayment - interestAmount;
            
            // Adjust for last installment
            if (i == termCount) {
                principalAmount = remainingBalance;
            }
            
            totalInterest += interestAmount;
            totalKkdf += kkdfAmount;
            totalBsmv += bsmvAmount;
            
            // Update remaining balance
            remainingBalance = remainingBalance - principalAmount;
            
            // Check to prevent negative balance
            if (remainingBalance < 0.01) {
                remainingBalance = 0.0;
            }
        }

        // Calculate total payment amount (principal + interest + KKDF + BSMV)
        Double totalPaymentAmount = principal + totalInterest + totalKkdf + totalBsmv;
        
        // Calculate fixed monthly payment (total payment divided by term count)
        Double fixedMonthlyPayment = totalPaymentAmount / termCount;
        
        // Second pass: Calculate payment details with fixed monthly payment
        List<Payment> payments = new ArrayList<>();
        remainingBalance = principal;
        totalInterest = 0.0;
        totalKkdf = 0.0;
        totalBsmv = 0.0;
        
        // Calculate monthly cost rates for each installment
        List<Double> monthlyCostRates = new ArrayList<>();

        for (int i = 1; i <= termCount; i++) {
            // Store balance at the beginning of the month
            Double balanceAtStart = remainingBalance;
            
            // This month's interest = Remaining balance * monthly interest rate
            Double interestAmount = remainingBalance * monthlyInterestRate;
            
            // Calculate KKDF: KKDF = Interest * (KKDF Rate / 100)
            Double kkdfAmount = interestAmount * kkdfMultiplier;
            
            // Calculate BSMV: BSMV = Interest * (BSMV Rate / 100)
            Double bsmvAmount = interestAmount * bsmvMultiplier;
            
            // Calculate monthly cost rate for this installment
            // Monthly cost rate = (Interest + KKDF + BSMV) / Balance at start of month
            // Store as decimal (not percentage) to avoid double conversion
            if (balanceAtStart > 0.01) {
                Double monthlyCost = interestAmount + kkdfAmount + bsmvAmount;
                Double costRate = monthlyCost / balanceAtStart; // Decimal form (e.g., 0.0104 for 1.04%)
                monthlyCostRates.add(costRate);
            }
            
            // Principal payment = Fixed monthly payment - Interest - KKDF - BSMV
            Double principalAmount = fixedMonthlyPayment - interestAmount - kkdfAmount - bsmvAmount;
            
            // Adjust for last installment to ensure balance is zero and payment is fixed
            if (i == termCount) {
                // For last installment, principal amount must equal remaining balance
                // Keep interest, KKDF, and BSMV as calculated (based on remaining balance)
                // Adjust principal to remaining balance
                principalAmount = remainingBalance;
                
                // Total payment will be: principalAmount + interestAmount + kkdfAmount + bsmvAmount
                // This might be slightly different from fixedMonthlyPayment due to rounding
                // But we keep it fixed by using fixedMonthlyPayment as total
            }
            
            // Update remaining balance
            remainingBalance = remainingBalance - principalAmount;
            
            // Check to prevent negative balance
            if (remainingBalance < 0.01) {
                remainingBalance = 0.0;
            }
            
            totalInterest += interestAmount;
            totalKkdf += kkdfAmount;
            totalBsmv += bsmvAmount;
            
            // Total payment for this month (fixed for all installments including last)
            Double totalPayment = fixedMonthlyPayment;

            Payment payment = new Payment(
                i,
                Math.round(principalAmount * 100.0) / 100.0,
                Math.round(interestAmount * 100.0) / 100.0,
                Math.round(kkdfAmount * 100.0) / 100.0,
                Math.round(bsmvAmount * 100.0) / 100.0,
                Math.round(totalPayment * 100.0) / 100.0,
                Math.round(remainingBalance * 100.0) / 100.0
            );
            
            payments.add(payment);
        }
        
        // Recalculate total payment amount from actual payments
        totalPaymentAmount = principal + totalInterest + totalKkdf + totalBsmv;
        
        // Fixed monthly payment (same for all installments)
        Double monthlyPayment = fixedMonthlyPayment;
        
        // Calculate average monthly cost rate (in decimal form)
        // Average of all monthly cost rates
        Double monthlyCostRateDecimal = 0.0;
        if (!monthlyCostRates.isEmpty()) {
            Double sum = 0.0;
            for (Double rate : monthlyCostRates) {
                sum += rate;
            }
            monthlyCostRateDecimal = sum / monthlyCostRates.size();
        }
        
        // Convert to percentage for display
        Double monthlyCostRate = monthlyCostRateDecimal * 100.0;
        
        // Calculate Effective Annual Interest Rate (EAR) using average monthly cost rate
        // EAR = (1 + monthly_cost_rate)^12 - 1
        // This accounts for the compounding effect of monthly payments
        // monthlyCostRateDecimal is in decimal form (e.g., 0.0104 for 1.04%)
        // Result is in decimal form, convert to percentage by multiplying by 100
        Double effectiveAnnualRate = calculateEffectiveAnnualRate(monthlyCostRateDecimal);

        return new PaymentPlanResponse(
            principal,
            Math.round(monthlyPayment * 100.0) / 100.0,
            Math.round(totalInterest * 100.0) / 100.0,
            Math.round(totalKkdf * 100.0) / 100.0,
            Math.round(totalBsmv * 100.0) / 100.0,
            Math.round(totalPaymentAmount * 100.0) / 100.0,
            termCount,
            annualInterestRate,
            kkdfRate,
            bsmvRate,
            Math.round(monthlyCostRate * 100.0) / 100.0, // Round to 2 decimal places as percentage
            Math.round(effectiveAnnualRate * 10000.0) / 100.0, // Round to 2 decimal places as percentage
            payments
        );
    }

    /**
     * Calculates equal installment payment amount
     * Formula: P * (r * (1+r)^n) / ((1+r)^n - 1)
     * 
     * @param principal Principal amount
     * @param monthlyInterestRate Monthly interest rate
     * @param termCount Term count
     * @return Monthly payment amount
     */
    private Double calculateMonthlyPayment(Double principal, Double monthlyInterestRate, Long termCount) {
        if (monthlyInterestRate == 0.0) {
            // Equal installment in case of no interest
            return principal / termCount;
        }

        Double onePlusRate = 1 + monthlyInterestRate;
        Double powerTerm = Math.pow(onePlusRate, termCount);
        Double numerator = principal * monthlyInterestRate * powerTerm;
        Double denominator = powerTerm - 1;

        return numerator / denominator;
    }
    
    /**
     * Calculates Effective Annual Interest Rate (EAR)
     * EAR = (1 + monthly_rate)^12 - 1
     * 
     * This formula accounts for the compounding effect of monthly interest payments.
     * The monthly interest rate is the nominal annual rate divided by 12.
     * 
     * @param monthlyInterestRate Monthly interest rate (as decimal, e.g., 0.0104 for 1.04%)
     * @return Effective Annual Interest Rate as percentage
     */
    private Double calculateEffectiveAnnualRate(Double monthlyInterestRate) {
        if (monthlyInterestRate == null || monthlyInterestRate <= 0.0) {
            return 0.0;
        }
        
        // Convert monthly rate to annual effective rate
        // EAR = (1 + monthly_rate)^12 - 1
        Double effectiveAnnualRate = (Math.pow(1 + monthlyInterestRate, 12) - 1);
        
        return effectiveAnnualRate;
    }
}

