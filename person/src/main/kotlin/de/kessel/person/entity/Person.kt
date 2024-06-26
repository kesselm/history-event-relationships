package de.kessel.person.entity

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(collection = "People")
data class Person(
    val id: String = "person_" + UUID.randomUUID().toString(),
    val firstname: String,
    val lastname: String,
    val alternativeName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate,
    val birthPlaceId: String,
    val deathPlace: String,
    val events: List<String>
)