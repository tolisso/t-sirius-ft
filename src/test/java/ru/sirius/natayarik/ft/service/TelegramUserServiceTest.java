package ru.sirius.natayarik.ft.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.TelegramUserRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;
import ru.sirius.natayarik.ft.services.CurrentUserService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class TelegramUserServiceTest {

    private final TelegramUserService telegramUserService;

    private final TelegramUserRepository telegramUserRepository;
    private final AccountRepository accountRepository;
    private final CurrentUserService currentUserService;
    private final UserToAccountRepository userToAccountRepository;

    public TelegramUserServiceTest() {
        this.telegramUserRepository = Mockito.mock(TelegramUserRepository.class);
        this.accountRepository = Mockito.mock(AccountRepository.class);
        this.currentUserService = Mockito.mock(CurrentUserService.class);
        this.userToAccountRepository = Mockito.mock(UserToAccountRepository.class);

        this.telegramUserService = new TelegramUserService(
                telegramUserRepository,
                accountRepository,
                currentUserService,
                userToAccountRepository
        );
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setUserName("userName");

        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");

        AccountEntity accountEntity = new AccountEntity();

        UserToAccountEntity toAccountEntity = new UserToAccountEntity();
        toAccountEntity.setAccount(accountEntity);

        Mockito.when(currentUserService.getUser()).thenReturn(userEntity);

        Mockito.when(userToAccountRepository.findAllByUser(userEntity)).thenReturn(List.of(
                toAccountEntity
        ));

        Mockito.when(telegramUserRepository.findByUserId("name")).thenReturn(null);

        telegramUserService.create(user, 1);
    }
}
