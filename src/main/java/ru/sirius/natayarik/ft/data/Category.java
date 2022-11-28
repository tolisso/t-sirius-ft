package ru.sirius.natayarik.ft.data;

/**
 * @author Yaroslav Ilin
 */

public class Category {

    private int id;
    private int userId;
    private String name;
    private Type type;

    public Category() {
    }

    public Category(int id, int userId, String name, Type type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
