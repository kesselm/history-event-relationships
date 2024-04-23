package de.kessel.person.endpoint

import de.kessel.person.dto.PersonRequestDto
import de.kessel.person.dto.PersonResponseDto
import de.kessel.person.service.PersonService
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class PersonHandler(private val personService: PersonService) {

    fun createPerson(request: ServerRequest): Mono<ServerResponse> {
        val personRequest = request.bodyToMono(PersonRequestDto::class.java)
        return personRequest.flatMap { req ->
            personService.createPerson(req)
                .flatMap { person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(Mono.just(person), PersonResponseDto::class.java)) }
        }
    }

    fun getPerson(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return personService.getPerson(id)
            .flatMap { person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(Mono.just(person), PersonResponseDto::class.java)) }
    }

    fun getAllPeople(request: ServerRequest): Mono<ServerResponse> {
        return personService.getAllPeople()
            .collectList()
            .flatMap { personList -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(Mono.just(personList), List::class.java)) }
    }

    fun updatePerson(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        val personRequest = request.bodyToMono(PersonRequestDto::class.java)
        return personRequest.flatMap { req ->
            personService.updatePerson(id, req)
                .flatMap { person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(Mono.just(person), PersonResponseDto::class.java)) }
        }
    }

    fun deletePerson(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return personService.deletePerson(id)
            .flatMap { ServerResponse.ok().build() }
    }

}