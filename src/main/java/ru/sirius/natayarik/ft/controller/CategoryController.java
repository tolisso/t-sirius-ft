package ru.sirius.natayarik.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.services.CategoryService;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/create")
    public CategoryDTO createCategory(@RequestBody CategoryDTO category) {
        return categoryService.create(category);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<CategoryDTO> getAllCategories(@RequestParam TypeDTO typeDTO) {
        return categoryService.getAll(typeDTO);
    }

    @GetMapping("/getFromId")
    @ResponseBody
    public CategoryDTO getCategoryFromId(@RequestParam int categoryId) {
        return categoryService.getFromId(categoryId);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam int categoryId) {
        categoryService.delete(categoryId);
    }

    @PutMapping("/change")
    public CategoryDTO changeCategory(@RequestBody CategoryDTO category) {
        return categoryService.change(category);
    }
}
