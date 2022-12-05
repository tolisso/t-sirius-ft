package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;
import ru.sirius.natayarik.ft.repository.TelegramUserRepository;

/**
 * @author Egor Malko
 */
@Service
public class TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final InitializationUserService initializationUserService;

    public TelegramUserService(TelegramUserRepository telegramUserRepository, InitializationUserService initializationUserService) {
        this.telegramUserRepository = telegramUserRepository;
        this.initializationUserService = initializationUserService;
    }

    public BotState getBotState(String userId) {
        TelegramUserEntity newUser = new TelegramUserEntity();
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName(userId);
        newUser.setUserId(initializationUserService.initializationUser(newUserDTO).getName());
        newUser.setState(BotState.START);
        TelegramUserEntity user = telegramUserRepository.findByUserId(userId);
        if (user == null) {
            user = telegramUserRepository.save(newUser);
        }
        return user.getState();
    }

    public void setBotState(String userId, BotState state) {
        TelegramUserEntity user = telegramUserRepository.findByUserId(userId);
        user.setState(state);
        telegramUserRepository.save(user);
    }
}
