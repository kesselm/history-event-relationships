package de.kessel.events.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequestDto {
    private String language;
    private String text;
}
