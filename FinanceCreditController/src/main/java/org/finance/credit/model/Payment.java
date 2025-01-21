package org.finance.credit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private Long returnAmount;
    private CreditRequest request;
    private List<Installment> installments;
}
