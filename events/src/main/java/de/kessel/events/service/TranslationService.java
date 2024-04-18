package de.kessel.events.service;

import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TranslationService {

    Mono<TranslationResponseDto> createTranslation(TranslationRequestDto translationRequestDto);

    Mono<TranslationResponseDto> findTranslationById(String id);

    Flux<TranslationResponseDto> findAllTranslation();

    Mono<TranslationResponseDto> updateTranslation(String id, TranslationRequestDto translationRequestDto);

    Mono<Void> deleteTranslationById(String id);

    Flux<TranslationResponseDto> findAllTranslationsAndPagination(Pageable pageable);

}
