package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.model.CustomErrorResponse;
import de.kessel.events.model.ErrorDetail;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.Translation;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.EventService;
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
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<EventEntity> createEvent(EventEntity eventEntity) {
        String htmlContent = asciidoctor.convert(eventEntity.getText(), Options.builder().build());
        eventEntity.setText(htmlContent);
        eventEntity.getTranslations().forEach(translation -> {
                    String html = asciidoctor.convert(translation.getText(), Options.builder().build());
                    translation.setText(html);
                });
        return eventRepository.save(eventEntity);
    }

    @Override
    public Flux<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Mono<EventEntity> getEventById(String id) {
        return eventRepository.findById(id)
                .switchIfEmpty(Mono.error(new EventNotFoundException(getErrorMessage(id),getCustomErrorResponse())));
    }

    @Override
    public Mono<EventEntity> updateEvent(String id, EventRequestDto eventRequestDto) {
        return eventRepository.findById(id)
                .flatMap(dbEvent -> {
                    dbEvent.setText(eventRequestDto.getText());
                    dbEvent.setTranslations(eventRequestDto.getTranslations());
                    return eventRepository.save(dbEvent);
                });
    }

    @Override
    public Mono<Void> deleteEvent(String id) {
        return eventRepository.deleteById(id);
    }


    @Override
    public Mono<Translation> getSingleTranslation(String id, String lang) {
        return eventRepository.findById(id)
                .flatMapIterable(EventEntity::getTranslations)
                .filter(translation -> lang.equals(translation.getLanguage()))
                .next()
                .switchIfEmpty(Mono.empty());
    }

    private String getErrorMessage(String id) {
        String errorMessage = new StringBuilder(ErrorDetail.EVENT_NOT_FOUND.getErrorCode())
                .append(" - ").append(String.format(ErrorDetail.EVENT_NOT_FOUND.getErrorMessage(),id)).toString();
        log.info(errorMessage);
        return errorMessage;
    }

    private CustomErrorResponse getCustomErrorResponse(){
        return CustomErrorResponse
                .builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().now())
                .status(HttpStatus.NO_CONTENT)
                .errors(List.of(ErrorDetail.EVENT_NOT_FOUND))
                .build();
    }
}