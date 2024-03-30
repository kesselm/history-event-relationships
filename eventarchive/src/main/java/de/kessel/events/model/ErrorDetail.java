package de.kessel.events.model;

import lombok.Getter;

public enum ErrorDetail {
    EVENT_NOT_FOUND("E-001", "Event not found with ID: %s"),
    REQUEST_MISSING_PROPERTY("E-002", "Text property is missing."),
    REQUEST_NO_EVENT_CREATED("E-003", "Event could not be created."),
    REQUEST_INTERNAL_ERROR("E-004", "Something went wrong"),
    REQUEST_BODY_IS_MISSING("E-005", "Request Body is missing.");

    @Getter
    private String errorCode;
    @Getter
    private String errorMessage;

    ErrorDetail(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}