package de.kessel.events.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Translation {

    private String language;
    private String text;
}
