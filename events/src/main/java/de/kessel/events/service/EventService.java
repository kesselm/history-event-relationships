package de.kessel.events.service;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.Translation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventService {

    Mono<EventResponseDto> createEvent(EventRequestDto eventRequestDto);

    Mono<EventEntity> getEventById(String id);

    Mono<EventEntity> updateEvent(String id, EventRequestDto eventEntity);

    Mono<Void> deleteEvent(String id);

    Flux<EventEntity> getAllEvents();

    Mono<Translation> getSingleTranslation(String id, String lang);
}