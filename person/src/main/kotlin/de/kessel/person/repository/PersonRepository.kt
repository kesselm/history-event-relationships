package de.kessel.person.repository

import de.kessel.person.entity.Person
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PersonRepository: ReactiveMongoRepository<Person, String> {

    // example of a custom query
    fun findByName(name: String): Mono<Person>
}