package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class MenuHandler implements InputMessageHandler {
    private final MessageMenuService messageMenuService;
    private final OperationHandler operationHandler;
    private final AccountHandler accountHandler;
    private final TelegramUserService telegramUserService;

    public MenuHandler(MessageMenuService messageMenuService, OperationHandler operationHandler, AccountHandler accountHandler, TelegramUserService telegramUserService) {
        this.messageMenuService = messageMenuService;
        this.operationHandler = operationHandler;
        this.accountHandler = accountHandler;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public List<SendMessage> handle(String message, String userId, long chatId) {
        BotState state = telegramUserService.getBotState(userId);
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case START:
                telegramUserService.setBotState(userId, BotState.MENU);
                reply.add(messageMenuService.getMainMenuMessage(chatId, "Добро пожаловать! Воспользуйтесь главным меню."));
                break;
            case MENU:
                switch (message) {
                    case "Создать операцию":
                        telegramUserService.setBotState(userId, BotState.CREATE_OPERATIONS);
                        reply = operationHandler.handle(message, userId, chatId);
                        break;
                    case "Посмотреть мои операции":
                        telegramUserService.setBotState(userId, BotState.GET_OPERATIONS);
                        reply = operationHandler.handle(message, userId, chatId);
                        break;
                    case "Сменить кошелек":
                        telegramUserService.setBotState(userId, BotState.CHANGE_ACCOUNT);
                        reply = accountHandler.handle(message, userId, chatId);
                        break;
                    case "Расшарить текущий кошелек":
                        reply = List.of(messageMenuService.getMainMenuMessage(chatId, "Данная функция пока не поддерживается"));
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
