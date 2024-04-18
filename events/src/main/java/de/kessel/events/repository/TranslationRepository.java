package de.kessel.events.repository;

import de.kessel.events.model.TranslationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TranslationRepository extends ReactiveMongoRepository<TranslationEntity, String> {
    Flux<TranslationEntity> findAllBy(Pageable pageable);
}
