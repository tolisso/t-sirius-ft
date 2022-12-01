package ru.sirius.natayarik.ft.exception;

/**
 * @author Yaroslav Ilin
 */

public class NotFoundDataException extends RuntimeException {
    public NotFoundDataException(String message) {
        super(message);
    }
}
