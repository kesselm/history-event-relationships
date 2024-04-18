package de.kessel.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventScope {

    COMMUNE("commune"),
    DISTRICT("district"),
    COUNTRY("country"),
    CONTINENT("continent");

    private final String value;

}
