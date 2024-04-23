package de.kessel.launcher.tools;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventConstants {

    public static final String BASE_API = "/api";
    public static final String VERSION = "/v1";
    public static final String EVENTS = "/events";
    public static final String TRANSLATION = "/translation";
    public static final String V1_API = BASE_API + VERSION;
    public static final String EVENTS_API = V1_API + EVENTS;
    public static final String TRANSLATION_API = V1_API + TRANSLATION;
}
