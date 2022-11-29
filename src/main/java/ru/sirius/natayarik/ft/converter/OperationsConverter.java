package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.services.AccountService;

/**
 * @author Yaroslav Ilin
 */

@Component
public class OperationsConverter {
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public OperationsConverter(AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    public OperationCreateDTO convertToDTO(final OperationEntity operationEntity) {
        OperationCreateDTO result = new OperationCreateDTO();
        result.setAccountId(operationEntity.getAccount().getId());
        result.setAmount(operationEntity.getAmount());
        result.setCategoryId(operationEntity.getCategory().getId());
        //result.setType(operationEntity.getCategory().getType());
        result.setId(operationEntity.getId());
        result.setCreationDate(operationEntity.getCreationDate());
        return result;
    }

    //    TODO: Здесь отвратительная архитектура, надо переделать я думаю!!!!!!!!!!
    public OperationEntity convertToEntity(final OperationCreateDTO operationDTO) {
        OperationEntity result = new OperationEntity();
        result.setCreationDate(operationDTO.getCreationDate());
        result.setAccount(accountRepository.findById(operationDTO.getAccountId()).orElseThrow(() -> new RuntimeException("Not found account by ID")));
        result.setAmount(operationDTO.getAmount());
        result.setCategory(categoryRepository.findById(operationDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Not found category by ID")));
        result.setId(operationDTO.getId());
        return result;
    }
}
