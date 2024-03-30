package de.kessel.events.util;

import de.kessel.events.model.CustomErrorResponse;
import de.kessel.events.model.ErrorDetail;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static java.time.OffsetDateTime.now;

@UtilityClass
public class EventUtil {

    public static String createErrorMessage(ErrorDetail errorDetail){
        return new StringBuilder(errorDetail.getErrorCode())
                .append(" - ").append(errorDetail.getErrorMessage()).toString();
    }

    public static CustomErrorResponse createCustomErrorResponse(HttpStatus status, ErrorDetail errorDetail){
        return CustomErrorResponse
                .builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(now())
                .status(status)
                .errors(List.of(errorDetail))
                .build();
    }

}
