package de.kessel.events.service;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TranslationService {

    Mono<TranslationResponseDto> createTranslation(TranslationRequestDto translationRequestDto);

    Mono<TranslationResponseDto> findTranslationById(String id);

    Mono<TranslationResponseDto> updateTranslation(String id, EventRequestDto eventEntity);

    Mono<Void> deleteTranslationById(String id);

    Flux<TranslationResponseDto> findAllTranslation();

}
