package de.kessel.events.dto;

import de.kessel.events.model.Translation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class EventResponseDto extends BaseDto {

    @Schema(description = "Text of the Event", example = "Hallo")
    private String text;
    @Schema(description = "Translations of the Event", example = "{\"en\": \"hello\", \"fr\": \"bonjour\"}")
    private List<Translation> translations;
}
