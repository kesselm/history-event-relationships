package de.kessel.events.repository;

import de.kessel.events.model.EventEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ReactiveMongoRepository<EventEntity, String> {

}
