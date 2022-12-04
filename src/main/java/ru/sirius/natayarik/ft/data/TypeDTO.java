package ru.sirius.natayarik.ft.data;

/**
 * @author Egor Malko
 */

public enum TypeDTO {
    INCOME("доход"),
    OUTCOME("расход");

    private final String label;

    TypeDTO(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
