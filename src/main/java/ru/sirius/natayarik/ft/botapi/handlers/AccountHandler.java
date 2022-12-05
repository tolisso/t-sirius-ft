package ru.sirius.natayarik.ft.botapi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.botapi.InputMessageHandler;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;
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
    private final AccountRepository accountRepository; //пользоваться сервисами а не репозиториями
    private final UserRepository userRepository;
    private final MessageMenuService messageMenuService;

    public AccountHandler(TelegramUserService telegramUserService, AccountRepository accountRepository, UserRepository userRepository, MessageMenuService messageMenuService) {
        this.telegramUserService = telegramUserService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.messageMenuService = messageMenuService;
    }

    @Override
    public List<SendMessage> handle(String message, String userId, long chatId) {
        BotState state = telegramUserService.getBotState(userId);
        List<SendMessage> reply = new ArrayList<>();
        switch (state) {
            case CHANGE_ACCOUNT:
                Map<String, String> accountMap = new HashMap<>();
                accountRepository.findAllByUser(userRepository.findByName(String.valueOf(userId)))
                        .forEach(account -> accountMap.put(String.valueOf(account.getId()), account.getName()));
                accountMap.put("new", "Создать новый");
                reply.add(messageMenuService.getWithoutMenuMessage(chatId, "Выберите кошелек"));
                reply.add(messageMenuService.getInlineMenuMessage(chatId, "или создайте новый:", accountMap));
                telegramUserService.setBotState(userId, BotState.CHOSE_ACCOUNT);
                break;
            case CHOSE_ACCOUNT:
                if (message.equals("new")) {
                    reply.add(messageMenuService.getWithoutMenuMessage(chatId, "Введите имя кошелька:"));
                    telegramUserService.setBotState(userId, BotState.CREATE_ACCOUNT);
                } else {
                    long accountId = Long.parseLong(message);
                    AccountEntity account = accountRepository.findById(accountId).orElseThrow(RuntimeException::new);
                    reply.add(messageMenuService.getMainMenuMessage(chatId,
                            String.format("Вы перешли в кошелёк %s, баланс %.2f", account.getName(), account.getBalance())));
                    telegramUserService.setCurrentAccount(userId, accountId);
                    telegramUserService.setBotState(userId, BotState.MENU);
                }
                break;
            case CREATE_ACCOUNT:
                AccountEntity newAccount = new AccountEntity();
                newAccount.setName(message);
                newAccount.setUser(userRepository.findByName(userId));
                AccountEntity account = accountRepository.save(newAccount);
                telegramUserService.setCurrentAccount(userId, account.getId());
                reply.add(messageMenuService.getMainMenuMessage(chatId,
                        String.format("Вы создали кошелёк %s и перешли в него, баланс %.2f", account.getName(), account.getBalance())));
                telegramUserService.setBotState(userId, BotState.MENU);
                break;
        }
        return reply;
    }

    @Override
    public List<BotState> getOperatedState() {
        return List.of(BotState.CHOSE_ACCOUNT, BotState.CHANGE_ACCOUNT, BotState.CREATE_ACCOUNT);
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return null;
    }
}
