package de.kessel.events.exception;

public class MissingPropertyException extends BaseException {

    public MissingPropertyException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
