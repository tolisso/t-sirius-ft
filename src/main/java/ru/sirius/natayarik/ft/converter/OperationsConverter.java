package ru.sirius.natayarik.ft.converter;

import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.services.AccountService;
import ru.sirius.natayarik.ft.services.UserService;

/**
 * @author Yaroslav Ilin
 */

public class OperationsConverter {
    public static OperationDTO convertToDTO(final OperationEntity operationEntity) {
        OperationDTO result = new OperationDTO();
        result.setAccountId(operationEntity.getAccount().getId());
        result.setAmount(operationEntity.getAmount());
        result.setCategory(CategoryConverter.convertToDTO(operationEntity.getCategory()));
        result.setType(operationEntity.getCategory().getType());
        result.setId(operationEntity.getId());
        result.setCreationDate(operationEntity.getCreationDate());
        return result;
    }

//    TODO: Здесь отвратительная архитектура, надо переделать я думаю!!!!!!!!!!
    public static OperationEntity convertToEntity(final OperationDTO operationDTO, final AccountService accountService,
                                                  final UserService userService) {
        OperationEntity result = new OperationEntity();
        result.setCreationDate(operationDTO.getCreationDate());
        result.setAccount(accountService.getAccountById(operationDTO.getAccountId()));
        result.setAmount(operationDTO.getAmount());
        result.setCategory(CategoryConverter.convertToEntity(operationDTO.getCategory(), userService));
        result.setId(operationDTO.getId());
        return result;
    }
}
