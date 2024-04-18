package de.kessel.events.exception;

public class PropertyValidationException extends BaseException {

    public PropertyValidationException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
