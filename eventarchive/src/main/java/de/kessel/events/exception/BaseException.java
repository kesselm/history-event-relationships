package de.kessel.events.exception;

import de.kessel.events.model.CustomErrorResponse;
import lombok.Getter;

import java.io.Serializable;

public class BaseException extends  RuntimeException implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    @Getter
    private final CustomErrorResponse errorResponse;

    public BaseException(String message, CustomErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
