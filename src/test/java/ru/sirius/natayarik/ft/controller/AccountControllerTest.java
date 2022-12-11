package ru.sirius.natayarik.ft.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.services.AccountService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class AccountControllerTest {
    private final AccountController accountController;

    private final AccountService accountService;

    public AccountControllerTest() {
        this.accountService = Mockito.mock(AccountService.class);
        this.accountController = new AccountController(accountService);
    }

    @Test
    public void testGetAllAccounts() {
        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setName("name1");

        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO1.setName("name2");

        AccountDTO accountDTO3 = new AccountDTO();
        accountDTO1.setName("name3");

        AccountDTO accountDTO4 = new AccountDTO();
        accountDTO1.setName("name4");

        AccountDTO accountDTO5 = new AccountDTO();
        accountDTO1.setName("name5");

        Mockito.when(accountService.getAll()).thenReturn(
                List.of(
                        accountDTO1,
                        accountDTO2,
                        accountDTO3,
                        accountDTO4,
                        accountDTO5
                )
        );

        Assertions.assertTrue(accountController.getAllAccounts().containsAll(List.of(
                accountDTO1,
                accountDTO2,
                accountDTO3,
                accountDTO4,
                accountDTO5
        )));
    }
}
