//package de.kessel.events.service.implementation;
//
//import de.kessel.events.dto.*;
//import de.kessel.events.service.TranslationService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//import reactor.core.publisher.Mono;
//
//import javax.sound.midi.Soundbank;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class ServicesImplIT {
//
//    @Container
//    public static GenericContainer<?> mongoDBContainer = new GenericContainer<>(DockerImageName.parse("mongo:latest"))
//            .withExposedPorts(27017)
//            .withReuse(true)
//            .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
//            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "admin");
//
//    @DynamicPropertySource
//    static void elasticProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getMappedPort(27017));
//        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
//        registry.add("spring.data.mongodb.username", () -> "admin");
//        registry.add("spring.data.mongodb.password", () -> "admin");
//        registry.add("spring.data.mongodb.database", () -> "history-event-db");
//    }
//
//    @Autowired
//    private TranslationService translationService;
//
//    @Test
//    @DisplayName("Test for creating a translation")
//    void creating_translation_test_should_return_correct_id() {
//        var translation = TranslationRequestDto.builder().build();
//        Mono<TranslationResponseDto> translationResponseDtoMono = translationService.createTranslation(translation);
//        translationResponseDtoMono.subscribe();
//        var translationResponseDto = translationResponseDtoMono.block();
//        String translationId = translationResponseDto.getId();
//        System.out.println("TranslationId 1: " + translationId);
//
//        Mono<TranslationResponseDto> translationResponseDtoMono1 = translationService.findTranslationById(translationId);
//        translationResponseDtoMono1.subscribe();
//        var translationResponseDto1 = translationResponseDtoMono1.block();
//
//        System.out.println("TranslationId 2: " + translationResponseDto1.getId());
//        assertThat(translationResponseDto1.getId()).contains("translation_");
//    }
//
//    private TranslationRequestDto createTranslationRequestDto(String text, String language) {
//        return TranslationRequestDto.builder()
//                .text(text)
//                .language(language)
//                .build();
//    }
//
//}
