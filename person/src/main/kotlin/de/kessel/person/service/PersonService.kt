package de.kessel.person.service

import de.kessel.person.dto.PersonRequestDto
import de.kessel.person.dto.PersonResponseDto
import de.kessel.person.repository.PersonRepository
import de.kessel.person.utils.PersonMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PersonService(private val personRepository: PersonRepository) {

    // Create operation
    fun createPerson(request: PersonRequestDto): Mono<PersonResponseDto> {
        val person = PersonMapper.toPerson(request)
        return personRepository.save(person)
            .map { PersonMapper.toPersonResponseDto(it) }
    }

    // Read (single) operation
    fun getPerson(id: String): Mono<PersonResponseDto> {
        return personRepository.findById(id)
            .map { PersonMapper.toPersonResponseDto(it) }
    }

    // Read (all) operation
    fun getAllPeople(): Flux<PersonResponseDto> {
        return personRepository.findAll()
            .map { PersonMapper.toPersonResponseDto(it) }
    }

    // Update operation
    fun updatePerson(id: String, request: PersonRequestDto): Mono<PersonResponseDto> {
        return personRepository.findById(id)
            .flatMap { person ->
                val updatedPerson = person.copy(
                    firstname = request.firstname,
                    lastname = request.lastname,
                    alternativeName = request.alternativeName,
                    birthDate = request.birthDate,
                    deathDate = request.deathDate,
                    birthPlaceId = request.birthPlaceId,
                    deathPlace = request.deathPlace,
                    events = request.events
                )
                personRepository.save(updatedPerson)
            }
            .map { PersonMapper.toPersonResponseDto(it) }
    }

    // Delete operation
    fun deletePerson(id: String): Mono<Void> = personRepository.deleteById(id)
}