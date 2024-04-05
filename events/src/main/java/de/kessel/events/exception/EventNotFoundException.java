package de.kessel.events.exception;

public class EventNotFoundException extends BaseException {
    public EventNotFoundException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
