package de.kessel.launcher.endpoints.handler.implementation;

import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@ComponentScan(basePackages = {
        "de.kessel.events",
        "de.kessel.events.service",
        "de.kessel.events.dto",
        "de.kessel.launcher",
        "de.kessel.launcher.endpoints"})
@Slf4j
public class TranslationHandlerImpl {

    private final TranslationService translationService;

    public Mono<ServerResponse> createTranslation(ServerRequest serverRequest) {
        TranslationRequestDto ddd = TranslationRequestDto.builder()
                .text("dddd")
                .language("de")
                .build();

        return serverRequest
                .bodyToMono(TranslationRequestDto.class)
                .flatMap(translationService::createTranslation)
                .flatMap(translationResponseDto -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(translationResponseDto));
    }
}
