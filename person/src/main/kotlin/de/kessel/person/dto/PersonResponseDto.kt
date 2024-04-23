package de.kessel.person.dto

import java.time.LocalDate

data class PersonResponseDto(
    val id: String,
    val firstname: String,
    val lastname: String,
    val alternativeName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate,
    val birthPlaceId: String,
    val deathPlace: String,
    val events: List<String>
)