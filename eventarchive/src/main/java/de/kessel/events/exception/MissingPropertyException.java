package de.kessel.events.exception;

import de.kessel.events.model.CustomErrorResponse;

public class MissingPropertyException extends BaseException {

    public MissingPropertyException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
