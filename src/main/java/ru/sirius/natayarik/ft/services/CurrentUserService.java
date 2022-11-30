package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.UserEntity;

/**
 * @author Yaroslav Ilin
 */

@Component
@RequestScope
public class CurrentUserService {
    private UserEntity user;
    private final InitializationUserService initializationUserService;

    public CurrentUserService(InitializationUserService initializationUserService) {
        this.initializationUserService = initializationUserService;
    }


    public void setUser(final String name) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        user = initializationUserService.initializationUser(userDTO);
    }

    public UserEntity getUser() {
        return user;
    }

}
