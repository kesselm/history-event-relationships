//package de.kessel.events.config;
//
//import de.kessel.events.dto.*;
//import de.kessel.events.model.EventMetadataEntity;
//import de.kessel.events.repository.EventMetadataRepository;
//import de.kessel.events.service.EventMetadataService;
//import de.kessel.events.service.EventService;
//import de.kessel.events.service.TranslationService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Configuration
//@Slf4j
//@Profile("dev")
//@EnableReactiveMongoRepositories(basePackages = {"de.kessel.events.repository"})
//public class EventArchiveConfiguration {
//
//    private final EventMetadataService eventMetadataService;
//    private final EventService eventService;
//    private final TranslationService translationService;
//
//    public EventArchiveConfiguration(TranslationService translationService,
//                                     EventService eventService, EventMetadataService eventMetadataService) {
//        this.translationService = translationService;
//        this.eventService = eventService;
//        this.eventMetadataService = eventMetadataService;
//    }
//
//    @Bean
//    CommandLineRunner initDatabase(EventMetadataRepository eventMetadataRepository) {
//        return args -> {
//            log.info("Start");
//            var translation = TranslationRequestDto.builder()
//                    .text("*hallo* Welt")
//                    .language("de").build();
//
//            Mono<TranslationResponseDto> savedTranslation = translationService.createTranslation(translation);
//            savedTranslation.subscribe();
//            var translationRequestDto = savedTranslation.block();
//            String translationID = translationRequestDto.getId();
//
//            var eventRequestDto = EventRequestDto.builder()
//                    .translationsIds(List.of(translationID)).build();
//            Mono<EventResponseDto> savedEventResponse = eventService.createEvent(eventRequestDto);
//            savedEventResponse.subscribe();
//            var eventResponseDto = savedEventResponse.block();
//            String eventID = eventResponseDto.getId();
//            log.info("TranslationID: " + translationID);
//
//
//            // Create EventMetadata entities for the database
//
//            eventMetadataService.deleteAllEventMetadatas()
//                    .thenMany(eventMetadataService.createAllEventMetada(createEventMetadataRequestDtos()))
//                    .subscribe();
//
//            Flux<EventMetadataResponseDto> allEventMetadatas = eventMetadataService.findAllEventMetadata();
//            allEventMetadatas.count().subscribe(count -> assertThat(count).isEqualTo(16)).wait(90000);
//
////            Flux<EventMetadataResponseDto> allEventMetadatasForAyear = eventMetadataService.findByYear(2024);
////            allEventMetadatasForAyear.count().subscribe(count -> assertThat(count).isEqualTo(6));
////
////            Flux<EventMetadataResponseDto> allEventMetadatasForYearAndMonth = eventMetadataService.findByYearAndMonth(2024, 1);
////            allEventMetadatasForYearAndMonth.count().subscribe(count -> assertThat(count).isEqualTo(2));
//
////            Flux<EventMetadataResponseDto> allEventMetadatasForYearAndMonthAndDay = eventMetadataService.findByYearAndMonthAndDay(2024, 3, 1);
////            allEventMetadatasForYearAndMonthAndDay.count().subscribe(count -> assertThat(count).isEqualTo(2));
//
////            Flux<EventMetadataResponseDto> allEventMetadatasForYearAndMonthAsc = eventMetadataService.findByYearAndMonthOrderByMonthAsc(2024, 1);
////            allEventMetadatasForYearAndMonthAsc.next()
////                    .subscribe(
////                            firstEventMetadata -> assertThat(firstEventMetadata.getDate()).isEqualTo("2024 n. Chr.")
////                    );
//
////            Flux<EventMetadataResponseDto> allEventMetadatasForYearAndMonthDesc = eventMetadataService.findByYearAndMonthOrderByMonthDesc(2024, 1);
////            allEventMetadatasForYearAndMonthDesc.next()
////                    .subscribe(
////                            firstEventMetadata -> assertThat(firstEventMetadata.getDate()).isEqualTo("1. Februar 2024 n. Chr.")
////                    );
//
//
//        };
//    }
//
//    private List<EventMetadataRequestDto> createEventMetadataRequestDtos() {
//        var emr1 = EventMetadataRequestDto.builder().year(2024).month(1).day(1).eventId("001").build();
//        var emr2 = EventMetadataRequestDto.builder().year(2024).month(1).day(2).eventId("002").build();
//        var emr3 = EventMetadataRequestDto.builder().year(2024).month(3).day(1).eventId("003").build();
//        var emr4 = EventMetadataRequestDto.builder().year(2024).month(3).day(1).eventId("004").build();
//        var emr5 = EventMetadataRequestDto.builder().year(2024).month(4).day(3).eventId("005").build();
//        var emr6 = EventMetadataRequestDto.builder().year(-200).month(0).day(0).eventId("006").build();
//        var emr7 = EventMetadataRequestDto.builder().year(-200).month(0).day(0).eventId("007").build();
//        var emr8 = EventMetadataRequestDto.builder().year(-195).month(5).day(6).eventId("008").build();
//        var emr9 = EventMetadataRequestDto.builder().year(2025).month(1).day(1).eventId("009").build();
//        var emr10 = EventMetadataRequestDto.builder().year(2025).month(1).day(1).eventId("010").build();
//        var emr11 = EventMetadataRequestDto.builder().year(2026).month(1).day(1).eventId("011").build();
//        var emr12 = EventMetadataRequestDto.builder().year(2027).month(1).day(1).eventId("012").build();
//        var emr13 = EventMetadataRequestDto.builder().year(2028).month(1).day(1).eventId("013").build();
//        var emr14 = EventMetadataRequestDto.builder().year(2028).month(1).day(1).eventId("014").build();
//        var emr15 = EventMetadataRequestDto.builder().year(2028).month(1).day(1).eventId("015").build();
//        var emr16 = EventMetadataRequestDto.builder().year(2024).month(0).day(0).eventId("016").build();
//        return List.of(emr1, emr2, emr3, emr4, emr5, emr6, emr7, emr8, emr9, emr10, emr11, emr12, emr13,
//                emr14, emr15, emr16);
//    }
//
//}
