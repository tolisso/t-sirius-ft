package ru.sirius.natayarik.ft.service;

import liquibase.pro.packaged.U;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.data.Role;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.services.*;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class TelegramAccountServiceTest {
    private final TelegramAccountService telegramAccountService;

    private final TelegramUserService telegramUserService;
    private final AccountService accountService;
    private final MessageMenuService messageMenuService;
    private final CurrentUserService currentUserService;
    private final UserToAccountService userToAccountService;

    public TelegramAccountServiceTest() {
        this.telegramUserService = Mockito.mock(TelegramUserService.class);
        this.accountService = Mockito.mock(AccountService.class);
        this.messageMenuService = Mockito.mock(MessageMenuService.class);
        this.currentUserService = Mockito.mock(CurrentUserService.class);
        this.userToAccountService = Mockito.mock(UserToAccountService.class);

        this.telegramAccountService = new TelegramAccountService(
                telegramUserService,
                accountService,
                messageMenuService,
                currentUserService,
                userToAccountService
        );
    }

    @Test
    public void testSendAllAccountsAndNew() {
        AccountEntity accountEntity1 = new AccountEntity();
        accountEntity1.setId(1L);
        accountEntity1.setName("Account");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setName("name");

        UserToAccountEntity userToAccountEntity1 = new UserToAccountEntity();
        userToAccountEntity1.setUser(userEntity1);

        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setUserName("userName");

        SendMessage sendMessage1 = new SendMessage();
        sendMessage1.setText("r1");

        SendMessage sendMessage2 = new SendMessage();
        sendMessage1.setText("r2");

        Mockito.when(accountService.getAllEntity()).thenReturn(List.of(
                accountEntity1
        ));

        Mockito.when(userToAccountService.getByAccountAndRole(accountEntity1,
                Role.OWNER)).thenReturn(userToAccountEntity1);
        Mockito.when(telegramUserService.getTelegramUserByUserId("name")).thenReturn(telegramUserEntity);

        Mockito.when(messageMenuService.getWithoutMenuMessage(1, "Выберите кошелек")).thenReturn(
                sendMessage1
        );

        Mockito.when(messageMenuService.getInlineMenuMessage(1, "или создайте новый:", Map.of(
            "1", "Account (userName)",
                "new", "Создать новый"
        ))).thenReturn(sendMessage2);

        List<SendMessage> sendMessages = telegramAccountService.sendAllAccountsAndNew(1);

        Assertions.assertEquals("r2", sendMessages.get(0).getText());
        Assertions.assertNull(sendMessages.get(1).getText());
    }


}
