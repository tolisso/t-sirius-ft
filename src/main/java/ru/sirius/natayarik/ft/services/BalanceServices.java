package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.Balance;

import java.math.BigDecimal;

/**
 * @author Egor Malko
 */

@Service
public class BalanceServices {
    public Balance getBalance() {
        Balance balance = new Balance();
        balance.setAmount(BigDecimal.valueOf(118000));
        balance.setIncome(BigDecimal.valueOf(130000));
        balance.setOutcome(BigDecimal.valueOf(12000));
        return balance;
    }
}
