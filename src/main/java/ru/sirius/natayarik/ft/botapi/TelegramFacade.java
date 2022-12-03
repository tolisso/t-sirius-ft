package ru.sirius.natayarik.ft.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class TelegramFacade {
    private List<InputMessageHandler> handlers;

    public List<SendMessage> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getFrom().getId();
            return Collections.emptyList();
        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            final int chatId = callbackQuery.getFrom().getId();
            return Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
    }

    private InputMessageHandler getHandlerByCallBackQuery(String query) { //запросы от кнопок
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery()
                        .stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
