package org.finance.common.control;

import com.google.gson.Gson;
import org.finance.common.model.GenericRequest;
import org.finance.common.model.GenericResponse;
import org.finance.credit.calculate.ICreditCalculator;
import org.finance.credit.model.CreditRequest;
import org.finance.credit.model.Payment;
import org.finance.deposit.calculate.IDepositCalculator;
import org.finance.deposit.model.DepositRequest;
import org.finance.infra.database.entity.UserEntity;
import org.finance.infra.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/generic")
public class GenericController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/find")
    public GenericResponse find(@RequestBody GenericRequest param) {
        Gson gson = new Gson();
        String command = param.getCommand();
        String payload = param.getPayload();

        UserEntity user = userRepository.findByUserNameAndState("jdoe", 1);

        return new GenericResponse(user.getUserName(), user.getUserEmailAddress());
    }
}
