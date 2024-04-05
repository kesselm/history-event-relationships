package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.dto.TranslationResponseDto;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.exception.CustomErrorResponse;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.model.EventEntity;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.EventService;
import de.kessel.events.util.EntityConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<EventResponseDto> createEvent(EventRequestDto eventRequestDto) {
        EventEntity eventEntity = EntityConverter.convertToEventEntity(eventRequestDto);
        String htmlContent = asciidoctor.convert(eventRequestDto.getText(), Options.builder().build());
        eventEntity.setText(htmlContent);
        eventEntity.getTranslations().forEach(translation -> {
            String html = asciidoctor.convert(translation.getText(), Options.builder().build());
            translation.setText(html);
        });
        Mono<EventEntity> savedEventEntity = eventRepository.save(eventEntity);
        return savedEventEntity.map(EntityConverter::convertToEventResponseDto);
    }

    @Override
    public Mono<EventResponseDto> findEventById(String id) {
        return eventRepository.findById(id)
                .map(EntityConverter::convertToEventResponseDto)
                .switchIfEmpty(Mono.error(new EventNotFoundException(getErrorMessage(id), getCustomErrorResponse())));
    }

    @Override
    public Flux<EventResponseDto> findAllEvents() {
        return eventRepository.findAll().map(EntityConverter::convertToEventResponseDto);
    }

    @Override
    public Mono<EventResponseDto> updateEvent(String id, EventRequestDto eventRequestDto) {
        return eventRepository.findById(id)
                .flatMap(dbEvent -> {
                    dbEvent.setText(eventRequestDto.getText());
                    dbEvent.setTranslations(eventRequestDto.getTranslations()
                            .stream().map(EntityConverter::convertToTranslationEntity).toList());
                    return eventRepository.save(dbEvent).map(EntityConverter::convertToEventResponseDto);
                });
    }

    @Override
    public Mono<Void> deleteEventById(String id) {
        return eventRepository.deleteById(id);
    }

    @Override
    public Mono<TranslationResponseDto> getSingleTranslation(String id, String lang) {
        return eventRepository.findById(id)
                .flatMapIterable(EventEntity::getTranslations)
                .filter(translation -> lang.equals(translation.getLanguage()))
                .next()
                .map(EntityConverter::convertToTranslationResponseDto)
                .onErrorResume(NoSuchElementException.class, ex ->
                        Mono.error(new EventNotFoundException(getErrorMessage(id), getCustomErrorResponse())));
    }

    private String getErrorMessage(String id) {
        String errorMessage = new StringBuilder(ErrorDetail.EVENT_NOT_FOUND.getErrorCode())
                .append(" - ").append(String.format(ErrorDetail.EVENT_NOT_FOUND.getErrorMessage(), id)).toString();
        log.info(errorMessage);
        return errorMessage;
    }

    private CustomErrorResponse getCustomErrorResponse() {
        return CustomErrorResponse
                .builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().now())
                .status(HttpStatus.NO_CONTENT)
                .errors(List.of(ErrorDetail.EVENT_NOT_FOUND))
                .build();
    }
}