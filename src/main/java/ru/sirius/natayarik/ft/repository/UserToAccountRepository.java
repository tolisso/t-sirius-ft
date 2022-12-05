package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.data.RoleDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;

/**
 * @author Yaroslav Ilin
 */

public interface UserToAccountRepository extends CrudRepository<UserToAccountEntity, Long> {
    UserToAccountEntity findByAccountAndRole(AccountEntity account, RoleDTO role);
    UserToAccountEntity findByAccountAndUser(AccountEntity account, UserEntity user);
}
