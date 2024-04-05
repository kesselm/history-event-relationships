package de.kessel.events.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection="event-translation")
public class TranslationEntity {
    private String id;
    private String language;
    private String text;
}
