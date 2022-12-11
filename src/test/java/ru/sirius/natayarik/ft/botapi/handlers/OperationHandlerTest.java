package ru.sirius.natayarik.ft.botapi.handlers;

import liquibase.pro.packaged.M;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.TelegramOperationService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

public class OperationHandlerTest {
    private final TelegramUserService telegramUserService;
    private final TelegramOperationService telegramOperationService;
    private final MessageMenuService messageMenuService;

    private final OperationHandler operationHandler;

    public OperationHandlerTest() {
        this.telegramUserService = Mockito.mock(TelegramUserService.class);
        this.telegramOperationService = Mockito.mock(TelegramOperationService.class);
        this.messageMenuService = Mockito.mock(MessageMenuService.class);

        this.operationHandler = new OperationHandler(
                telegramUserService,
                telegramOperationService,
                messageMenuService
        );
    }

    @Test
    public void testHandleMessageCreateOperationsState() {
        Mockito.when(telegramUserService.getBotState()).thenReturn(BotState.CREATE_OPERATIONS);

        operationHandler.handleMessage(new Message(), 1);

        Mockito.verify(telegramOperationService).createOperation(1);
    }

    @Test
    public void testHandleMessageAskAmountState() {
        Mockito.when(telegramUserService.getBotState()).thenReturn(BotState.ASK_AMOUNT);

        Message message = new Message();
        message.setText("message");

        operationHandler.handleMessage(message, 1);

        Mockito.verify(telegramOperationService).addAmount(1, message.getText());
    }

    @Test
    public void testHandleMessageAskTypeState() {
        Mockito.when(telegramUserService.getBotState()).thenReturn(BotState.ASK_TYPE);

        Message message = new Message();
        message.setText("message");
        operationHandler.handleMessage(message, 1);

        Mockito.verify(telegramOperationService).choseType(1, message.getText());
    }
}
