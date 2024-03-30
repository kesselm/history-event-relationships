package de.kessel.events.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.Translation;
import de.kessel.events.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Flux;

import java.util.List;

@Configuration
@Slf4j
@EnableMongoRepositories(basePackages = "de.kessel.events.repository")
public class EventArchievConfiguration extends AbstractReactiveMongoConfiguration {

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

    @Bean
    CommandLineRunner initDatabase(EventRepository repository) {
        return args -> {
            log.info("Start");
            Flux.range(1, 10)
                    .flatMap(i -> {
                        Translation translation1 = Translation.builder().language("en").text("Hello World!").build();
                        Translation translation2 = Translation.builder().language("de").text("Hallo Welt!").build();
                        Translation translation3 = Translation.builder().language("zh").text("Nǐ hǎo shìjiè!").build();

                        EventEntity eventEntity = EventEntity.builder()
                                .id("id" + i)
                                .text("Event " + i)
                                .translations(List.of(translation1, translation2, translation3))
                                .build();
                        return repository.save(eventEntity)
                                .doOnSuccess(e -> log.info("Saved {}", e.getId()));
                    }).subscribe();
            log.info("Ende");
        };
    }
}
