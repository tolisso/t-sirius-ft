package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.services.OperationService;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/operation/")
public class OperationsController {
    private final OperationService operationService;

    @Autowired
    public OperationsController(OperationService operationService) {
        this.operationService = operationService;
    }


    @PostMapping("/create")
    public OperationCreateDTO createOperation(@RequestBody OperationCreateDTO operation) {
        return operationService.create(operation);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<OperationCreateDTO> getAllOperations(@RequestParam long accountId) {
        return operationService.getAll(accountId);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public OperationCreateDTO getOperationFromId(@RequestParam int operationId) {
        return operationService.getFromId(operationId);
    }

    @DeleteMapping("/delete")
    public void deleteOperation(@RequestParam int operationId) {
        operationService.delete(operationId);
    }

    @PutMapping("/change")
    public OperationCreateDTO changeOperation(@RequestBody OperationCreateDTO operation) {
        return operationService.change(operation);
    }
}
