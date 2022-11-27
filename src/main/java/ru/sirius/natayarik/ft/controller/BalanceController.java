package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sirius.natayarik.ft.data.Balance;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/balance")
public class BalanceController {
    private final BalanceServices balanceServices;

    @Autowired
    public BalanceController(BalanceServices balanceServices) {
        this.balanceServices = balanceServices;
    }

    @GetMapping("/get")
    @ResponseBody
    public Balance getBalance() {
        return balanceServices.getBalance();
    }

}
