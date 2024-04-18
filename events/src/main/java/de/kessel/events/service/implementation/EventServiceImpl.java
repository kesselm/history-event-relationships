package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.model.EventEntity;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.EventService;
import de.kessel.events.util.EntityMapper;
import de.kessel.events.util.ErrorMessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<EventResponseDto> createEvent(EventRequestDto eventRequestDto) {
        EventEntity eventEntity = EntityMapper.convertToEventEntity(eventRequestDto);
        Mono<EventEntity> savedEventEntity = eventRepository.save(eventEntity);
        return savedEventEntity.map(EntityMapper::convertToEventResponseDto);
    }

    @Override
    public Mono<EventResponseDto> findEventById(String id) {
        return eventRepository.findById(id)
                .map(EntityMapper::convertToEventResponseDto)
                .switchIfEmpty(Mono.error(new EventNotFoundException(
                        ErrorMessageUtil.getErrorMessage(id,
                                ErrorDetail.EVENT_NOT_FOUND), ErrorMessageUtil.getCustomErrorResponse(
                        HttpStatus.NO_CONTENT,
                        ErrorDetail.EVENT_NOT_FOUND
                ))));
    }

    @Override
    public Flux<EventResponseDto> findAllEvents() {
        return eventRepository.findAll().map(EntityMapper::convertToEventResponseDto);
    }

    @Override
    public Mono<EventResponseDto> updateEvent(String id, EventRequestDto eventRequestDto) {
        return eventRepository.findById(id)
                .flatMap(dbEvent -> {
                    dbEvent.setTranslationIds(eventRequestDto.getTranslationsIds());
                    return eventRepository.save(dbEvent).map(EntityMapper::convertToEventResponseDto);
                });
    }

    @Override
    public Mono<Void> deleteEventById(String id) {
        return eventRepository.deleteById(id);
    }
}