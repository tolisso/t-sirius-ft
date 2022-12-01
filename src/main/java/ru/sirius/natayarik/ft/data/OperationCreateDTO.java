package ru.sirius.natayarik.ft.data;

/**
 * @author Egor Malko
 */

public class OperationCreateDTO extends BaseOperationDTO {
    private long categoryId;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
