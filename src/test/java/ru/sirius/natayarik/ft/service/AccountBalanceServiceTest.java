package ru.sirius.natayarik.ft.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.OperationRepository;
import ru.sirius.natayarik.ft.services.AccountBalanceService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class AccountBalanceServiceTest {
    private final AccountBalanceService accountBalanceService;

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    public AccountBalanceServiceTest() {
        this.operationRepository = Mockito.mock(OperationRepository.class);
        this.accountRepository = Mockito.mock(AccountRepository.class);

        this.accountBalanceService = new AccountBalanceService(operationRepository, accountRepository);
    }

    @Test
    public void testGetSumByType() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(BigDecimal.valueOf(1000));
        accountEntity.setName("test");

        OperationEntity operationEntity1 = new OperationEntity();
        operationEntity1.setAmount(BigDecimal.valueOf(1000));
        operationEntity1.setCategory(new CategoryEntity(null, null, Type.INCOME));

        OperationEntity operationEntity2 = new OperationEntity();
        operationEntity2.setAmount(BigDecimal.valueOf(1000));
        operationEntity2.setCategory(new CategoryEntity(null, null, Type.OUTCOME));

        OperationEntity operationEntity3 = new OperationEntity();
        operationEntity3.setAmount(BigDecimal.valueOf(2000));
        operationEntity3.setCategory(new CategoryEntity(null, null, Type.INCOME));

        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));
        Mockito.when(operationRepository.findAllByAccount(accountEntity)).thenReturn(List.of(
                operationEntity1,
                operationEntity2,
                operationEntity3
        ));

        Assertions.assertEquals(BigDecimal.valueOf(3000), accountBalanceService.getSumByType(1L, Type.INCOME));
    }
}
