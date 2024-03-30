package de.kessel.events.exception;

import de.kessel.events.model.CustomErrorResponse;

public class MissingBodyException extends BaseException {
    public MissingBodyException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
