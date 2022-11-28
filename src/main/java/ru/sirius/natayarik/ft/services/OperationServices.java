package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.Category;
import ru.sirius.natayarik.ft.data.Operation;
import ru.sirius.natayarik.ft.data.Type;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class OperationServices {

    public Operation create(final Operation operation) {
        operation.setId(42);
        return operation;
    }

    public List<Operation> getAll(final int accountId) {
        Operation op1 = new Operation();
        op1.setId(0);
        op1.setAccountId(accountId);
        op1.setAmount(BigDecimal.valueOf(42));
        op1.setCategory(new Category(0, 0, "Salary", Type.INCOME));
        //op1.setType(Type.INCOME);
        op1.setCreationDate(ZonedDateTime.now());
        Operation op2 = new Operation();
        op2.setId(1);
        op2.setAccountId(accountId);
        op2.setAmount(BigDecimal.valueOf(2281337));
        op2.setCategory(new Category(1, 228, "Gift", Type.OUTCOME));
        //op2.setType(Type.OUTCOME);
        op2.setCreationDate(ZonedDateTime.now());
        return List.of(op1, op2);
    }

    public Operation getFromId(final int operationId) {
        Operation result = new Operation();
        result.setId(operationId);
        //result.setType(Type.OUTCOME);
        result.setCategory(new Category(2, 123, "Medicine", Type.OUTCOME));
        result.setCreationDate(ZonedDateTime.now());
        result.setAccountId(42);
        result.setAmount(BigDecimal.valueOf(123456789));
        return result;
    }

    public void delete(final int operationId) {

    }

    public Operation change(final Operation operation) {
        return operation;
    }
}
