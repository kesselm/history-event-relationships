package de.kessel.events.service;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.model.EventMetadataEntity;
import de.kessel.events.model.Translation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventMetadataService {
    Mono<EventMetadataEntity> createEventMetadata(EventMetadataEntity eventMetadataEntity);

    Mono<EventMetadataEntity> getEventMetadataById(String id);

    Mono<EventMetadataEntity> updateMetadataEvent(String id, EventRequestDto eventEntity);

    Mono<Void> deleteEventMetadata(String id);

    Flux<EventMetadataEntity> getAllEventsMetadata();

    Mono<Translation> getSingleTranslation(String id, String lang);
}
