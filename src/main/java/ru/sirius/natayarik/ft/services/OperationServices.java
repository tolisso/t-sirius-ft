package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class OperationServices {

    public OperationDTO create(final OperationDTO operation) {
        operation.setId(42);
        return operation;
    }

    public List<OperationDTO> getAll(final int accountId) {
        OperationDTO op1 = new OperationDTO();
        op1.setId(0);
        op1.setAccountId(accountId);
        op1.setAmount(BigDecimal.valueOf(42));
        op1.setCategory(new CategoryDTO(0, 0, "Salary", TypeDTO.INCOME));
        op1.setType(TypeDTO.INCOME);
        op1.setCreationDate(ZonedDateTime.now());
        OperationDTO op2 = new OperationDTO();
        op2.setId(1);
        op2.setAccountId(accountId);
        op2.setAmount(BigDecimal.valueOf(2281337));
        op2.setCategory(new CategoryDTO(1, 228, "Gift", TypeDTO.OUTCOME));
        op2.setType(TypeDTO.OUTCOME);
        op2.setCreationDate(ZonedDateTime.now());
        return List.of(op1, op2);
    }

    public OperationDTO getFromId(final int operationId) {
        OperationDTO result = new OperationDTO();
        result.setId(operationId);
        result.setType(TypeDTO.OUTCOME);
        result.setCategory(new CategoryDTO(2, 123, "Medicine", TypeDTO.OUTCOME));
        result.setCreationDate(ZonedDateTime.now());
        result.setAccountId(42);
        result.setAmount(BigDecimal.valueOf(123456789));
        return result;
    }

    public void delete(final int operationId) {

    }

    public OperationDTO change(final OperationDTO operation) {
        return operation;
    }
}
