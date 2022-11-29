package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.entity.AccountEntity;

/**
 * @author Yaroslav Ilin
 */

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
}
