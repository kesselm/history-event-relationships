package de.kessel.launcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EventArchiveConfiguration {

//    @Bean
//    CommandLineRunner initDatabase(EventRepository repository) {
//        return args -> {
//            log.info("Start");
//            Flux.range(1, 10)
//                    .flatMap(i -> {
//                        Translation translation1 = Translation.builder().language("en").text("Hello World!").build();
//                        Translation translation2 = Translation.builder().language("de").text("Hallo Welt!").build();
//                        Translation translation3 = Translation.builder().language("zh").text("Nǐ hǎo shìjiè!").build();
//
//                        EventEntity eventEntity = EventEntity.builder()
//                                .id("id" + i)
//                                .text("Event " + i)
//                                .translations(List.of(translation1, translation2, translation3))
//                                .build();
//                        return repository.save(eventEntity)
//                                .doOnSuccess(e -> log.info("Saved {}", e.getId()));
//                    }).subscribe();
//            log.info("Ende");
//        };
//    }
}
