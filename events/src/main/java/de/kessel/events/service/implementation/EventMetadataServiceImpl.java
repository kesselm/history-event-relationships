package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventMetadataRequestDto;
import de.kessel.events.dto.EventMetadataResponseDto;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.exception.PropertyValidationException;
import de.kessel.events.model.EventMetadataEntity;
import de.kessel.events.repository.EventMetadataRepository;
import de.kessel.events.service.EventMetadataService;
import de.kessel.events.util.EntityMapper;
import de.kessel.events.util.ErrorMessageUtil;
import de.kessel.events.util.EventUtil;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EventMetadataServiceImpl implements EventMetadataService {

    private final EventMetadataRepository eventMetadataRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<EventMetadataResponseDto> createEventMetadata(EventMetadataRequestDto eventMetadataRequestDto) {
        String validationMessage = eventMetadataRequestDto.validate();
        if (StringUtils.isNotEmpty(validationMessage)) {
            Optional<ErrorDetail> errorDetail = EventUtil.getErrorDetailFromErrorMessage(validationMessage);
            if (errorDetail.isPresent()) {
                log.error(errorDetail.get().getErrorCode() + " - " + errorDetail.get().getErrorMessage());
                return Mono.error(new PropertyValidationException(errorDetail.get().getErrorMessage(),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NOT_ACCEPTABLE,
                                errorDetail.get())));
            }
        }

        Options options = Options.builder().build();
        String referenceAsHtml = asciidoctor.convert(eventMetadataRequestDto.getReference(), options);
        String commentAsHtml = asciidoctor.convert(eventMetadataRequestDto.getComment(), options);
        EventMetadataEntity eventMetadataEntity = EventMetadataEntity.builder()
                .comment(commentAsHtml)
                .reference(referenceAsHtml)
                .personId(eventMetadataRequestDto.getPersonId())
                .locationId(eventMetadataRequestDto.getLocationId())
                .epocheId(eventMetadataRequestDto.getEpocheId())
                .scope(eventMetadataRequestDto.getScope())
                .status(eventMetadataRequestDto.getStatus())
                .topicId(eventMetadataRequestDto.getTopicId())
                .year(eventMetadataRequestDto.getYear())
                .month(eventMetadataRequestDto.getMonth() != null ? eventMetadataRequestDto.getMonth() : 0)
                .day(eventMetadataRequestDto.getDay() != null ? eventMetadataRequestDto.getDay() : 0)
                .eventId(eventMetadataRequestDto.getEventId())
                .build();
        return eventMetadataRepository.save(eventMetadataEntity).map(EntityMapper::convertToEventMetadataResponseDto);
    }

    @SneakyThrows
    @Override
    public void createEventMetadatas(List<EventMetadataRequestDto> eventMetadataRequestDtoList) {
        for (EventMetadataRequestDto requestDto : eventMetadataRequestDtoList) {
            Mono<EventMetadataResponseDto> eventMetadataRequestDto = createEventMetadata(requestDto);
        }
    }

    @Override
    public Mono<EventMetadataResponseDto> findEventMetadataById(String id) {
        return eventMetadataRepository.findById(id)
                .map(EntityMapper::convertToEventMetadataResponseDto)
                .switchIfEmpty(Mono.error(new EventNotFoundException(ErrorMessageUtil.getErrorMessage(id,
                        ErrorDetail.EVENT_METADATA_NOT_FOUND),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NO_CONTENT,
                                ErrorDetail.EVENT_METADATA_NOT_FOUND))));
    }

    @Override
    public Flux<EventMetadataResponseDto> findAllEventMetadata() {
        return eventMetadataRepository.findAll().map(EntityMapper::convertToEventMetadataResponseDto)
                .switchIfEmpty(Mono.error(new EventNotFoundException(ErrorMessageUtil.getErrorMessage("",
                        ErrorDetail.EVENT_METADATAS_NOT_FOUND),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NO_CONTENT,
                                ErrorDetail.EVENT_METADATAS_NOT_FOUND))));
    }

    @Override
    public Mono<EventMetadataResponseDto> updateMetadataEvent(String id, EventMetadataRequestDto eventMetadataRequestDto) throws ParseException {
        return eventMetadataRepository.findById(id)
                .flatMap(dbEvent -> {
                    dbEvent.setPersonId(eventMetadataRequestDto.getPersonId());
                    dbEvent.setLocationId(eventMetadataRequestDto.getLocationId());
                    dbEvent.setEpocheId(eventMetadataRequestDto.getEpocheId());
                    dbEvent.setScope(eventMetadataRequestDto.getScope());
                    dbEvent.setStatus(eventMetadataRequestDto.getStatus());
                    dbEvent.setTopicId(eventMetadataRequestDto.getTopicId());
                    dbEvent.setReference(eventMetadataRequestDto.getReference());
                    dbEvent.setYear(eventMetadataRequestDto.getYear());
                    dbEvent.setMonth(eventMetadataRequestDto.getMonth());
                    dbEvent.setDay(eventMetadataRequestDto.getDay());
                    dbEvent.setEventId(eventMetadataRequestDto.getEventId());
                    dbEvent.setComment(eventMetadataRequestDto.getComment());
                    return eventMetadataRepository.save(dbEvent).map(EntityMapper::convertToEventMetadataResponseDto);
                });
    }

    @Override
    public Mono<Void> deleteEventMetadata(String id) {
        return eventMetadataRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new EventNotFoundException(ErrorMessageUtil.getErrorMessage(id,
                        ErrorDetail.EVENT_METADATA_NOT_FOUND),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NO_CONTENT,
                                ErrorDetail.EVENT_METADATA_NOT_FOUND))));
    }
}
