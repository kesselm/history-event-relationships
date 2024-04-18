package de.kessel.events.util;

import de.kessel.events.exception.CustomErrorResponse;
import de.kessel.events.exception.ErrorDetail;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@UtilityClass
@Slf4j
public class ErrorMessageUtil {

    public static String getErrorMessage(String id, ErrorDetail errorDetail) {
        String errorMessage = new StringBuilder(errorDetail.getErrorCode())
                .append(" - ").append(String.format(errorDetail.getErrorMessage(), id)).toString();
        log.info(errorMessage);
        return errorMessage;
    }

    public static CustomErrorResponse getCustomErrorResponse(HttpStatus httpStatus, ErrorDetail errorDetail) {
        return CustomErrorResponse
                .builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().now())
                .status(httpStatus)
                .errors(List.of(errorDetail))
                .build();
    }
}
