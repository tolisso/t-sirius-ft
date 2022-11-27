package ru.sirius.natayarik.ft.controller;

import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.Operation;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/operation/")
public class OperationsController {

    @PostMapping("/create")
    public void createOperation(@RequestParam Operation operation) {

    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Operation> getAllOperations(@RequestParam int accountId) {
        return null;
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public Operation getOperationFromId(@RequestParam int operationId) {
        return null;
    }

    @DeleteMapping("/delete")
    public void deleteOperation(@RequestParam int operationId) {

    }

    @PutMapping("/change")
    public void changeOperation(@RequestParam Operation operation) {

    }


}
