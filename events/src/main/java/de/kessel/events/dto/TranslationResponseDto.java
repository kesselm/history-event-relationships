package de.kessel.events.dto;

import lombok.Data;

@Data
public class TranslationResponseDto extends BaseDto {
    private String id;
    private String language;
    private String text;
}
