package ru.sirius.natayarik.ft.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * @author Egor Malko
 */
public interface InputMessageHandler {
   List<SendMessage> handle(String message);
   BotState getOperatedState();
   List<String> operatedCallBackQuery();
}
