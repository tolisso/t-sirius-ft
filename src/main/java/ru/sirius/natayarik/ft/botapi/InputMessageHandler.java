package ru.sirius.natayarik.ft.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;

import java.util.List;

/**
 * @author Egor Malko
 */
public interface InputMessageHandler {
   List<SendMessage> handle(String message, long chatId);
   List<BotState> getOperatedState();
}
