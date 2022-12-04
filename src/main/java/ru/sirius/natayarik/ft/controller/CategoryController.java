package ru.sirius.natayarik.ft.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.services.CategoryService;
import ru.sirius.natayarik.ft.services.UserToAccountService;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserToAccountService userToAccountService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserToAccountService userToAccountService) {
        this.categoryService = categoryService;
        this.userToAccountService = userToAccountService;
    }


    @Operation(summary = "Метод для создания категории")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDTO createCategory(@RequestBody CategoryDTO category) {
        return categoryService.create(category);
    }

    @Operation(summary = "Метод для получения списка категорий пользователя по типу")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CategoryDTO> getAllCategories(@RequestParam TypeDTO typeDTO, @RequestParam Long accountId) {
        return accountId == 0
                ? categoryService.getAll(typeDTO)
                : userToAccountService.getAllCategoriesFromAccount(accountId, typeDTO);
    }


    @Operation(summary = "Метод для получения категории по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDTO getCategoryFromId(@PathVariable("id") long categoryId) {
        return categoryService.getFromId(categoryId);
    }

    @Operation(summary = "Метод для удаления категории по id")
    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable("id") long categoryId) {
        categoryService.delete(categoryId);
    }

    @Operation(summary = "Метод для изменения категории по id")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDTO changeCategory(@RequestBody CategoryDTO category) {
        return categoryService.change(category);
    }
}
