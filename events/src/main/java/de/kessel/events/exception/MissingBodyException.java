package de.kessel.events.exception;

public class MissingBodyException extends BaseException {
    public MissingBodyException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
