package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.cache.OperationCash;
import ru.sirius.natayarik.ft.cache.StateCash;
import ru.sirius.natayarik.ft.services.MainMenuService;

import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class MenuHandler implements InputMessageHandler {
    private final MainMenuService mainMenuService;
    private final StateCash stateCash;
    private final OperationCash operationCash;

    public MenuHandler(MainMenuService mainMenuService, StateCash stateCash, OperationCash operationCash) {
        this.mainMenuService = mainMenuService;
        this.stateCash = stateCash;
        this.operationCash = operationCash;
    }

    @Override
    public List<SendMessage> handle(String message, int userId, long chatId) {
        BotState state = stateCash.getBotState(userId);
        SendMessage reply = new SendMessage();
        reply.setChatId(String.valueOf(chatId));
        switch (state) {
            case START:
                stateCash.saveBotState(userId, BotState.MENU);
                reply = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");
                break;
            case MENU:
                if (message.equals("Создать операцию")) {
                    stateCash.saveBotState(userId, BotState.ASK_AMOUNT);
                    reply.setText("Введите сумму операции");
                    operationCash.createOperation(userId);
                }
        }
        return List.of(reply);
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.START, BotState.MENU);
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
