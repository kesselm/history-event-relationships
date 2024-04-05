package de.kessel.events.exception;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ApplicationContext applicationContext,
                                  ServerCodecConfigurer configurer) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageWriters(configurer.getWriters());
        this.setMessageReaders(configurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResposne);
    }

    private Mono<ServerResponse> renderErrorResposne(ServerRequest request) {
        Map<String, Object> errorProperties = getErrorAttributes(request);
        return ServerResponse.status((HttpStatusCode) errorProperties.get("status")).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorProperties));
    }

    public Map<String, Object> getErrorAttributes(ServerRequest request) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Throwable error = getError(request);

        Tuple3<String, String, HttpStatus> message = switch (error) {
            case MissingPropertyException customException ->
                    Tuple.of(ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorCode(),
                            ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage(),
                            ((MissingPropertyException) error).getErrorResponse().getStatus());
            case EventNotFoundException eventNotFoundException ->
                    Tuple.of(ErrorDetail.EVENT_NOT_FOUND.getErrorCode(),
                            String.format(ErrorDetail.EVENT_NOT_FOUND.getErrorMessage(), request.pathVariable("id")),
                            ((EventNotFoundException) error).getErrorResponse().getStatus());
            case MissingBodyException missingBodyException ->
                    Tuple.of(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorCode(),
                            ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorMessage(),
                            ((MissingBodyException)error).getErrorResponse().getStatus());
            default -> Tuple.of("n/a", "n/a",HttpStatus.INTERNAL_SERVER_ERROR);
        };

        errorAttributes.put("path", request.path());
        errorAttributes.put("code", message._1);
        errorAttributes.put("message", message._2);
        errorAttributes.put("status", message._3);
        errorAttributes.put("error", error.getMessage());
        return errorAttributes;
    }


}
