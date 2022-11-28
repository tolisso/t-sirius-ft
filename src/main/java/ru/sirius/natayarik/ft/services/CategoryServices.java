package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class CategoryServices {
    public CategoryDTO create(CategoryDTO category) {
        category.setId(424243);
        return category;
    }

    public List<CategoryDTO> getAll(TypeDTO typeDTO) {
        return List.of(new CategoryDTO(0, 0, "Salary", TypeDTO.INCOME),
                new CategoryDTO(1, 228, "Gift", TypeDTO.OUTCOME),
                new CategoryDTO(2, 123, "Medicine", TypeDTO.OUTCOME));
    }

    public CategoryDTO getFromId(int categoryId) {
        return new CategoryDTO(categoryId, 324124, "KEKLOL", TypeDTO.INCOME);
    }

    public void delete(int categoryId) {

    }

    public CategoryDTO change(CategoryDTO category) {
        return category;
    }
}
