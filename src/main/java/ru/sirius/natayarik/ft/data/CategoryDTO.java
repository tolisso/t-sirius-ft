package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yaroslav Ilin
 */

public class CategoryDTO extends BaseDTO {
    @Schema(description = "Id пользователя")
    private long userId;
    @NotBlank
    @Schema(description = "Имя пользователя")
    private String name;
    @NotNull
    @Schema(description = "Тип операции(Доход/Расход)")
    private TypeDTO typeDTO;

    public CategoryDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeDTO getType() {
        return typeDTO;
    }

    public void setType(TypeDTO typeDTO) {
        this.typeDTO = typeDTO;
    }
}
