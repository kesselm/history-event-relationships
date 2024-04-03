//package de.kessel.events.service.implementation;
//
//import de.kessel.events.dto.EventRequestDto;
//import de.kessel.events.model.EventMetadataEntity;
//import de.kessel.events.model.Translation;
//import de.kessel.events.service.EventMetadataService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.asciidoctor.Asciidoctor;
//import org.asciidoctor.Options;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class EventMetadataServiceImpl implements EventMetadataService {
//
//    private final Asciidoctor asciidoctor;
//
//    @Override
//    public Mono<EventMetadataEntity> createEventMetadata(EventMetadataEntity eventMetadataEntity) {
//        Options options = Options.builder().build();
//        String cityAsHtml = asciidoctor.convert(eventMetadataEntity.getCity(), options);
//        String epocheAsHtml = asciidoctor.convert(eventMetadataEntity.getEpoche(), options);
//        String referenceAsHtml = asciidoctor.convert(eventMetadataEntity.getReference(), options);
//        String placeAsHtml = asciidoctor.convert(eventMetadataEntity.getPlace(), options);
//        eventMetadataEntity.setCity(cityAsHtml);
//        eventMetadataEntity.setEpoche(epocheAsHtml);
//        eventMetadataEntity.setReference(referenceAsHtml);
//        eventMetadataEntity.setPlace(placeAsHtml);
//        return Mono.just(eventMetadataEntity);
//    }
//
//    @Override
//    public Mono<EventMetadataEntity> getEventMetadataById(String id) {
//        return null;
//    }
//
//    @Override
//    public Mono<EventMetadataEntity> updateMetadataEvent(String id, EventRequestDto eventEntity) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> deleteEventMetadata(String id) {
//        return null;
//    }
//
//    @Override
//    public Flux<EventMetadataEntity> getAllEventsMetadata() {
//        return null;
//    }
//
//    @Override
//    public Mono<Translation> getSingleTranslation(String id, String lang) {
//        return null;
//    }
//}
