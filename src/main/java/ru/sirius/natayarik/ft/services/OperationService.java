package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.repository.OperationRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService; // TODO: не понятно можно ли так делать.

    public OperationService(OperationRepository operationRepository, AccountService accountService) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }


    public OperationEntity create(final OperationEntity operation) {
//        operation.setId(42);
//        return operation;
        return operationRepository.save(operation);
    }

    public List<OperationEntity> getAll(final long accountId) {
        return operationRepository.findAllByAccount(accountService.getAccountById(accountId));
    }

    public OperationEntity getFromId(final long operationId) {
      return operationRepository.findById(operationId).orElse(null);
    }

    public void delete(final int operationId) {
        operationRepository.delete(getFromId(operationId));
    }

    public OperationEntity change(final OperationEntity operation) {
        return operationRepository.save(operation);
    }
}
