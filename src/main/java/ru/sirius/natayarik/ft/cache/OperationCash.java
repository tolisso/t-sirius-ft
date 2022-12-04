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
    private final Map<Integer, OperationCreateDTO> operationCash = new HashMap<>();
    private final OperationService operationService;

    public OperationCash(OperationService operationService) {
        this.operationService = operationService;
    }

    public void createOperation(int userId) {
        operationCash.put(userId, new OperationCreateDTO());
    }
    public void addAmount(int userId, BigDecimal amount) {
        operationCash.get(userId).setAmount(amount);
    }

    public void addCategory(int userId, long categoryId) {
        operationCash.get(userId).setCategoryId(categoryId);
    }

    public void addAccount(int userId, long accountId) {
        operationCash.get(userId).setAccountId(accountId);
    }

    public OperationCreateDTO saveOperation(int userId) {
        return operationService.create(operationCash.get(userId));
    }
}
