package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Malko
 */
@Service
public class MessageMenuService {
   public SendMessage getWithoutMenuMessage(final long chatId, final String textMessage) {
      ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(true);
      final SendMessage withoutMenuMessage =
              createMessageWithKeyboard(chatId, textMessage, replyKeyboardRemove);

      return withoutMenuMessage;
   }

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
      row1.add(new KeyboardButton("Посмотреть мои операции"));
      keyboard.add(row1);
      KeyboardRow row2 = new KeyboardRow();
      row2.add(new KeyboardButton("Сменить кошелек")); //В нем последняя кнопка - создать новый кошелек
      row2.add(new KeyboardButton("Расшарить текущий кошелек"));
      keyboard.add(row2);
      replyKeyboardMarkup.setKeyboard(keyboard);
      return replyKeyboardMarkup;
   }

   private SendMessage createMessageWithKeyboard(final long chatId,
                                                 String textMessage,
                                                 final ReplyKeyboard replyKeyboardMarkup) {
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
