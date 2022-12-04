package ru.sirius.natayarik.ft.cache;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.botapi.BotState;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.services.InitializationUserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Component
public class StateCash {
    private final Map<Integer, BotState> stateMap = new HashMap<>();
    private final InitializationUserService initializationUserService;

    public StateCash(InitializationUserService initializationUserService) {
        this.initializationUserService = initializationUserService;
    }

    public void saveBotState(int userId, BotState state) {
        stateMap.put(userId, state);
    }

    public BotState getBotState(int userId) {
        if (!stateMap.containsKey(userId)) {
            stateMap.put(userId, BotState.START);
            UserDTO user = new UserDTO();
            user.setName(String.valueOf(userId));
            initializationUserService.initializationUser(user);
        }
        return stateMap.get(userId);
    }
}
