package de.kessel.events.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class EventRequestDto {

    @NotBlank(message = "Text should not be empty.")
    @Schema(description = "Text of the Event", example = "Hallo")
    String text;
    @Schema(description = "Translations of the Event", example = "{\"en\": \"hello\", \"fr\": \"bonjour\"}")
    List<TranslationRequestDto> translations;
}
