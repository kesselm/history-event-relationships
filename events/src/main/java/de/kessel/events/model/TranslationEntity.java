package de.kessel.events.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Data
@Document(collection="translation")
public class TranslationEntity {

    @Id
    @Builder.Default
    private String id = "translation_" + UUID.randomUUID().toString();
    private String language;
    private String text;
}
