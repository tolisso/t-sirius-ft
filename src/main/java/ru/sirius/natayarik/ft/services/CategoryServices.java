package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.Category;
import ru.sirius.natayarik.ft.data.Operation;
import ru.sirius.natayarik.ft.data.Type;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class CategoryServices {
    public Category create(Category category) {
        category.setId(424243);
        return category;
    }

    public List<Category> getAll(Type type) {
        return List.of(new Category(0, 0, "Salary", Type.INCOME),
                new Category(1, 228, "Gift", Type.OUTCOME),
                new Category(2, 123, "Medicine", Type.OUTCOME));
    }

    public Category getFromId(int categoryId) {
        return new Category(categoryId, 324124, "KEKLOL", Type.INCOME);
    }

    public void delete(int categoryId) {

    }

    public Category change(Category category) {
        return category;
    }
}
