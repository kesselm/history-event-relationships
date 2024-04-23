package de.kessel.events.dto;

import de.kessel.events.model.EventScope;
import de.kessel.events.model.EventStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

import static de.kessel.events.exception.ErrorConstants.*;

@Data
@Builder
@Validated
@AllArgsConstructor
public class EventMetadataRequestDto {

    private String personId;
    private String locationId;
    private EventScope scope;
    private EventStatus status;
    private String topicId;
    private String reference;
    @NotNull(message = VALIDATION_YEAR_MESSAGE)
    private Integer year;
    @Max(value = 12, message = VALIDATION_MAX_MESSAGE)
    @Min(value = 0, message = VALIDATION_MIN_MESSAGE)
    private Integer month;
    @Max(value = 31, message = VALIDATION_MAX_MESSAGE)
    @Min(value = 0, message = VALIDATION_MIN_MESSAGE)
    private Integer day;
    @NotBlank(message = VALIDATION_TRANSLATION_ID_MESSAGE)
    private String eventId;
    private String alternativeDateString;
    private String comment;

    public String validate() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<EventMetadataRequestDto>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<EventMetadataRequestDto> violation : violations) {
                sb.append(violation.getMessage());
            }
            return sb.toString();
        }
        return StringUtils.EMPTY;
    }
}

