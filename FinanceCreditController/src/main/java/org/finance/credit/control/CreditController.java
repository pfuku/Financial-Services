package org.finance.credit.control;

import org.finance.credit.calculate.ICreditCalculator;
import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    ICreditCalculator<CreditRequest, Payment> creditPaymentPlanCalculator;

    @PostMapping("calculate")
    public Payment calculate(@RequestBody CreditRequest param) {
        return creditPaymentPlanCalculator.calculate(param);
    }

}
