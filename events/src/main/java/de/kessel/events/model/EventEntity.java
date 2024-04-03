package de.kessel.events.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "event")
@Schema(description = "Details about the Event")
public class EventEntity {

    @Id
    @Schema(description = "Unique identifier of the Event", example = "123456789")
    private String id;
    @NotEmpty
    @Schema(description = "Text of the Event", example = "Hallo")
    private String text;
    @Schema(description = "Translations of the Event", example = "{\"en\": \"hello\", \"fr\": \"bonjour\"}")
    private List<Translation> translations;
}
