package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.cache.OperationCash;
import ru.sirius.natayarik.ft.cache.StateCash;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Component
public class OperationHandler implements InputMessageHandler {
    private final StateCash stateCash;
    private final OperationCash operationCash;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public OperationHandler(StateCash stateCash, OperationCash operationCash, CategoryRepository categoryRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.stateCash = stateCash;
        this.operationCash = operationCash;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<SendMessage> handle(String message, int userId, long chatId) {
        BotState state = stateCash.getBotState(userId);
        SendMessage reply = new SendMessage();
        reply.setChatId(String.valueOf(chatId));
        switch (state) {
            case ASK_AMOUNT:
                try {
                    BigDecimal amount = new BigDecimal(message);
                    if (amount.compareTo(new BigDecimal(0)) <= 0) {
                        throw new NumberFormatException();
                    }
                    operationCash.addAmount(userId, amount);
                    reply.setText("Выберите тип операции");
                    Map<String, String> typeMap = new HashMap<>();
                    typeMap.put(TypeDTO.INCOME.name(), TypeDTO.INCOME.getLabel());
                    typeMap.put(TypeDTO.OUTCOME.name(), TypeDTO.OUTCOME.getLabel());
                    reply.setReplyMarkup(getKeyboard(typeMap));
                    stateCash.saveBotState(userId, BotState.ASK_TYPE);
                } catch (NumberFormatException e) {
                    reply.setText("Сумма операции должна быть положительным числом. Попробуйте ещё раз!");
                }
                break;
            case ASK_TYPE:
                reply.setText("Выберите категорию:");
                Map<String, String> categoryMap = new HashMap<>();
                if (message.equals("INCOME")) {
                    categoryRepository.findAllByTypeDTOAndUser(TypeDTO.INCOME, userRepository.findByName(String.valueOf(userId)))
                            .forEach(category -> categoryMap.put(String.valueOf(category.getId()), category.getName()));
                } else {
                    categoryRepository.findAllByTypeDTOAndUser(TypeDTO.OUTCOME, userRepository.findByName(String.valueOf(userId)))
                            .forEach(category -> categoryMap.put(String.valueOf(category.getId()), category.getName()));
                }
                reply.setReplyMarkup(getKeyboard(categoryMap));
                stateCash.saveBotState(userId, BotState.ASK_CATEGORY);
                break;
            case ASK_CATEGORY:
                stateCash.saveBotState(userId, BotState.MENU);
                operationCash.addCategory(userId, Long.parseLong(message));
                operationCash.addAccount(userId, accountRepository.findByUser(userRepository.findByName(String.valueOf(userId))).getId());
                OperationCreateDTO result = operationCash.saveOperation(userId);
                reply.setText("Операция успешно создана!"); //TODO показывать баланс после создания
        }

        return List.of(reply);
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.ASK_AMOUNT, BotState.ASK_TYPE, BotState.ASK_CATEGORY);
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return null;
    }

    private InlineKeyboardMarkup getKeyboard(Map<String, String> buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (String name: buttons.keySet()) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setCallbackData(name);
            button.setText(buttons.get(name));
            keyboardButtonsRow.add(button);
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
