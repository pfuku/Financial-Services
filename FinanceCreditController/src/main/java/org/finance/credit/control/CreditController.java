package org.finance.credit.control;

import org.finance.credit.calculate.ICreditCalculator;
import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.PaymentPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    ICreditCalculator<CreditRequest, PaymentPlanResponse> creditCalculator;

    @PostMapping("/calculate/payment-plan")
    public PaymentPlanResponse calculatePaymentPlan(@RequestBody CreditRequest request) {
        return creditCalculator.calculate(request);
    }
}

