package ru.sirius.natayarik.ft.cache;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.repository.OperationRepository;
import ru.sirius.natayarik.ft.services.OperationService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Egor Malko
 */
@Component
public class OperationCash {
    private final Map<String, OperationCreateDTO> operationCash = new HashMap<>();
    private final OperationService operationService;

    public OperationCash(OperationService operationService) {
        this.operationService = operationService;
    }

    public void createOperation(String userId) {
        operationCash.put(userId, new OperationCreateDTO());
    }
    public void addAmount(String userId, BigDecimal amount) {
        operationCash.get(userId).setAmount(amount);
    }

    public void addCategory(String userId, long categoryId) {
        operationCash.get(userId).setCategoryId(categoryId);
    }

    public void addAccount(String userId, long accountId) {
        operationCash.get(userId).setAccountId(accountId);
    }

    public OperationCreateDTO saveOperation(String userId) {
        return operationService.create(operationCash.get(userId));
    }
}
