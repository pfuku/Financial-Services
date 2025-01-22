package org.finance.deposit.calculate;

public interface IDepositCalculator<T, K> {
    K calculate(T param);
}
