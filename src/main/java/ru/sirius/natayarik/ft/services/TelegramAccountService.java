package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Service
public class TelegramAccountService {
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;
    private final MessageMenuService messageMenuService;
    private final CurrentUserService currentUserService;

    public TelegramAccountService(TelegramUserService telegramUserService, AccountService accountService, MessageMenuService messageMenuService, CurrentUserService currentUserService) {
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
        this.messageMenuService = messageMenuService;
        this.currentUserService = currentUserService;
    }

    public List<SendMessage> sendAllAccountsAndNew(long chatId) {
        telegramUserService.setBotState(BotState.CHOSE_ACCOUNT);
        Map<String, String> accountMap = new HashMap<>();
        accountService.getAll()
                .forEach(account -> accountMap.put(String.valueOf(account.getId()), account.getName()));
        accountMap.put("new", "Создать новый");
        return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Выберите кошелек"),
                messageMenuService.getInlineMenuMessage(chatId, "или создайте новый:", accountMap));
    }

    public List<SendMessage> createAccount(long chatId) {
        telegramUserService.setBotState(BotState.CREATE_ACCOUNT);
        return List.of(messageMenuService.getWithoutMenuMessage(chatId, "Введите имя кошелька:"));
    }

    public List<SendMessage> choseAccount(long chatId, String userAnswer) {
        try {
            long accountId = Long.parseLong(userAnswer);
            AccountDTO account = accountService.getAccountById(accountId);
            telegramUserService.setCurrentAccount(accountId);
            telegramUserService.setBotState(BotState.MENU);
            return List.of(messageMenuService.getMainMenuMessage(chatId,
                    String.format("Вы перешли в кошелёк %s, баланс %.2f", account.getName(), account.getBalance())));
        } catch (NumberFormatException | NotFoundDataException | NullPointerException e) {
            List<SendMessage> result = sendAllAccountsAndNew(chatId);
            result.add(0, messageMenuService.getAskClickMessage(chatId));
            return result;
        }
    }

    public List<SendMessage> saveAccount(long chatId, String userAnswer) {
        AccountDTO newAccount = new AccountDTO();
        newAccount.setName(userAnswer);
        newAccount.setUserId(currentUserService.getUser().getId());
        newAccount.setBalance(new BigDecimal(0));
        AccountDTO account = accountService.create(newAccount);
        telegramUserService.setCurrentAccount(account.getId());
        telegramUserService.setBotState(BotState.MENU);
        return List.of(messageMenuService.getMainMenuMessage(chatId,
                String.format("Вы создали кошелёк %s и перешли в него, баланс %.2f", account.getName(), account.getBalance())));
    }
}
