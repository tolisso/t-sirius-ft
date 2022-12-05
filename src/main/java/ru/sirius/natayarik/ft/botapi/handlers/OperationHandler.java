package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.cache.OperationCash;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.services.CategoryService;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.OperationService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Component
public class OperationHandler implements InputMessageHandler {
    private final TelegramUserService telegramUserService;
    private final OperationCash operationCash;
    private final CategoryService categoryService;
    private final OperationService operationService;
    private final MessageMenuService messageMenuService;

    public OperationHandler(TelegramUserService telegramUserService, OperationCash operationCash, CategoryService categoryService, OperationService operationService, MessageMenuService messageMenuService) {
        this.telegramUserService = telegramUserService;
        this.operationCash = operationCash;
        this.categoryService = categoryService;
        this.operationService = operationService;
        this.messageMenuService = messageMenuService;
    }

    @Override
    public List<SendMessage> handle(String message, long chatId) {
        BotState state = telegramUserService.getBotState();
        SendMessage reply;
        switch (state) {
            case CREATE_OPERATIONS:
                reply = messageMenuService.getWithoutMenuMessage(chatId, "Введите сумму операции");
                operationCash.createOperation();
                telegramUserService.setBotState(BotState.ASK_AMOUNT);
                break;
            case ASK_AMOUNT:
                try {
                    BigDecimal amount = new BigDecimal(message);
                    if (amount.compareTo(new BigDecimal(0)) <= 0) {
                        throw new NumberFormatException();
                    }
                    operationCash.addAmount(amount);
                    Map<String, String> typeMap = new HashMap<>();
                    typeMap.put(Type.INCOME.name(), Type.INCOME.getLabel());
                    typeMap.put(Type.OUTCOME.name(), Type.OUTCOME.getLabel());
                    reply = messageMenuService.getInlineMenuMessage(chatId, "Выберите тип операции", typeMap);
                    telegramUserService.setBotState(BotState.ASK_TYPE);
                } catch (NumberFormatException e) {
                    reply = messageMenuService.getWithoutMenuMessage(chatId,"Сумма операции должна быть положительным числом. Попробуйте ещё раз!");
                }
                break;
            case ASK_TYPE:
                Map<String, String> categoryMap = new HashMap<>();
                if (message.equals("INCOME")) {
                    categoryService.getAll(Type.INCOME)
                            .forEach(category -> categoryMap.put(String.valueOf(category.getId()), category.getName()));
                    reply = messageMenuService.getInlineMenuMessage(chatId,"Выберите категорию:", categoryMap);
                    telegramUserService.setBotState(BotState.ASK_CATEGORY);
                } else if (message.equals("OUTCOME")) {
                    categoryService.getAll(Type.OUTCOME)
                            .forEach(category -> categoryMap.put(String.valueOf(category.getId()), category.getName()));
                    reply = messageMenuService.getInlineMenuMessage(chatId,"Выберите категорию:", categoryMap);
                    telegramUserService.setBotState(BotState.ASK_CATEGORY);
                } else {
                    Map<String, String> typeMap = new HashMap<>();
                    typeMap.put(Type.INCOME.name(), Type.INCOME.getLabel());
                    typeMap.put(Type.OUTCOME.name(), Type.OUTCOME.getLabel());
                    reply = messageMenuService.getInlineMenuMessage(chatId, "Пожалуйста, нажмите на кнопку, а не пишите что-нибудь. Выберите тип операции", typeMap);
                }
                break;
            case ASK_CATEGORY:
                telegramUserService.setBotState(BotState.MENU);
                operationCash.addCategory(Long.parseLong(message));
                operationCash.addAccount(telegramUserService.getCurrentAccountId());
                operationCash.saveOperation();
                reply = messageMenuService.getMainMenuMessage(chatId, "Операция успешно создана!"); //TODO показывать баланс после создания
                break;
            case GET_OPERATIONS:
                telegramUserService.setBotState(BotState.MENU);
                List<FullOperationDTO> listOperation = operationService.getAll(telegramUserService.getCurrentAccountId());
                StringBuilder result = new StringBuilder();
                if (listOperation.isEmpty()) {
                    result.append("Вы пока не добавили ни одной операции. Чтобы добавить, воспользуйтесь первой кнопкой в меню.");
                } else {
                    result.append("Ваши операции:\n\n");
                }
                for (FullOperationDTO operation: listOperation) {
                    result.append(String.format("Сумма %.2f, тип %s, категория %s, дата создания %tc.\n", operation.getAmount(), operation.getCategoryDTO().getType(), operation.getCategoryDTO().getName(), operation.getCreationDate()));
                }
                reply = messageMenuService.getMainMenuMessage(chatId, result.toString());
                break;
            default:
                reply = messageMenuService.getMainMenuMessage(chatId, "");
        }
        return List.of(reply);
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.ASK_AMOUNT, BotState.ASK_TYPE, BotState.ASK_CATEGORY);
    }
}
