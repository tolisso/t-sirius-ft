package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.converter.OperationsConverter;
import ru.sirius.natayarik.ft.data.OperationDTO;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.services.AccountService;
import ru.sirius.natayarik.ft.services.CategoryService;
import ru.sirius.natayarik.ft.services.OperationService;
import ru.sirius.natayarik.ft.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

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
    public OperationDTO createOperation(@RequestBody OperationDTO operation) {
        return operationService.create(operation);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<OperationDTO> getAllOperations(@RequestParam long accountId) {
        return operationService.getAll(accountId);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public OperationDTO getOperationFromId(@RequestParam int operationId) {
        return operationService.getFromId(operationId);
    }

    @DeleteMapping("/delete")
    public void deleteOperation(@RequestParam int operationId) {
        operationService.delete(operationId);
    }

    @PutMapping("/change")
    public OperationDTO changeOperation(@RequestBody OperationDTO operation) {
        return operationService.change(operation);
    }
}
