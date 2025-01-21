package org.finance.credit.calculate;

import org.finance.credit.model.CreditRequest;

public interface ICreditCalculator<T, K> {
    K calculate(T param);
}
