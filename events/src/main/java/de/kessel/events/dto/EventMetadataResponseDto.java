package de.kessel.events.dto;

import de.kessel.events.model.EventScope;
import de.kessel.events.model.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class EventMetadataResponseDto extends BaseDto {
    private String personId;
    private String locationId;
    private EventScope scope;
    private EventStatus status;
    private String topicId;
    private String reference;
    private String date;
    @Schema(description = "The value must be registered and really exist", example = "e1")
    private String eventId;
    private String alternativeDateFormat;
    private String comment;
}

