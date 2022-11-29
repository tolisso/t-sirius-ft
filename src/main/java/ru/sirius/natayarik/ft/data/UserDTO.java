package ru.sirius.natayarik.ft.data;

/**
 * @author Egor Malko
 */

public class UserDTO extends BaseDTO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
