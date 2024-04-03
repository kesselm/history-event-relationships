package de.kessel.launcher.endpoints.handler;

import de.kessel.launcher.endpoints.handler.doc.EventHandler;
import de.kessel.events.service.EventService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventHandlerImpl implements EventHandler {

    private final EventService eventService;
    private final Validator validator;

    @Override
    public Mono<ServerResponse> createEvent(ServerRequest serverRequest) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public Mono<ServerResponse> getEventById(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> deleteEventById(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> getAllEvents(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public Mono<ServerResponse> updateEventById(ServerRequest serverRequest) {
        return null;
    }

//    public Mono<ServerResponse> createEvent(ServerRequest serverRequest) {
//        return serverRequest
//                .bodyToMono(EventRequestDto.class)
//                .doOnNext(this::validate)
//                .map(EventConverter::convertToEventEntity)
//                .flatMap(eventService::createEvent)
//                .map(EventConverter::convertToResponseDto)
//                .flatMap(eventResponseDto -> ServerResponse.status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(eventResponseDto));
//    }

//    public Mono<ServerResponse> getAllEvents(ServerRequest serverRequest) {
//        return eventService.getAllEvents()
//                .map(EventConverter::convertToResponseDto)
//                .collectList()
//                .flatMap(data -> {
//                    if (data.isEmpty()) {
//                        return ServerResponse.noContent().build();
//                    } else {
//                        return ServerResponse.status(HttpStatus.OK)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .bodyValue(data);
//                    }
//                });
//    }

//    public Mono<ServerResponse> getEventById(ServerRequest request) {
//        String id = request.pathVariable("id");
//        return eventService.getEventById(id)
//                .flatMap(data ->
//                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                                .bodyValue(data));
//    }

//    public Mono<ServerResponse> updateEventById(ServerRequest serverRequest) {
//        Long contentLength = serverRequest.headers().asHttpHeaders().getContentLength();
//        String id = serverRequest.pathVariable("id");
//
//        if (contentLength <= 0) {
//            CustomErrorResponse customErrorResponse = EventUtil.createCustomErrorResponse(HttpStatus.BAD_REQUEST,
//                    ErrorDetail.REQUEST_BODY_IS_MISSING);
//            String errorMessage = new StringBuilder(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorCode())
//                    .append(" - ")
//                    .append(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorMessage())
//                    .toString();
//            log.error(errorMessage);
//            throw new MissingBodyException(errorMessage, customErrorResponse);
//        }

//
//        return serverRequest.bodyToMono(EventRequestDto.class)
//                .flatMap(data -> eventService.updateEvent(id, data))
//                .flatMap(data ->
//                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                                .bodyValue(data));
//    }

//    public Mono<ServerResponse> deleteEventById(ServerRequest request) {
//        String id = request.pathVariable("id");
//        return eventService.deleteEvent(id)
//                .flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(data));
//    }

//    private void validate(EventRequestDto eventRequestDto) {
//        Set<ConstraintViolation<EventRequestDto>> constraintViolations = validator.validate(eventRequestDto);
//
//        if (!constraintViolations.isEmpty()) {
//            String errorMessage2 = constraintViolations
//                    .stream()
//                    .map(ConstraintViolation::getMessage)
//                    .sorted()
//                    .collect(Collectors.joining(","));
//
//            CustomErrorResponse customErrorResponse = EventUtil.createCustomErrorResponse(HttpStatus.BAD_REQUEST,
//                    ErrorDetail.REQUEST_MISSING_PROPERTY);
//
//            String errorMessage = EventUtil.createErrorMessage(ErrorDetail.REQUEST_MISSING_PROPERTY);
//            log.error(errorMessage);
//
//            throw new MissingPropertyException(errorMessage2, customErrorResponse);
//        }
//    }

//    private Tuple2<String, CustomErrorResponse> createEventNotFoundExceptionTuple() {
//        CustomErrorResponse customErrorResponse = EventUtil.createCustomErrorResponse(HttpStatus.BAD_REQUEST,
//                ErrorDetail.EVENT_NOT_FOUND);
//        String errorMessage = EventUtil.createErrorMessage(ErrorDetail.EVENT_NOT_FOUND);
//        log.error(errorMessage);
//        return Tuple.of(errorMessage, customErrorResponse);
//    }
}