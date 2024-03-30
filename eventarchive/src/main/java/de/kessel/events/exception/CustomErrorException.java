package de.kessel.events.exception;

import de.kessel.events.model.CustomErrorResponse;

public class CustomErrorException extends BaseException {

    public CustomErrorException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
