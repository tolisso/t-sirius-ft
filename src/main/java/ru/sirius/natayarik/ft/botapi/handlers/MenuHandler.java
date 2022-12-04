package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.cache.StateCash;
import ru.sirius.natayarik.ft.services.MessageMenuService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class MenuHandler implements InputMessageHandler {
    private final MessageMenuService messageMenuService;
    private final StateCash stateCash;
    private final OperationHandler operationHandler;

    public MenuHandler(MessageMenuService messageMenuService, StateCash stateCash, OperationHandler operationHandler) {
        this.messageMenuService = messageMenuService;
        this.stateCash = stateCash;
        this.operationHandler = operationHandler;
    }

    @Override
    public List<SendMessage> handle(String message, int userId, long chatId) {
        BotState state = stateCash.getBotState(userId);
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case START:
                stateCash.saveBotState(userId, BotState.MENU);
                reply.add(messageMenuService.getMainMenuMessage(chatId, "Добро пожаловать! Воспользуйтесь главным меню."));
                break;
            case MENU:
                switch (message) {
                    case "Создать операцию":
                        stateCash.saveBotState(userId, BotState.CREATE_OPERATIONS);
                        reply = operationHandler.handle(message, userId, chatId);
                        break;
                    case "Посмотреть мои операции":
                        stateCash.saveBotState(userId, BotState.GET_OPERATIONS);
                        reply = operationHandler.handle(message, userId, chatId);
                        break;
                    case "Сменить кошелек":
                        break;
                    case "Расшарить текущий кошелек":
                        break;
                }
                break;
        }
        return reply;
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
