package ru.sirius.natayarik.ft.data;

/**
 * @author Yaroslav Ilin
 */

public class CategoryDTO {

    private long id;
    private long userId;
    private String name;
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
