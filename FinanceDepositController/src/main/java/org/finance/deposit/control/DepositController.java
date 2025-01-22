package org.finance.deposit.control;

import org.finance.deposit.calculate.IDepositCalculator;
import org.finance.deposit.model.DepositRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    IDepositCalculator<DepositRequest, Double> termDepositCalculator;

    @PostMapping("calculate/term")
    public Double calculateTermDeposit(@RequestBody DepositRequest param) {
        return termDepositCalculator.calculate(param);
    }

}
