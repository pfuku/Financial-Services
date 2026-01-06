package org.finance.credit.calculate;

public interface ICreditCalculator<T, K> {
    K calculate(T param);
}

