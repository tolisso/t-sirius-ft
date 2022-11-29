package ru.sirius.natayarik.ft.services;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.repository.UserRepository;

/**
 * @author Yaroslav Ilin
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUserService {
    private UserEntity user;
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUser(final String name) {
        user = userRepository.findByName(name);
    }

    public UserEntity getUser() {
        return user;
    }

}
