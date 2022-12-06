package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.services.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class AccountHandler implements InputMessageHandler {
    private final TelegramUserService telegramUserService;
    private final TelegramAccountService telegramAccountService;

    public AccountHandler(TelegramUserService telegramUserService, TelegramAccountService telegramAccountService) {
        this.telegramUserService = telegramUserService;
        this.telegramAccountService = telegramAccountService;
    }

    @Override
    public List<SendMessage> handle(String message, long chatId) {
        BotState state = telegramUserService.getBotState();
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case CHANGE_ACCOUNT:
                return telegramAccountService.sendAllAccountsAndNew(chatId);
            case CHOSE_ACCOUNT:
                if (message.equals("new")) {
                    return telegramAccountService.createAccount(chatId);
                } else {
                    return telegramAccountService.choseAccount(chatId, message);
                }
            case CREATE_ACCOUNT:
                return telegramAccountService.saveAccount(chatId, message);
        }
        return reply;
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.CHOSE_ACCOUNT, BotState.CHANGE_ACCOUNT, BotState.CREATE_ACCOUNT);
    }
}
