package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.TranslationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class TranslationServiceImpl implements TranslationService {

    private final EventRepository eventRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<TranslationResponseDto> createTranslation(TranslationRequestDto translationRequestDto) {
        return null;
    }

    @Override
    public Mono<TranslationResponseDto> findTranslationById(String id) {
        return null;
    }

    @Override
    public Mono<TranslationResponseDto> updateTranslation(String id, EventRequestDto eventEntity) {
        return null;
    }

    @Override
    public Mono<Void> deleteTranslationById(String id) {
        return null;
    }

    @Override
    public Flux<TranslationResponseDto> findAllTranslation() {
        return null;
    }
}
