package de.kessel.events.service;

import de.kessel.events.dto.EventMetadataRequestDto;
import de.kessel.events.dto.EventMetadataResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;

public interface EventMetadataService {
    Mono<EventMetadataResponseDto> createEventMetadata(EventMetadataRequestDto eventMetadataRequestDto) throws ParseException;
    Flux<EventMetadataResponseDto> createAllEventMetada(List<EventMetadataRequestDto> eventMetadataRequestDtos);
    Mono<EventMetadataResponseDto> findEventMetadataById(String id);
    Flux<EventMetadataResponseDto> findAllEventMetadata();
    Mono<EventMetadataResponseDto> updateMetadataEvent(String id, EventMetadataRequestDto eventMetadataRequestDto) throws ParseException;
    Mono<Void> deleteEventMetadata(String id);
    Mono<Void> deleteAllEventMetadatas();
    Flux<EventMetadataResponseDto> findByYear(int year);
    Flux<EventMetadataResponseDto> findAllByOrderByYearAsc();
    Flux<EventMetadataResponseDto> findAllByOrderByYearDesc();
    Flux<EventMetadataResponseDto> findByYearAndMonth(int year, int month);
    Flux<EventMetadataResponseDto> findByYearAndMonthAndDay(int year, int month, int day);
    Flux<EventMetadataResponseDto> findByYearAndMonthOrderByMonthAsc(int year, int month);
    Flux<EventMetadataResponseDto> findByYearAndMonthOrderByMonthDesc(int year, int month);
    Flux<EventMetadataResponseDto> findByYearAndMonthAndDayOrderByDayAsc(int year, int month, int day);
    Flux<EventMetadataResponseDto> findByYearAndMonthAndDayOrderByDayDesc(int year, int month, int day);
    Flux<EventMetadataResponseDto> findByYearBetweenOrderByYearAsc(int startYear, int endYear);
    Flux<EventMetadataResponseDto> findByYearBetweenOrderByYearDesc(int startYear, int endYear);

}
