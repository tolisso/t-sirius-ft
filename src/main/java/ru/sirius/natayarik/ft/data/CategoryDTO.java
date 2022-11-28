package ru.sirius.natayarik.ft.data;

/**
 * @author Yaroslav Ilin
 */

public class CategoryDTO {

    private int id;
    private int userId;
    private String name;
    private TypeDTO typeDTO;

    public CategoryDTO() {
    }

    public CategoryDTO(int id, int userId, String name, TypeDTO typeDTO) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.typeDTO = typeDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
