package de.kessel.events.repository;

import de.kessel.events.model.EventMetadataEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMetadataRepository extends ReactiveMongoRepository<EventMetadataEntity, String> {
}
