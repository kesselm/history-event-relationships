package de.kessel.events.filter;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.exception.MissingPropertyException;
import de.kessel.events.exception.CustomErrorResponse;
import de.kessel.events.exception.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RequestHandlerFilterFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> handlerFunction) {
        return request.bodyToMono(EventRequestDto.class).flatMap(body -> {
            if (StringUtils.isBlank(body.getText())) {
                String errorMessage = ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorCode() +
                        " - " +
                        ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage();
                CustomErrorResponse customErrorResponse = CustomErrorResponse
                        .builder()
                        .traceId(UUID.randomUUID().toString())
                        .timestamp(OffsetDateTime.now().now())
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(List.of(ErrorDetail.REQUEST_MISSING_PROPERTY))
                        .build();
                log.error(errorMessage);
                new MissingPropertyException(errorMessage, customErrorResponse);
            }
            return handlerFunction.handle(request);
        });
    }
}