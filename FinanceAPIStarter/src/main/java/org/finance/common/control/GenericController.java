package org.finance.common.control;

import com.google.gson.Gson;
import org.finance.common.model.GenericRequest;
import org.finance.common.model.GenericResponse;
import org.finance.credit.calculate.ICreditCalculator;
import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.Payment;
import org.finance.deposit.calculate.IDepositCalculator;
import org.finance.deposit.model.DepositRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/generic")
public class GenericController {
    @Autowired
    ICreditCalculator<CreditRequest, Payment> creditPaymentPlanCalculator;

    @Autowired
    IDepositCalculator<DepositRequest, Double> termDepositCalculator;

    @PostMapping("/find")
    public GenericResponse find(@RequestBody GenericRequest param) {
        Gson gson = new Gson();
        String command = param.getCommand();
        String payload = param.getPayload();

        if (command.equalsIgnoreCase("credit")) {
            Payment payment = creditPaymentPlanCalculator.calculate(new CreditRequest(payload));
            return new GenericResponse(command, gson.toJson(payment));
        }
        return new GenericResponse(param.getCommand() ,"not found!" );
    }
}
