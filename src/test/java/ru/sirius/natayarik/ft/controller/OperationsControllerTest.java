package ru.sirius.natayarik.ft.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.services.OperationService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class OperationsControllerTest {
    private final OperationsController operationsController;

    private final OperationService operationService;

    public OperationsControllerTest() {
        this.operationService = Mockito.mock(OperationService.class);

        this.operationsController = new OperationsController(operationService);
    }

    @Test
    public void testGetOperationFromId() {
        FullOperationDTO fullOperationDTO = new FullOperationDTO();
        fullOperationDTO.setAccountId(1);
        fullOperationDTO.setAmount(BigDecimal.TEN);
        fullOperationDTO.setCreationDate(ZonedDateTime.now());

        Mockito.when(operationService.getFromId(1)).thenReturn(fullOperationDTO);

        Assertions.assertEquals(fullOperationDTO, operationsController.getOperationFromId(1));
    }
}
