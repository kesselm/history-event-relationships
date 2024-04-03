package de.kessel.events.config;

import org.asciidoctor.Asciidoctor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsciidoctorConfig {

    @Bean
    public Asciidoctor createAsciidoctor() {
        return Asciidoctor.Factory.create();
    }
}
