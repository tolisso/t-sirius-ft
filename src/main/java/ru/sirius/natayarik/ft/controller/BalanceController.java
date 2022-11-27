package ru.sirius.natayarik.ft.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sirius.natayarik.ft.data.Balance;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @GetMapping("/get")
    @ResponseBody
    public Balance getBalance() {
        return null;
    }

}
