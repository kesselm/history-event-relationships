package de.kessel.events.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "event")
@Schema(description = "Details about the Event")
public class EventEntity {

    @Id
    @Builder.Default
    private String id = "event_" + UUID.randomUUID().toString();
    private List<String> translationIds;
}
