package ru.sirius.natayarik.ft.data;

/**
 * @author Yaroslav Ilin
 */

public class FullOperationDTO extends BaseOperationDTO{
    private CategoryDTO categoryDTO;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
}
