package de.kessel.events.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventPerson {
    private String name;
    private String personId;
}
