package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.entity.TelegramUserEntity;

import java.util.Optional;

/**
 * @author Egor Malko
 */
public interface TelegramUserRepository extends CrudRepository<TelegramUserEntity, Integer> {
    //TelegramUserEntity findByUserId(String userId);

    @Override
    Optional<TelegramUserEntity> findById(Integer userId);
}
