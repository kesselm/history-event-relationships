package de.kessel.launcher.endpoints.router;

import de.kessel.launcher.endpoints.handler.implementation.TranslationHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static de.kessel.launcher.tools.EventConstants.TRANSLATION_API;

@Configuration
@ComponentScan(basePackages = {
        "de.kessel.events",
        "de.kessel.events",
        "de.kessel.events.dto",
        "de.kessel.launcher"})
public class TranslationRouter {

    @Bean
    public RouterFunction<ServerResponse> translationRoute(TranslationHandlerImpl handler) {
        return RouterFunctions
                .route(RequestPredicates.POST(TRANSLATION_API)
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        handler::createTranslation);
    }
}
