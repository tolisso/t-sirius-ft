package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.services.AccountService;
import ru.sirius.natayarik.ft.services.UserService;

/**
 * @author Yaroslav Ilin
 */

@Component
public class OperationsConverter {
    private final AccountService accountService;
    private final UserService userService;
    private final CategoryConverter categoryConverter;

    public OperationsConverter(AccountService accountService, UserService userService, CategoryConverter categoryConverter) {
        this.accountService = accountService;
        this.userService = userService;
        this.categoryConverter = categoryConverter;
    }

    public OperationDTO convertToDTO(final OperationEntity operationEntity) {
        OperationDTO result = new OperationDTO();
        result.setAccountId(operationEntity.getAccount().getId());
        result.setAmount(operationEntity.getAmount());
        result.setCategory(categoryConverter.convertToDTO(operationEntity.getCategory()));
        //result.setType(operationEntity.getCategory().getType());
        result.setId(operationEntity.getId());
        result.setCreationDate(operationEntity.getCreationDate());
        return result;
    }

//    TODO: Здесь отвратительная архитектура, надо переделать я думаю!!!!!!!!!!
    public OperationEntity convertToEntity(final OperationDTO operationDTO) {
        OperationEntity result = new OperationEntity();
        result.setCreationDate(operationDTO.getCreationDate());
        result.setAccount(accountService.getAccountById(operationDTO.getAccountId()));
        result.setAmount(operationDTO.getAmount());
        result.setCategory(categoryConverter.convertToEntity(operationDTO.getCategory()));
        result.setId(operationDTO.getId());
        return result;
    }
}
