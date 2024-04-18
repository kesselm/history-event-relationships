package de.kessel.events.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class ApplicationProperties {
    private String database;
    private Integer port;
    private String host;
    private String username;
    private String password;
    private String AutoIndexCreation;
}
