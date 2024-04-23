//package de.kessel.launcher.endpoints.router;
//
//import de.kessel.events.dto.EventRequestDto;
//import de.kessel.events.dto.EventResponseDto;
//import de.kessel.launcher.endpoints.handler.implementation.EventHandlerImpl;
//import de.kessel.events.service.EventService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.springdoc.core.annotations.RouterOperation;
//import org.springdoc.core.annotations.RouterOperations;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static de.kessel.launcher.tools.EventConstants.EVENTS_API;


//@Configuration
//@ComponentScan(basePackages = {"de.kessel.launcher.endpoints.handler",
//        "de.kessel.launcher.utils",
//        "de.kessel.launcher"})
//public class EventRouter {

//    @RouterOperations({
//            @RouterOperation(path = EVENTS_API, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, beanClass = EventService.class, beanMethod = "createEvent",
//                    operation = @Operation(operationId = "createEvent", responses = {
//                            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = EventResponseDto.class))),
//                            @ApiResponse(responseCode = "404", description = "Bad Request")},
//                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = EventRequestDto.class)))
//                    )
//            ),
//            @RouterOperation(path = EVENTS_API + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, beanClass = EventService.class, beanMethod = "getEventById",
//                    operation = @Operation(operationId = "getEventById", responses = {
//                            @ApiResponse(responseCode = "200", description = "Created", content = @Content(schema = @Schema(implementation = EventRequestDto.class))),
//                            @ApiResponse(responseCode = "404", description = "Bad Request")
//                    })
//            ),
//            @RouterOperation(path = EVENTS_API + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, beanClass = EventService.class, beanMethod = "updateEvent",
//                    operation = @Operation(operationId = "updateEvent", responses = {
//                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = EventRequestDto.class))),
//                            @ApiResponse(responseCode = "400", description = "Invalid Event supplied"),
//                            @ApiResponse(responseCode = "404", description = "Event not found")
//                    })
//            ),
//            @RouterOperation(path = EVENTS_API + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE, beanClass = EventService.class, beanMethod = "deleteEvent",
//                    operation = @Operation(operationId = "deleteEvent", responses = {
//                            @ApiResponse(responseCode = "204", description = "successful operation, no content returned"),
//                            @ApiResponse(responseCode = "404", description = "Event not found"),
//                    })
//            ),
//            @RouterOperation(path = EVENTS_API, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, beanClass = EventService.class, beanMethod = "getAllEvents",
//                    operation = @Operation(operationId = "getAllEvents", responses = {
//                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventRequestDto.class))))
//                    })
//            )
//    })
//    @Bean
//    public RouterFunction<ServerResponse> eventRoute(EventHandlerImpl handler) {
//        return RouterFunctions
//                .route(RequestPredicates.POST(EVENTS_API)
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::createEvent);
//                .andRoute(RequestPredicates.GET(EVENTS_API)
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findAllEvents)
//                .andRoute(RequestPredicates.GET(EVENTS_API + "/{id}")
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::findEventById)
//                .andRoute(RequestPredicates.DELETE(EVENTS_API + "/{id}")
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::deleteEventById)
//                .andRoute(RequestPredicates.PUT(EVENTS_API + "/{id}")
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::updateEventById)
//                .andRoute(RequestPredicates.GET(TRANSLATION_API)
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::selectSingleTranslation);
//    }
//}