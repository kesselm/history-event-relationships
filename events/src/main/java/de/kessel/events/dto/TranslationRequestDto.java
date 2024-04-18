package de.kessel.events.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class TranslationRequestDto {
    @Schema(description = "Text of the Event", example = "Hallo")
    @NotBlank(message = "")
    private String language;
    @Schema(description = "Text of the Event", example = "Hallo")
    private String text;

    public String validate() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<TranslationRequestDto>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<TranslationRequestDto> violation : violations) {
                sb.append(violation.getMessage());
            }
            return sb.toString();
        }
        return StringUtils.EMPTY;
    }
}
