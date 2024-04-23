//package de.kessel.launcher.endpoints.handler.implementation;
//
//import de.kessel.events.dto.EventRequestDto;
//import de.kessel.events.service.implementation.EventServiceImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class EventHandlerImpl  { //implements EventHandler {
//
//    private final EventServiceImpl eventService;
//
//    public Mono<ServerResponse> createEvent(ServerRequest serverRequest) {
//        return serverRequest
//                .bodyToMono(EventRequestDto.class)
//                .flatMap(eventService::createEvent)
//                .flatMap(eventResponseDto -> ServerResponse.status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .bodyValue(eventResponseDto));
//    }
//
////    public Mono<ServerResponse> findAllEvents(ServerRequest serverRequest) {
////        return eventService.findAllEvents()
////                .collectList()
////                .flatMap(data -> {
////                    if (data.isEmpty()) {
////                        return ServerResponse.noContent().build();
////                    } else {
////                        return ServerResponse.status(HttpStatus.OK)
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .bodyValue(data);
////                    }
////                });
////    }
////
////    @Override
////    public Mono<ServerResponse> updateEventById(ServerRequest serverRequest) {
////        return null;
////    }
////
////    @Override
////    public Mono<ServerResponse> selectSingleTranslation(ServerRequest serverRequest) {
////        return null;
////    }
//
////    public Mono<ServerResponse> findEventById(ServerRequest request) {
////        String id = request.pathVariable("id");
////        return eventService.findEventById(id)
////                .flatMap(data ->
////                        ok().contentType(MediaType.APPLICATION_JSON)
////                                .bodyValue(data));
////    }
////
////    @Override
////    public Mono<ServerResponse> deleteEventById(ServerRequest request) {
////        return null;
////    }
//
////    @Override
////    public Mono<ServerResponse> findAllEventsAndPaginate(ServerRequest serverRequest) {
////        Optional<Integer> page = serverRequest.queryParam("page").map(Integer::valueOf);
////        Optional<Integer> size = serverRequest.queryParam("size").map(Integer::valueOf);
////        Optional<String> sortAlg = serverRequest.queryParam("sortAlg");
////
////        int pageInt = 0;
////        int sizeInt = 10;
////        if (page.isPresent() && size.isPresent()) {
////            pageInt = page.get();
////            sizeInt = size.get();
////        }
////        Order order = new Order(createOrder(sortAlg), "language");
////        Pageable paging = PageRequest.of(pageInt, sizeInt, by(order));
////        Flux<EventResponseDto> pageEvents = eventService.
////        Map<String, Object> response = new HashMap<>();
////        response.put("currentPage", pageEvents.getNumber());
////        response.put("totalItems", pageEvents.getTotalElements());
////        response.put("totalPages", pageEvents.getTotalPages());
////        return ServerResponse.ok()
////                .header("X-APP-PAGINATION-ENABLED", "false")
////                .contentType(MediaType.APPLICATION_JSON).bodyValue(response);
////    }
//
////    public Mono<ServerResponse> updateEventById(ServerRequest serverRequest) {
////        Long contentLength = serverRequest.headers().asHttpHeaders().getContentLength();
////        String id = serverRequest.pathVariable("id");
////
////        if (contentLength <= 0) {
////            CustomErrorResponse customErrorResponse = ErrorResponseUtil.createCustomErrorResponse(HttpStatus.BAD_REQUEST,
////                    ErrorDetail.REQUEST_BODY_IS_MISSING);
////            String errorMessage = new StringBuilder(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorCode())
////                    .append(" - ")
////                    .append(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorMessage())
////                    .toString();
////            log.error(errorMessage);
////            throw new MissingBodyException(errorMessage, customErrorResponse);
////        }
////
////        return serverRequest.bodyToMono(EventRequestDto.class)
////                .flatMap(data -> eventService.updateEvent(id, data))
////                .flatMap(data ->
////                        ok().contentType(MediaType.APPLICATION_JSON)
////                                .bodyValue(data));
////    }
//
////    public Mono<ServerResponse> deleteEventById(ServerRequest request) {
////        String id = request.pathVariable("id");
////        return eventService.deleteEventById(id)
////                .flatMap(data -> ok().contentType(MediaType.APPLICATION_JSON)
////                        .bodyValue(data));
////    }
//
////    public Mono<ServerResponse> selectSingleTranslation(ServerRequest request) {
////        Optional<String> eventIdOptional = request.queryParam("eventId");
////        Optional<String> languageOptional = request.queryParam("language");
////
////        Mono<TranslationResponseDto> translationResponseDtoMono = Mono.empty();
////
////        if (eventIdOptional.isPresent() && languageOptional.isPresent()) {
////            translationResponseDtoMono = eventService.getSingleTranslation(eventIdOptional.get(),
////                    languageOptional.get());
////        }
////        return ok().contentType(MediaType.APPLICATION_JSON)
////                .body(translationResponseDtoMono, TranslationResponseDto.class);
////    }
//
////    private void validate(EventRequestDto eventRequestDto) {
////        Set<ConstraintViolation<EventRequestDto>> constraintViolations = validator.validate(eventRequestDto);
////
////        if (!constraintViolations.isEmpty()) {
////            String constraintMessage = constraintViolations
////                    .stream()
////                    .map(ConstraintViolation::getMessage)
////                    .sorted()
////                    .collect(Collectors.joining(","));
////
////            CustomErrorResponse customErrorResponse = ErrorResponseUtil.createCustomErrorResponse(HttpStatus.BAD_REQUEST,
////                    ErrorDetail.REQUEST_MISSING_PROPERTY);
////
////            String errorMessage = ErrorResponseUtil.createErrorMessage(ErrorDetail.REQUEST_MISSING_PROPERTY);
////            log.error(errorMessage);
////
////            throw new MissingPropertyException(constraintMessage, customErrorResponse);
////        }
////    }
//
////    private Direction createOrder(Optional<String> sortAlg) {
////        if (sortAlg.isPresent()) {
////            if (sortAlg.get().equals(DESC.toString())) {
////                return Direction.DESC;
////            }
////        }
////        return ASC;
////    }
//}