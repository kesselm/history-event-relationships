package de.kessel.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequestDto {
    @JsonProperty("language")
    private String language;
    @JsonProperty("text")
    private String text;
}
