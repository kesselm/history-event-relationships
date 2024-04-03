package de.kessel.events.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
    private String traceId;
    private OffsetDateTime timestamp;
    private HttpStatus status;
    private List<ErrorDetail> errors;
}
