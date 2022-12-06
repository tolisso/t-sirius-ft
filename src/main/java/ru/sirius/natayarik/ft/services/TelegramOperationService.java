package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.cache.OperationCache;
import ru.sirius.natayarik.ft.converter.AccountConverter;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Service
public class TelegramOperationService {
   private final TelegramUserService telegramUserService;
   private final OperationCache operationCache;
   private final CategoryService categoryService;
   private final OperationService operationService;
   private final MessageMenuService messageMenuService;
   private final UserToAccountService userToAccountService;
   private final AccountConverter accountConverter;

   public TelegramOperationService(TelegramUserService telegramUserService,
                                   OperationCache operationCache,
                                   CategoryService categoryService,
                                   OperationService operationService,
                                   MessageMenuService messageMenuService, UserToAccountService userToAccountService, AccountConverter accountConverter) {
      this.telegramUserService = telegramUserService;
      this.operationCache = operationCache;
      this.categoryService = categoryService;
      this.operationService = operationService;
      this.messageMenuService = messageMenuService;
      this.userToAccountService = userToAccountService;
      this.accountConverter = accountConverter;
   }

   public List<SendMessage> createOperation(long chatId) {
      try {
         operationCache.createOperation();
         operationCache.addAccount(telegramUserService.getCurrentAccount().getId());
         telegramUserService.setBotState(BotState.ASK_AMOUNT);
         return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Введите сумму операции:"));
      } catch (NullPointerException e) {
         telegramUserService.setBotState(BotState.MENU);
         return List.of(messageMenuService.getNotChoseAccountMessage(chatId));
      }
   }

   public List<SendMessage> addAmount(long chatId, String userAnswer) {
      try {
         BigDecimal amount = new BigDecimal(userAnswer);
         if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new NumberFormatException();
         }
         operationCache.addAmount(amount);
         return List.of(getTypeChoseMessage(chatId));
      } catch (NumberFormatException e) {
         return List.of(messageMenuService.getWithoutMenuMessage(chatId,"Сумма операции должна быть положительным числом. Попробуйте ещё раз!"));
      }
   }

   private SendMessage getTypeChoseMessage(long chatId) {
      Map<String, String> typeMap = new HashMap<>();
      typeMap.put(Type.INCOME.name(), Type.INCOME.getLabel());
      typeMap.put(Type.OUTCOME.name(), Type.OUTCOME.getLabel());
      telegramUserService.setBotState(BotState.ASK_TYPE);
      return messageMenuService.getInlineMenuMessage(chatId, "Выберите тип операции", typeMap);
   }

   private SendMessage getCategoryChoseMessage(long chatId, Type type) {
      Map<String, String> categoryMap = new HashMap<>();
      userToAccountService.getAllCategoriesFromAccount(telegramUserService.getCurrentAccount().getId(), type)
              .forEach(category -> categoryMap.put(String.valueOf(category.getId()), category.getName()));
      telegramUserService.setBotState(type.equals(Type.INCOME) ? BotState.ASK_INCOME_CATEGORY : BotState.ASK_OUTCOME_CATEGORY);
      return messageMenuService.getInlineMenuMessage(chatId,"Выберите категорию:", categoryMap);
   }

   public List<SendMessage> choseType(long chatId, String userAnswer) {
      try {
         return List.of(getCategoryChoseMessage(chatId, Type.valueOf(userAnswer)));
      } catch (IllegalArgumentException e) {
         return List.of(messageMenuService.getAskClickMessage(chatId), getTypeChoseMessage(chatId));
      }
   }

   public List<SendMessage> choseCategoryAndSaveOperation(long chatId, String userAnswer) {
      try {
         operationCache.addCategory(Long.parseLong(userAnswer)); //TODO проверка что взяли категорию из текущего кошелька/свою
         operationCache.saveOperation();
         telegramUserService.setBotState(BotState.MENU);
         return List.of(messageMenuService.getMainMenuMessage(chatId, "Операция успешно создана!"));
      } catch (NumberFormatException | NotFoundDataException | PermissionDeniedException e) {
         return List.of(messageMenuService.getAskClickMessage(chatId),
                 getCategoryChoseMessage(chatId, telegramUserService.getBotState().equals(BotState.ASK_INCOME_CATEGORY) ? Type.INCOME : Type.OUTCOME));
      }
   }

   public List<SendMessage> sendOperations(long chatId) {
      try {
         telegramUserService.setBotState(BotState.MENU);
         AccountEntity account = telegramUserService.getCurrentAccount();
         List<FullOperationDTO> listOperation = operationService.getAll(account.getId());
         StringBuilder result = new StringBuilder();
         if (listOperation.isEmpty()) {
            result.append("Вы пока не добавили ни одной операции. Чтобы добавить, воспользуйтесь первой кнопкой в меню.");
         } else {
            result.append(String.format("Баланс: %.2f\nДоход: %.2f\nРасход: %.2f\n\n", account.getBalance(), accountConverter.convertToDTO(account).getIncome(), accountConverter.convertToDTO(account).getOutcome()));
            result.append("Операции:\n\n");
         }
         for (FullOperationDTO operation : listOperation) {
            result.append(String.format("Сумма: %.2f\nТип: %s\nКатегория: %s\nДата создания: %tD %tT\n\n", operation.getAmount(), operation.getCategoryDTO().getType().getLabel(), operation.getCategoryDTO().getName(), operation.getCreationDate(), operation.getCreationDate()));
         }
         return List.of(messageMenuService.getMainMenuMessage(chatId, result.toString()));
      } catch (NullPointerException e) {
         return List.of(messageMenuService.getNotChoseAccountMessage(chatId));
      }
   }
}
