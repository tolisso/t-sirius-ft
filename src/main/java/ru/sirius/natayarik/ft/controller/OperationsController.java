package ru.sirius.natayarik.ft.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
public class OperationsController {

    @PostMapping("/api/createOperation")
//    NOTE: change param type
    public void createOperation(@RequestParam String operation) {

    }

    @GetMapping("/api/getAllOperations")
    @ResponseBody
//    NOTE: change result type
    public List<String> getAllOperations() {
        return null;
    }

    @GetMapping("/api/getOperationFromId")
    @ResponseBody
//    NOTE: change result type
    public String getOperationFromId() {
        return null;
    }

    @DeleteMapping("/api/deleteOperation")
    public void deleteOperation() {

    }

    @PutMapping("/api/changeOperation")
//    NOTE: change param type
    public void changeOperation(@RequestParam String operation) {

    }



}
