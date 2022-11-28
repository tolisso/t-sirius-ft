package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.OperationEntity;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

public interface OperationRepository extends CrudRepository<OperationEntity, Long> {
//    TODO: В целом кажется id нужно бы переделать в long
    List<OperationEntity> findAllByAccount(AccountEntity account); // TODO: Существует метод findAllById в CrudRepository, но он принимает Iterable<Integer>
}
