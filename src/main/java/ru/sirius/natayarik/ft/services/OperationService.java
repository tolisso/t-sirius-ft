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

    public OperationService(OperationRepository operationRepository, AccountRepository accountRepository, OperationsConverter operationsConverter) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.operationsConverter = operationsConverter;
    }


    public OperationCreateDTO create(final OperationCreateDTO operation) {
        if (operation.getCreationDate() == null) {
            operation.setCreationDate(ZonedDateTime.now());
        }
        return operationsConverter.convertToCreateDTO(
                operationRepository.save(operationsConverter.convertToEntityFromCreateDTO(operation)));
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
    }

    public OperationCreateDTO change(final OperationCreateDTO operation) {
        return operationsConverter.convertToCreateDTO(operationRepository.save(operationsConverter.convertToEntityFromCreateDTO(operation)));
    }
}
