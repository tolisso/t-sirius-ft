package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.TelegramUserRepository;

/**
 * @author Egor Malko
 */
@Service
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final InitializationUserService initializationUserService;
    private final AccountRepository accountRepository;

    public TelegramUserService(TelegramUserRepository telegramUserRepository, InitializationUserService initializationUserService, AccountRepository accountRepository) {
        this.telegramUserRepository = telegramUserRepository;
        this.initializationUserService = initializationUserService;
        this.accountRepository = accountRepository;
    }

    public BotState getBotState(String userId) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(userId);
        if (user == null) {
            TelegramUserEntity newUser = new TelegramUserEntity();
            UserDTO newUserDTO = new UserDTO();
            newUserDTO.setName(userId);
            UserEntity newUserEntity = initializationUserService.initializationUser(newUserDTO);
            newUser.setUserId(newUserEntity.getName());
            newUser.setState(BotState.START);
            newUser.setAccountEntity(accountRepository.findByUser(newUserEntity));
            user = telegramUserRepository.save(newUser);
        }
        return user.getState();
    }

    public void setBotState(String userId, BotState state) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(userId);
        user.setState(state);
        telegramUserRepository.save(user);
    }

    public void setCurrentAccount(String userId, long accountId) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(userId);
        user.setAccountEntity(accountRepository.findById(accountId).orElseThrow(RuntimeException::new));
        telegramUserRepository.save(user);
    }

    public long getCurrentAccountId(String userId) {
        return telegramUserRepository.findByUserId(userId).getAccountEntity().getId();
    }
}
