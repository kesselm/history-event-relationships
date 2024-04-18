package de.kessel.events.service;

import de.kessel.events.dto.EventMetadataRequestDto;
import de.kessel.events.dto.EventMetadataResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

public interface EventMetadataService {
    Mono<EventMetadataResponseDto> createEventMetadata(EventMetadataRequestDto eventMetadataRequestDto) throws ParseException;
    void createEventMetadatas(List<EventMetadataRequestDto> eventMetadataRequestDtoList);
    Mono<EventMetadataResponseDto> findEventMetadataById(String id);
    Flux<EventMetadataResponseDto> findAllEventMetadata();
    Mono<EventMetadataResponseDto> updateMetadataEvent(String id, EventMetadataRequestDto eventMetadataRequestDto) throws ParseException;
    Mono<Void> deleteEventMetadata(String id);

}
