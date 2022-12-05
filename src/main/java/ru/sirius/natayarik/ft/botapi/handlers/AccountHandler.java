package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.services.AccountService;
import ru.sirius.natayarik.ft.services.CurrentUserService;
import ru.sirius.natayarik.ft.services.MessageMenuService;
import ru.sirius.natayarik.ft.services.TelegramUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Component
public class AccountHandler implements InputMessageHandler {
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;
    private final MessageMenuService messageMenuService;
    private final CurrentUserService currentUserService;

    public AccountHandler(TelegramUserService telegramUserService, AccountService accountService, MessageMenuService messageMenuService, CurrentUserService currentUserService) {
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
        this.messageMenuService = messageMenuService;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<SendMessage> handle(String message, long chatId) {
        BotState state = telegramUserService.getBotState();
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case CHANGE_ACCOUNT:
                Map<String, String> accountMap = new HashMap<>();
                accountService.getAll()
                        .forEach(account -> accountMap.put(String.valueOf(account.getId()), account.getName()));
                accountMap.put("new", "Создать новый");
                reply.add(messageMenuService.getWithoutMenuMessage(chatId, "Выберите кошелек"));
                reply.add(messageMenuService.getInlineMenuMessage(chatId, "или создайте новый:", accountMap));
                telegramUserService.setBotState(BotState.CHOSE_ACCOUNT);
                break;
            case CHOSE_ACCOUNT:
                if (message.equals("new")) {
                    reply.add(messageMenuService.getWithoutMenuMessage(chatId, "Введите имя кошелька:"));
                    telegramUserService.setBotState(BotState.CREATE_ACCOUNT);
                } else {
                    long accountId = Long.parseLong(message);
                    AccountDTO account = accountService.getAccountById(accountId);
                    reply.add(messageMenuService.getMainMenuMessage(chatId,
                            String.format("Вы перешли в кошелёк %s, баланс %.2f", account.getName(), account.getBalance())));
                    telegramUserService.setCurrentAccount(accountId);
                    telegramUserService.setBotState(BotState.MENU);
                }
                break;
            case CREATE_ACCOUNT:
                AccountDTO newAccount = new AccountDTO();
                newAccount.setName(message);
                newAccount.setUserId(currentUserService.getUser().getId());
                AccountDTO account = accountService.create(newAccount);
                telegramUserService.setCurrentAccount(account.getId());
                reply.add(messageMenuService.getMainMenuMessage(chatId,
                        String.format("Вы создали кошелёк %s и перешли в него, баланс %.2f", account.getName(), account.getBalance())));
                telegramUserService.setBotState(BotState.MENU);
                break;
        }
        return reply;
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.CHOSE_ACCOUNT, BotState.CHANGE_ACCOUNT, BotState.CREATE_ACCOUNT);
    }
}
