package de.kessel.events.service.implementation;

import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.exception.TranslationNotFoundException;
import de.kessel.events.model.TranslationEntity;
import de.kessel.events.repository.TranslationRepository;
import de.kessel.events.service.TranslationService;
import de.kessel.events.util.ErrorMessageUtil;
import org.asciidoctor.Asciidoctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TranslationServiceImplUT {

    @MockBean
    private TranslationRepository translationRepositoryMock;
    @Autowired
    private Asciidoctor asciidoctorMock;
    private TranslationService sut;

    @BeforeEach
    public void setUp() {
        sut = new TranslationServiceImpl(translationRepositoryMock, asciidoctorMock);
    }

    @Test
    @DisplayName("Response should be a created translation.")
    void createTranslation_test_should_return_a_created_translation() {
        var text = "Hallo!";
        TranslationRequestDto translationRequestDto = TranslationRequestDto.builder()
                .text(text)
                .language("de")
                .build();
        TranslationEntity translationEntity = TranslationEntity.builder()
                .id("t1")
                .text(text)
                .language("de").build();

        when(translationRepositoryMock.save(any(TranslationEntity.class)))
                .thenReturn(Mono.just(translationEntity));

        StepVerifier.create(sut.createTranslation(translationRequestDto))
                .consumeNextWith(r -> r.getText().contains(text))
                .verifyComplete();
    }

    @Test
    @DisplayName("The Response should return the found translation.")
    void findTranslationById_test_should_return_an_event() {
        var translationEventId = "008";
        var text = "test1";
        TranslationEntity translationEntity = TranslationEntity.builder()
                .id(translationEventId).text(text).build();
        when(translationRepositoryMock.findById(eq(translationEventId))).thenReturn(Mono.just(translationEntity));

        StepVerifier.create(sut.findTranslationById(translationEventId))
                .consumeNextWith(r -> assertThat(r.getText()).isEqualTo(text))
                .verifyComplete();
    }

    @Test
    @DisplayName("The Response should return all translation.")
    void findAllEvents_test_should_return_all_translation() {
        TranslationEntity translationEntity = TranslationEntity.builder()
                .id("007")
                .language("de")
                .text("test1").build();
        when(translationRepositoryMock.findAll()).thenReturn(Flux.just(translationEntity));

        StepVerifier.create(sut.findAllTranslation())
                .expectNextMatches(event -> event.getText().equals("test1"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("The Response should return the updated translation.")
    void updateTranslation_test_should_return_an_updated_translation() {
        var translationEntityId1 = "009";
        var translationEntityId2 = "010";
        var text1 = "test2";
        var text2 = "test3";

        var translationRequestDto = TranslationRequestDto.builder()
                .language("en")
                .text(text1)
                .build();
        var translationResponseDto = new TranslationResponseDto();
        translationResponseDto.setId("007");
        translationResponseDto.setText(text2);
        translationResponseDto.setLanguage("de");

        Mono<TranslationEntity> originalTranslationEntity = Mono.just(TranslationEntity.builder().text(text1).id(translationEntityId1).build());
        TranslationEntity updateTranslationEntity = TranslationEntity.builder().text(text2).language("de").id(translationEntityId2).build();

        when(translationRepositoryMock.findById(anyString())).thenReturn(originalTranslationEntity);
        when(translationRepositoryMock.save(any())).thenReturn(Mono.just(updateTranslationEntity));

        StepVerifier.create(sut.updateTranslation(translationEntityId1, translationRequestDto))
                .assertNext(translationResponse -> {
                    assertThat(translationResponse.getLanguage()).isEqualTo("de");
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("f no translation to be updated was found, an error is thrown.")
    void updateTranslation_test_should_return_an_error() {
        TranslationRequestDto eventRequestDto = TranslationRequestDto.builder()
                .text("Hallo").build();
        when(translationRepositoryMock.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(sut.updateTranslation("7", eventRequestDto))
                .expectErrorMatches(throwable -> throwable instanceof TranslationNotFoundException
                        && throwable.getMessage().equals(ErrorMessageUtil.getErrorMessage("7",
                        ErrorDetail.TRANSLATION_NOT_FOUND))
                )
                .verify();
    }

    @Test
    @DisplayName("The response should return a list of translations")
    void findAllTranslationsAndPgination_test_should_return_a_list_of_translations() {
        when(translationRepositoryMock.findAllBy(any()))
                .thenReturn(createTranslationEntities());
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "language");
        Pageable pageable = PageRequest.of(1, 4, Sort.by(order));

        StepVerifier.create(sut.findAllTranslationsAndPagination(pageable))
                .expectNextCount(4)
                .verifyComplete();
    }

    private Flux<TranslationEntity> createTranslationEntities() {
        var translationEntity1 = TranslationEntity.builder()
                .text("Hello World")
                .language("en").build();
        var translationEntity2 = TranslationEntity.builder()
                .text("Hello World1")
                .language("en").build();
        var translationEntity3 = TranslationEntity.builder()
                .text("Hello World2")
                .language("en").build();
        var translationEntity4 = TranslationEntity.builder()
                .text("Hello World3")
                .language("en").build();
        return Flux.just(translationEntity1, translationEntity2, translationEntity3,
                translationEntity4);
    }
}
