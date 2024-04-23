package de.kessel.person.entity

import java.time.LocalDate

data class Person(
    val firstname: String,
    val lastname: String,
    val alternativeName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate,
    val birthPlaceId: String,
    val deathPlace: String,
    val events: List<String>
)