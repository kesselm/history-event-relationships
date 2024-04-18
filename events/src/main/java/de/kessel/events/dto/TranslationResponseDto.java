package de.kessel.events.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TranslationResponseDto extends BaseDto {
    private String id;
    @Schema(description = "Language of translation.", example = "de")
    private String language;
    @Schema(description = "Translated text.", example = "Hallo")
    private String text;
}
