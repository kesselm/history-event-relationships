package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.Translation;
import de.kessel.events.repository.EventRepository;
import de.kessel.events.service.EventService;
import org.asciidoctor.Asciidoctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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

    private Translation translation;

    @BeforeEach
    public void setup() {
        sut = new EventServiceImpl(eventRepositoryMock, asciidoctorMock);
        translation = Translation.builder()
                .language("en")
                .text("Hello World!")
                .build();
    }

    @Test
    void createEvent_test_should_return_a_created_event() {
        EventRequestDto eventRequestDto = EventRequestDto.builder()
                .text("*abc*")
                .translations(List.of(translation)).build();
        var text = "It went through";
        EventEntity eventEntity = EventEntity.builder()
                .id("1")
                .text(text)
                .translations(List.of(translation)).build();

        when(eventRepositoryMock.save(any(EventEntity.class)))
                .thenReturn(Mono.just(eventEntity));

        StepVerifier.create(sut.createEvent(eventRequestDto))
                .consumeNextWith(r -> r.getText().contains(text))
                .verifyComplete();
    }

//    @Test
//    void eventById_test_should_return_an_event() {
//        var eventEntityId = "008";
//        var text = "test1";
//        EventEntity eventEntity = EventEntity.builder()
//                .id(eventEntityId).text(text).build();
//        when(eventRepositoryMock.findById(eq(eventEntityId))).thenReturn(Mono.just(eventEntity));
//
//        StepVerifier.create(sut.getEventById(eventEntityId))
//                .consumeNextWith(r -> assertThat(r.getText()).isEqualTo(text))
//                .verifyComplete();
//    }

//    @Test
//    void updateEvent_test_should_return_an_updated_event() {
//        var eventEntityId1 = "009";
//        var eventEntityId2 = "010";
//        var text1 = "test2";
//        var text2 = "test3";
//        Map<String, String> translations = Map.of("en", "example");
//
//        Translation translation = Translation.builder()
//                .language("en")
//                .text(text1)
//                .build();
//
//        EventRequestDto eventRequestDto = new EventRequestDto(text1, List.of(translation));
//        Mono<EventEntity> originalEventEntity = Mono.just(EventEntity.builder().text(text1).id(eventEntityId1).build());
//        EventEntity updateEventEntity = EventEntity.builder().text(text2).id(eventEntityId2).build();
//
//        when(eventRepositoryMock.findById(anyString())).thenReturn(originalEventEntity);
//        when(eventRepositoryMock.save(any())).thenReturn(Mono.just(updateEventEntity));
//
//        StepVerifier.create(sut.updateEvent(eventEntityId1, eventRequestDto))
//                .expectNext(updateEventEntity)
//                .verifyComplete();
//    }

//    @Test
//    void shouldGetSingleTranslation() {
//
//        when(eventRepositoryMock.findById(eq("011"))).thenReturn(Mono.just(createTranslations()));
//
//        StepVerifier.create(sut.getSingleTranslation("011", "en"))
//                .consumeNextWith(e -> assertThat(e.getText()).isEqualTo(englishText))
//                .verifyComplete();
//    }

//    private EventEntity createTranslations() {
//
//        Translation translation1 = Translation.builder()
//                .language("en")
//                .text(englishText).build();
//
//        Translation translation2 = Translation.builder()
//                .language("zh")
//                .text(chineseText)
//                .build();
//
//        EventEntity event = EventEntity.builder()
//                .id("011")
//                .text("Wie geht es dir?")
//                .translations(List.of(translation1, translation2))
//                .build();
//        return event;
//    }
}
