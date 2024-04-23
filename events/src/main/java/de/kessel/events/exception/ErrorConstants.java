package de.kessel.events.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorConstants {

    public static final String TRANSLATION_NOT_FOUND_MESSAGE = "Translation not found with ID: %s";
    public static final String REQUEST_MISSING_PROPERTY_MESSAGE = "Text property is missing.";
    public static final String REQUEST_NO_EVENT_CREATED_MESSAGE = "Event could not be created.";
    public static final String REQUEST_INTERNAL_ERROR_MESSAGE = "Something went wrong";
    public static final String REQUEST_BODY_IS_MISSING_MESSAGE =  "Request Body is missing.";
    public static final String EVENT_COULD_NOT_BE_UPDATED_MESSAGE =  "Event could not be updated, because event could not be found.";
    public static final String EVENT_METADATA_NOT_FOUND_MESSAGE =  "EventMetadata not found with ID: %s";
    public static final String VALIDATION_YEAR_MESSAGE =  "Year must has a value.";
    public static final String VALIDATION_MAX_MESSAGE = "The value should be smaller then 32";
    public static final String VALIDATION_MIN_MESSAGE = "The value should be greater then -1";
    public static final String VALIDATION_TRANSLATION_ID_MESSAGE = "The EventMetadata Object should be related to an Translation Object.";
    public static final String VALIDATION_LANGUAGE_MESSAGE = "Translation needs the language code";
    public static final String EVENT_NOT_FOUND_MESSAGE = "Event not found with ID: %s";
}
