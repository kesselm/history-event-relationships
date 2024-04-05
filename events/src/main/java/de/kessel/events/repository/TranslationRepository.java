package de.kessel.events.repository;

import de.kessel.events.model.TranslationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends ReactiveMongoRepository<TranslationEntity, String>  {
}
