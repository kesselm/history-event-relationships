package de.kessel.events.exception;

import de.kessel.events.model.CustomErrorResponse;

public class EventNotFoundException extends BaseException {
    public EventNotFoundException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
