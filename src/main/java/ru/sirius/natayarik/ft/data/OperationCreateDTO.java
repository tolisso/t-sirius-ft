package ru.sirius.natayarik.ft.data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
