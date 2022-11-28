package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.Category;
import ru.sirius.natayarik.ft.data.Operation;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.services.CategoryServices;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
    private final CategoryServices categoryServices;

    @Autowired
    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }


    @PostMapping("/create")
    public Category createCategory(@RequestParam Category category) {
        return categoryServices.create(category);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Category> getAllCategories(@RequestParam Type type) {
        return categoryServices.getAll(type);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public Category getCategoryFromId(@RequestParam int categoryId) {
        return categoryServices.getFromId(categoryId);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam int categoryId) {
        categoryServices.delete(categoryId);
    }

    @PutMapping("/change")
    public Category changeCategory(@RequestParam Category category) {
        return categoryServices.change(category);
    }
}
