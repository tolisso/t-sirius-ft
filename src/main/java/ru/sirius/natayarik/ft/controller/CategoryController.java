package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
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
    public CategoryDTO createCategory(@RequestParam CategoryDTO category) {
        return categoryServices.create(category);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<CategoryDTO> getAllCategories(@RequestParam TypeDTO typeDTO) {
        return categoryServices.getAll(typeDTO);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public CategoryDTO getCategoryFromId(@RequestParam int categoryId) {
        return categoryServices.getFromId(categoryId);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam int categoryId) {
        categoryServices.delete(categoryId);
    }

    @PutMapping("/change")
    public CategoryDTO changeCategory(@RequestParam CategoryDTO category) {
        return categoryServices.change(category);
    }
}
