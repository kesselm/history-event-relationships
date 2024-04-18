package de.kessel.events.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseDto {
    @Schema(description = "Unique identifier.", example = "123456789")
    private String id;
}
