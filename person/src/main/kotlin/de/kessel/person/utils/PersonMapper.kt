package de.kessel.person.utils

import de.kessel.person.dto.PersonRequestDto
import de.kessel.person.dto.PersonResponseDto
import de.kessel.person.entity.Person

object PersonMapper {

    fun toPerson(request: PersonRequestDto): Person {
        return Person(
            firstname = request.firstname,
            lastname = request.lastname,
            alternativeName = request.alternativeName,
            birthDate = request.birthDate,
            deathDate = request.deathDate,
            birthPlaceId = request.birthPlaceId,
            deathPlace = request.deathPlace,
            events = request.events
        )
    }

    fun toPersonResponseDto(person: Person): PersonResponseDto {
        return PersonResponseDto(
            id = person.id,
            firstname = person.firstname,
            lastname = person.lastname,
            alternativeName = person.alternativeName,
            birthDate = person.birthDate,
            deathDate = person.deathDate,
            birthPlaceId = person.birthPlaceId,
            deathPlace = person.deathPlace,
            events = person.events
        )
    }
}