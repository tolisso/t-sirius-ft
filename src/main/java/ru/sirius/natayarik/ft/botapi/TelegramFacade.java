package ru.sirius.natayarik.ft.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sirius.natayarik.ft.cache.StateCash;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;
import ru.sirius.natayarik.ft.repository.TelegramUserRepository;

import java.util.Collections;
import java.util.List;

/**
 * @author Egor Malko
 */
@Component
public class TelegramFacade {
    private final List<InputMessageHandler> handlers;
    private final TelegramUserRepository telegramUserRepository;
    private final StateCash stateCash;

    public TelegramFacade(List<InputMessageHandler> handlers, TelegramUserRepository telegramUserRepository, StateCash stateCash) {
        this.handlers = handlers;
        this.telegramUserRepository = telegramUserRepository;
        this.stateCash = stateCash;
    }

    public List<SendMessage> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            BotState state = stateCash.getBotState(message.getFrom().getId());

            /*int userId = update.getMessage().getFrom().getId();
            TelegramUserEntity telegramUser = telegramUserRepository.findById(userId)
                    .orElseGet(() -> telegramUserRepository.save(new TelegramUserEntity(userId)));*/
            return getHandlerByState(state).handle(message.getText(), message.getFrom().getId(), message.getChatId());
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            BotState state = stateCash.getBotState(callbackQuery.getFrom().getId());
            /*int userId = update.getMessage().getFrom().getId();
            TelegramUserEntity telegramUser = telegramUserRepository.findById(userId)
                    .orElseGet(() -> telegramUserRepository.save(new TelegramUserEntity(userId)));*/
            return getHandlerByState(state).handle(callbackQuery.getData(), callbackQuery.getFrom().getId(), callbackQuery.getMessage().getChatId());
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

    private InputMessageHandler getHandlerByCallBackQuery(String query) { //запросы от кнопок у сообщения изменить!!!
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery()
                        .stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
