package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Malko
 */
@Service
public class MainMenuService {
   public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
      final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
      final SendMessage mainMenuMessage =
              createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

      return mainMenuMessage;
   }

   private ReplyKeyboardMarkup getMainMenuKeyboard() {
      final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
      replyKeyboardMarkup.setSelective(true);
      replyKeyboardMarkup.setResizeKeyboard(true);

      List<KeyboardRow> keyboard = new ArrayList<>();
      KeyboardRow row1 = new KeyboardRow();
      row1.add(new KeyboardButton("Создать операцию"));
      keyboard.add(row1);
      replyKeyboardMarkup.setKeyboard(keyboard);
      return replyKeyboardMarkup;
   }

   private SendMessage createMessageWithKeyboard(final long chatId,
                                                 String textMessage,
                                                 final ReplyKeyboardMarkup replyKeyboardMarkup) {
      final SendMessage sendMessage = new SendMessage();
      sendMessage.enableMarkdown(true);
      sendMessage.setChatId(String.valueOf(chatId));
      sendMessage.setText(textMessage);
      if (replyKeyboardMarkup != null) {
         sendMessage.setReplyMarkup(replyKeyboardMarkup);
      }
      return sendMessage;
   }
}
