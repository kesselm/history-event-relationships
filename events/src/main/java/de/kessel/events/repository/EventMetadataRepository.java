package de.kessel.events.repository;

import de.kessel.events.model.EventMetadataEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventMetadataRepository extends ReactiveMongoRepository<EventMetadataEntity, String> {

    Flux<EventMetadataEntity> findByYear(int yer);
    Flux<EventMetadataEntity> findAllByYearAndMonth(int year, int month);
    Flux<EventMetadataEntity> findByYearAndMonthAndDay(int year, int month, int day);
    Flux<EventMetadataEntity> findByYearOrderByMonthAsc(int year, int month);
    Flux<EventMetadataEntity> findByYearAndMonthOrderByMonthDesc(int year, int month);
    Flux<EventMetadataEntity> findByYearAndMonthAndDayOrderByDayAsc(int year, int month, int day);
    Flux<EventMetadataEntity> findByYearAndMonthAndDayOrderByDayDesc(int year, int month, int day);
    Flux<EventMetadataEntity> findByYearBetweenOrderByYearAsc(int startYear, int endYear);
    Flux<EventMetadataEntity> findByYearBetweenOrderByYearDesc(int startYear, int endYear);

}
