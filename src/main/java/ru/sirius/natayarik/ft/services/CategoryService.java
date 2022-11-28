package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class CategoryService {
    public CategoryDTO create(CategoryDTO category) {
        category.setId(424243);
        return category;
    }

    public List<CategoryDTO> getAll(TypeDTO typeDTO) {
        return null;
    }

    public CategoryDTO getFromId(int categoryId) {
        return null;
    }

    public void delete(int categoryId) {

    }

    public CategoryDTO change(CategoryDTO category) {
        return category;
    }
}
