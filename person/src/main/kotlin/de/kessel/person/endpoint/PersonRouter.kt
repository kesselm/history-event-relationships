package de.kessel.person.endpoint

import de.kessel.person.utils.UriConstants
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class PersonRouter(private val personHandler: PersonHandler) {

    @Bean
    fun route() = router {
        (accept(MediaType.APPLICATION_JSON) and UriConstants.API).nest {
            POST(UriConstants.PERSON, personHandler::createPerson)
            GET(UriConstants.PERSON_ID, personHandler::getPerson)
            GET(UriConstants.PERSON, personHandler::getAllPeople)
            PUT(UriConstants.PERSON_ID, personHandler::updatePerson)
            DELETE(UriConstants.PERSON_ID, personHandler::deletePerson)
        }
    }
}