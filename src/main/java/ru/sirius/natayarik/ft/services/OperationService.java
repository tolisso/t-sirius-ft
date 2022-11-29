package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.OperationsConverter;
import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
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
    private final AccountService accountService;
    private final OperationsConverter operationsConverter;

    public OperationService(OperationRepository operationRepository, AccountService accountService, OperationsConverter operationsConverter) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.operationsConverter = operationsConverter;
    }


    public OperationDTO create(final OperationDTO operation) {
        if (operation.getCreationDate() == null) {
            operation.setCreationDate(ZonedDateTime.now());
        }
        return operationsConverter.convertToDTO(
                operationRepository.save(operationsConverter.convertToEntity(operation)));
    }

    public List<OperationDTO> getAll(final long accountId) {
        return operationRepository.findAllByAccountOrderByCreationDateDesc(accountService.getAccountById(accountId))
                .stream()
                .map(operationsConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public OperationDTO getFromId(final long operationId) {
        return operationsConverter.convertToDTO(operationRepository.findById(operationId)
                .orElseThrow(NoSuchElementException::new));
    }

    public void delete(final int operationId) {
        operationRepository.delete(operationsConverter.convertToEntity(getFromId(operationId)));
    }

    public OperationDTO change(final OperationDTO operation) {
        return operationsConverter.convertToDTO(operationRepository.save(operationsConverter.convertToEntity(operation)));
    }
}
