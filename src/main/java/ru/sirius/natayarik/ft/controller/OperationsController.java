package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.OperationDTO;
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
    public OperationDTO createOperation(@RequestParam OperationDTO operation) {
        return operationServices.create(operation);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<OperationDTO> getAllOperations(@RequestParam int accountId) {
        return operationServices.getAll(accountId);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public OperationDTO getOperationFromId(@RequestParam int operationId) {
        return operationServices.getFromId(operationId);
    }

    @DeleteMapping("/delete")
    public void deleteOperation(@RequestParam int operationId) {
        operationServices.delete(operationId);
    }

    @PutMapping("/change")
    public OperationDTO changeOperation(@RequestParam OperationDTO operation) {
        return operationServices.change(operation);
    }
}
