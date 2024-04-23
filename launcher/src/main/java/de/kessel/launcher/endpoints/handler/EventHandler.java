package de.kessel.launcher.endpoints.handler;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;


@Tag(name = "History Event Application", description = "CRUD Application for history events.")
public interface EventHandler {

    Mono<ServerResponse> createEvent(ServerRequest serverRequest) throws ExecutionException, InterruptedException;

    Mono<ServerResponse> findEventById(ServerRequest request);

    Mono<ServerResponse> deleteEventById(ServerRequest request);

    Mono<ServerResponse> findAllEvents(ServerRequest serverRequest);

    Mono<ServerResponse> updateEventById(ServerRequest serverRequest);

    Mono<ServerResponse> selectSingleTranslation(ServerRequest serverRequest);

}