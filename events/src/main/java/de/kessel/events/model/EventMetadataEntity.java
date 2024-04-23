package de.kessel.events.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document(collection = "event_metadata")
public class EventMetadataEntity {

    @Id
    @Schema(description = "Unique identifier of the Event Metadata", example = "eventMetadata-123456789")
    @Builder.Default
    private String id = "eventMetadata_" + UUID.randomUUID().toString();

    private String personId;
    private String locationId;
    private EventScope scope;
    private EventStatus status;
    private String topicId;
    private String reference;
    private int year;
    private int month;
    private int day;
    private String alternativeDateString;
    private String eventId;
    private String comment;
}
