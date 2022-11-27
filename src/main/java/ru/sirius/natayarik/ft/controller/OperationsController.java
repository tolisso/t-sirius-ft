package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.Operation;
import ru.sirius.natayarik.ft.services.OperationServices;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/operation/")
public class OperationsController {
    private final OperationServices operationServices;

    @Autowired
    public OperationsController(OperationServices operationServices) {
        this.operationServices = operationServices;
    }


    @PostMapping("/create")
    public Operation createOperation(@RequestParam Operation operation) {
        return operationServices.create(operation);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Operation> getAllOperations(@RequestParam int accountId) {
        return operationServices.getAll(accountId);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public Operation getOperationFromId(@RequestParam int operationId) {
        return operationServices.getFromId(operationId);
    }

    @DeleteMapping("/delete")
    public void deleteOperation(@RequestParam int operationId) {
        operationServices.delete(operationId); // TODO
    }

    @PutMapping("/change")
    public Operation changeOperation(@RequestParam Operation operation) {
        return operationServices.change(operation);
    }
}
