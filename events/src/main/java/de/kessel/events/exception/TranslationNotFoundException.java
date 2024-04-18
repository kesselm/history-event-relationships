package de.kessel.events.exception;

public class TranslationNotFoundException extends BaseException {
    public TranslationNotFoundException(String message, CustomErrorResponse errorResponse) {
        super(message, errorResponse);
    }
}
