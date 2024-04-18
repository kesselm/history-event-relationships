package de.kessel.events.config;

import de.kessel.events.dto.*;
import de.kessel.events.service.EventMetadataService;
import de.kessel.events.service.EventService;
import de.kessel.events.service.TranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
@Profile("dev")
@EnableReactiveMongoRepositories(basePackages = {"de.kessel.events.repository"})
public class EventArchiveConfiguration {

    private final EventMetadataService eventMetadataService;
    private final EventService eventService;
    private final TranslationService translationService;

    public EventArchiveConfiguration(TranslationService translationService,
                                     EventService eventService, EventMetadataService eventMetadataService) {
        this.translationService = translationService;
        this.eventService = eventService;
        this.eventMetadataService = eventMetadataService;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Start");
            var translation = TranslationRequestDto.builder()
                    .text("*hallo* Welt")
                    .language("de").build();

            Mono<TranslationResponseDto> savedTranslation = translationService.createTranslation(translation);
            savedTranslation.subscribe();
            var translationRequestDto = savedTranslation.block();
            String translationID = translationRequestDto.getId();

            var eventRequestDto = EventRequestDto.builder()
                    .translationsIds(List.of(translationID)).build();
            Mono<EventResponseDto> savedEventResponse = eventService.createEvent(eventRequestDto);
            savedEventResponse.subscribe();
            var eventResponseDto = savedEventResponse.block();
            String eventID = eventResponseDto.getId();

            var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                    .eventId(eventID)
                    .year(2024).build();
            Mono<EventMetadataResponseDto> savedEventMetadataResponse = eventMetadataService.createEventMetadata(eventMetadataRequestDto);
            savedEventMetadataResponse.subscribe();
            var eventMetadataResponseDto = savedEventMetadataResponse.block();
            String eventMetadataID = eventMetadataResponseDto.getId();

            log.info("TranslationID: " + translationID);
            log.info("EventID: " + eventID);
            log.info("EventMetadataID: " + eventMetadataID);
            log.info("End");
        };
    }
}
