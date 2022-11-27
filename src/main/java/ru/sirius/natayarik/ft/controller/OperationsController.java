package ru.sirius.natayarik.ft.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/operation/")
public class OperationsController {

    @PostMapping("/create")
//    NOTE: change param type
    public void createOperation(@RequestParam String operation) {

    }

    @GetMapping("/getAll")
    @ResponseBody
//    NOTE: change result type
    public List<String> getAllOperations() {
        return null;
    }

    @GetMapping("/getFromId")
    @ResponseBody
//    NOTE: change result type
    public String getOperationFromId() {
        return null;
    }

    @DeleteMapping("/delete")
    public void deleteOperation() {

    }

    @PutMapping("/change")
//    NOTE: change param type
    public void changeOperation(@RequestParam String operation) {

    }


}
