package de.kessel.events.exception;

import lombok.Getter;

import java.io.Serializable;

public class BaseException extends  RuntimeException implements Serializable {

    @Getter
    private final CustomErrorResponse errorResponse;

    public BaseException(String message, CustomErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
