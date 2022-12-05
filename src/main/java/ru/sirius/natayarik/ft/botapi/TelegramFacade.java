package ru.sirius.natayarik.ft.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sirius.natayarik.ft.services.CurrentUserService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class TelegramFacade {
    private final List<InputMessageHandler> handlers;
    private final TelegramUserService telegramUserService;
    private final CurrentUserService currentUserService;

    public TelegramFacade(List<InputMessageHandler> handlers, TelegramUserService telegramUserService, CurrentUserService currentUserService) {
        this.handlers = handlers;
        this.telegramUserService = telegramUserService;
        this.currentUserService = currentUserService;
    }

    public List<SendMessage> handleUpdate(Update update) { //TODO сделать менее повторный if
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String userId = String.valueOf(message.getFrom().getId());
            currentUserService.setUser(userId);
            BotState state = telegramUserService.getBotState();
            return getHandlerByState(state).handle(message.getText(), message.getChatId());
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String userId = String.valueOf(callbackQuery.getFrom().getId());
            currentUserService.setUser(userId);
            BotState state = telegramUserService.getBotState();
            return getHandlerByState(state).handle(callbackQuery.getData(), callbackQuery.getMessage().getChatId());
        } else {
            return Collections.emptyList();
        }
    }

    private InputMessageHandler getHandlerByState(BotState state) {
        return handlers.stream()
                .filter(h -> (h.getOperatedState() != null) && (h.getOperatedState().contains(state)))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
