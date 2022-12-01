package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.OperationsConverter;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.OperationRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final OperationsConverter operationsConverter;
    private final AccountBalanceService accountBalanceService;

    public OperationService(OperationRepository operationRepository, AccountRepository accountRepository, OperationsConverter operationsConverter, AccountBalanceService accountBalanceService) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.operationsConverter = operationsConverter;
        this.accountBalanceService = accountBalanceService;
    }


    public OperationCreateDTO create(final OperationCreateDTO operation) {
        if (operation.getCreationDate() == null) {
            operation.setCreationDate(ZonedDateTime.now());
        }
        OperationCreateDTO result = operationsConverter.convertToCreateDTO(
                operationRepository.save(operationsConverter.convertToEntityFromCreateDTO(operation)));
        accountBalanceService.updateBalance(operation.getAccountId());
        return result;
    }

    public List<FullOperationDTO> getAll(final long accountId) {
        return operationRepository
                .findAllByAccountOrderByCreationDateDesc(
                        accountRepository
                                .findById(accountId)
                                .orElseThrow(() -> new RuntimeException("Don't find account by id.")))
                .stream()
                .map(operationsConverter::convertToFullDTO)
                .collect(Collectors.toList());
    }

    public FullOperationDTO getFromId(final long operationId) {
        return operationsConverter.convertToFullDTO(operationRepository.findById(operationId)
                .orElseThrow(NoSuchElementException::new));
    }

    public void delete(final int operationId) {
        operationRepository.delete(operationsConverter.convertToEntityFromFullDTO(getFromId(operationId)));
        accountBalanceService.updateBalance(getFromId(operationId).getAccountId());
    }

    public OperationCreateDTO change(final OperationCreateDTO operation) {
        OperationCreateDTO result = operationsConverter.convertToCreateDTO(operationRepository.save(operationsConverter.convertToEntityFromCreateDTO(operation)));
        accountBalanceService.updateBalance(operation.getAccountId());
        return result;
    }
}
