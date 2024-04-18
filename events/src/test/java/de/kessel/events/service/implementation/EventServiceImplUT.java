package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.TranslationEntity;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.EventService;
import org.asciidoctor.Asciidoctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EventServiceImplUT {

    private final String englishText = "How are you?";
    private final String chineseText = "nĭ hăo ma?";

    @Mock
    private EventRepository eventRepositoryMock;
    @Autowired
    private Asciidoctor asciidoctorMock;

    private EventService sut;

    private TranslationRequestDto translationRequestDto;

    @BeforeEach
    public void setup() {
        sut = new EventServiceImpl(eventRepositoryMock, asciidoctorMock);
        translationRequestDto = TranslationRequestDto.builder()
                .language("en")
                .text("Hello World!")
                .build();
    }

    @Test
    @DisplayName("Response should be a created event.")
    void createEvent_test_should_return_a_created_event() {
        EventRequestDto eventRequestDto = EventRequestDto.builder()
                .translationsIds(List.of("translation_1")).build();
        var text = "It went through";
        EventEntity eventEntity = EventEntity.builder()
                .id("1")
                .translationIds(List.of("translation_1")).build();

        when(eventRepositoryMock.save(any(EventEntity.class)))
                .thenReturn(Mono.just(eventEntity));

        StepVerifier.create(sut.createEvent(eventRequestDto))
                .assertNext(r -> assertThat(r.getTranslationIds().size()))
                .verifyComplete();
    }

    @Test
    @DisplayName("The Response should return the found event.")
    void findEventById_test_should_return_an_event() {
        var eventEntityId = "008";
        EventEntity eventEntity = EventEntity.builder()
                .id(eventEntityId).build();
        when(eventRepositoryMock.findById(eq(eventEntityId))).thenReturn(Mono.just(eventEntity));

        StepVerifier.create(sut.findEventById(eventEntityId))
                .consumeNextWith(r -> assertThat(r.getId()).isEqualTo(eventEntityId))
                .verifyComplete();
    }

    @Test
    @DisplayName("The Response should return all events.")
    void findAllEvents_test_should_return_all_events() {
        EventEntity eventEntity = EventEntity.builder()
                .id("007").build();
        when(eventRepositoryMock.findAll()).thenReturn(Flux.just(eventEntity));

        StepVerifier.create(sut.findAllEvents())
                .expectNextMatches(event -> event.getId().equals("007"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("The Response should return the updated event.")
    void updateEvent_test_should_return_an_updated_event() {
        var eventEntityId1 = "009";
        var eventEntityId2 = "010";
        var text1 = "test2";
        Map<String, String> translations = Map.of("en", "example");

        TranslationRequestDto translation = TranslationRequestDto.builder()
                .language("en")
                .text(text1)
                .build();

        EventRequestDto eventRequestDto = EventRequestDto.builder()
                .translationsIds(List.of("translation_2"))
                .build();
        Mono<EventEntity> originalEventEntity = Mono.just(EventEntity.builder().id(eventEntityId1).build());
        EventEntity updateEventEntity = EventEntity.builder().id(eventEntityId2).build();
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(eventEntityId2);

        when(eventRepositoryMock.findById(anyString())).thenReturn(originalEventEntity);
        when(eventRepositoryMock.save(any())).thenReturn(Mono.just(updateEventEntity));

        StepVerifier.create(sut.updateEvent(eventEntityId1, eventRequestDto))
                .expectNext(eventResponseDto)
                .verifyComplete();
    }

    private EventEntity createTranslations() {

        TranslationEntity translation1 = TranslationEntity.builder()
                .id("translation_1")
                .language("en")
                .text(englishText).build();

        TranslationEntity translation2 = TranslationEntity.builder()
                .id("translation_2")
                .language("zh")
                .text(chineseText)
                .build();

        EventEntity event = EventEntity.builder()
                .id("011")
                .translationIds(List.of(translation1.getId(), translation2.getId()))
                .build();
        return event;
    }
}
