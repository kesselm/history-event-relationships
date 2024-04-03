package de.kessel.events.dto;

import de.kessel.events.model.ErrorDetail;
import de.kessel.events.model.Translation;
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

    private final String error1 = ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorCode();

    @NotBlank(message ="Text should not be empty.")
    @Schema(description = "Text of the Event", example = "Hallo")
    String text;
    @Schema(description = "Translations of the Event", example = "{\"en\": \"hello\", \"fr\": \"bonjour\"}")
    List<Translation> translations;
}
