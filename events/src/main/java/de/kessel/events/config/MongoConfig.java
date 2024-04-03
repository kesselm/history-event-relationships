package de.kessel.events.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Slf4j
@EnableMongoRepositories(basePackages = "de.kessel.events.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${udemy.mongodb.replicaset.name}")
    private String replicasetName;
    @Value("${udemy.mongodb.replicaset.username}")
    private String replicasetUsername;
    @Value("${udemy.mongodb.replicaset.password}")
    private String replicasetPassword;
    @Value("${udemy.mongodb.replicaset.primary}")
    private String replicasetPrimary;
    @Value("${udemy.mongodb.replicaset.port}")
    private String replicasetPort;
    @Value("${udemy.mongodb.replicaset.database}")
    private String database;
    @Value("${udemy.mongodb.replicaset.authentication-database}")
    private String replicasetAuthenticationDb;

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://" + replicasetUsername + ":" + replicasetPassword + "@" + replicasetPrimary + ":" + replicasetPort + "/" + database + "?replicaSet=" + replicasetName + "&authSource=" + replicasetAuthenticationDb);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
