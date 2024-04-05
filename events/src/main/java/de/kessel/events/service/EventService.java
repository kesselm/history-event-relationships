package de.kessel.events.service;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.dto.TranslationResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {

    Mono<EventResponseDto> createEvent(EventRequestDto eventRequestDto);
    Mono<EventResponseDto> findEventById(String id);
    Flux<EventResponseDto> findAllEvents();
    Mono<EventResponseDto> updateEvent(String id, EventRequestDto eventEntity);
    Mono<Void> deleteEventById(String id);
    Mono<TranslationResponseDto> getSingleTranslation(String id, String lang);
}