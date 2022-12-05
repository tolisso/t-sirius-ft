package ru.sirius.natayarik.ft.data;

/**
 * @author Egor Malko
 */

public enum Type {
    INCOME("доход"),
    OUTCOME("расход");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
