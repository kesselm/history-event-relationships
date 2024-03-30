package de.kessel.events.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventConstants {

    public static final String BASE_API = "/api";
    public static final String VERSION = "/v1";

    public static final String EVENTS = "/events";

    public static final String V1_API = BASE_API + VERSION;

    public static final String EVENTS_API = V1_API + EVENTS;
}
