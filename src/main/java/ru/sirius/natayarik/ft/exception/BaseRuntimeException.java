package ru.sirius.natayarik.ft.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Yaroslav Ilin
 */

public abstract class BaseRuntimeException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public BaseRuntimeException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
