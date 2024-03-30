package de.kessel.events.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Document(collection = "event_metadata")
public class EventMetadataEntity {

    @Id
    private String id;
    private String place;
    private String city;
    private String country;
    private String epoche;
    private String epocheId;
    private String scope;
    private String topic;
    private String topicId;
    private String reference;
    private LocalDate date;
    private List<EventPerson> persons;

    @DBRef
    private EventEntity eventEntity;
}
