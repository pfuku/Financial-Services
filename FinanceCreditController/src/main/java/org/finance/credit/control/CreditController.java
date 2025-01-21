package org.finance.credit.control;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @PostMapping("calculate")
    public String calculate(@RequestBody String param) {
        return "Hello from credit calculation!";
    }

}
