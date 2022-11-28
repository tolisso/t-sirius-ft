package ru.sirius.natayarik.ft.data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Egor Malko
 */

public class OperationDTO {

    private long id;
    private long accountId;
    private BigDecimal amount;
    private ZonedDateTime creationDate;
    private CategoryDTO category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public TypeDTO getType() {
        return category.getType();
    }

    public void setType(TypeDTO typeDTO) {
        category.setType(typeDTO);
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
