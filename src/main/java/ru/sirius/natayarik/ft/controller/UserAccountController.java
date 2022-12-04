package ru.sirius.natayarik.ft.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sirius.natayarik.ft.services.UserToAccountService;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/user_account")
public class UserAccountController {
    private final UserToAccountService userToAccountService;

    public UserAccountController(UserToAccountService userToAccountService) {
        this.userToAccountService = userToAccountService;
    }

    @Operation(summary = "Метод для добавления пользователя в кошелек")
    @PostMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@PathVariable("accountId") long accountId) {
        userToAccountService.addUserToAccount(accountId);
    }
}
