package de.kessel.events.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Slf4j
@EnableMongoRepositories(basePackages = "de.kessel.events.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private ApplicationProperties properties;

    public MongoConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public MongoClient reactiveMongoClient() {
        StringBuilder settings = new StringBuilder("mongodb://")
                .append(properties.getUsername())
                .append(":")
                .append(properties.getPassword())
                .append("@")
                .append(properties.getHost())
                .append(":")
                .append(properties.getPort());
        return MongoClients.create(settings.toString());
    }

    @Override
    protected String getDatabaseName() {
        return properties.getDatabase();
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
