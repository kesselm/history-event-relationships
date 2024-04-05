package de.kessel.events.exception;

public class CustomErrorException extends BaseException {

    public CustomErrorException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
