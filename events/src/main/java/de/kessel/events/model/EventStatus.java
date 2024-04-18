package de.kessel.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {

    IN_PROGRESS("in_progress"),
    UNDER_REVIEW("under_review"),
    IN_CORRECTION("in_correction"),
    WAITING_FOR_APPROVAL("waiting_for_approval"),
    IN_PRODUCTION("in_production");

    private final String value;
}
