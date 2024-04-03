package de.kessel.launcher.endpoints;


import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.model.CustomErrorResponse;
import de.kessel.events.model.ErrorDetail;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.Translation;
import de.kessel.events.service.implementation.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static de.kessel.launcher.utils.EventConstants.EVENTS_API;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EventRouter.class)
@ComponentScan(basePackages = {"de.kessel.events"})
@AutoConfigureWebTestClient
@ExtendWith(OutputCaptureExtension.class)
class EventRouterUT {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private EventServiceImpl eventServiceMock;
    private EventEntity testEventEntity;
    private EventRequestDto eventRequestDto;
    private Translation translation;


    @BeforeEach
    public void setup() {
        translation = Translation.builder()
                .language("en")
                .text("Hello World!")
                .build();
        testEventEntity = EventEntity.builder()
                .id("1")
                .text("Hallo Welt")
                .translations(List.of(translation))
                .build();
        eventRequestDto = EventRequestDto.builder()
                .text("Hallo Welt!")
                .translations(List.of(translation))
                .build();
    }

    @Test
    @DisplayName("Event could be created.")
    void createEvent_test_should_return_is_created_status() {
        when(eventServiceMock.createEvent(any())).thenReturn(Mono.just(testEventEntity));

        webTestClient.post().uri(EVENTS_API)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(eventRequestDto), EventRequestDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    @DisplayName("Event could not be created.")
    void createEvent_test_should_return_is_bad_request_status(CapturedOutput output) {
        var wrongEventRequestDto = new EventRequestDto("", null);

        when(eventServiceMock.createEvent(any())).thenReturn(Mono.just(testEventEntity));

        webTestClient.post().uri("/api/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(wrongEventRequestDto), EventRequestDto.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").isEqualTo(ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage());

        assertThat(output.getOut()).contains(ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage());
    }

    @Test
    @DisplayName("List of Events is shown.")
    void findAll_test_should_return_is_ok_status() {
        when(eventServiceMock.getAllEvents()).thenReturn(Flux.just(testEventEntity));

        webTestClient.get()
                .uri(EVENTS_API)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventRequestDto.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("No Event is found.")
    void findAll_test_should_return_no_content_status() {
        when(eventServiceMock.getAllEvents()).thenReturn(Flux.empty());

        webTestClient.get()
                .uri(EVENTS_API)
                .exchange()
                .expectStatus().isNoContent()
                .expectBodyList(EventRequestDto.class)
                .hasSize(0);
    }

    @Test
    @DisplayName("Event by Id should be returned.")
    void findById_test_should_return_is_ok_status() {
        when(eventServiceMock.getEventById(any())).thenReturn(Mono.just(testEventEntity));

        webTestClient.get().uri(EVENTS_API + "/{id}", "007")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    @DisplayName("Event could not be found.")
    void findById_test_should_return_no_content_status() {

        String errorMessage = new StringBuilder(ErrorDetail.EVENT_NOT_FOUND.getErrorCode())
                .append(" - ").append(String.format(ErrorDetail.EVENT_NOT_FOUND.getErrorMessage(), "007")).toString();

        CustomErrorResponse customErrorResponse = CustomErrorResponse
                .builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().now())
                .status(HttpStatus.NO_CONTENT)
                .errors(List.of(ErrorDetail.EVENT_NOT_FOUND))
                .build();

        when(eventServiceMock.getEventById(any())).thenThrow(new EventNotFoundException(errorMessage, customErrorResponse));
        webTestClient.get().uri(EVENTS_API + "/{id}", "007")
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    @DisplayName("Event should be updated.")
    void update_test_should_return_http_status_ok() {
        when(eventServiceMock.updateEvent(any(), any())).thenReturn(Mono.just(testEventEntity));

        webTestClient.put().uri(EVENTS_API + "/{id}", "id2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(testEventEntity), EventRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    @DisplayName("Event should be updated.")
    void update_test_should_return_http_status_ok2() {
        when(eventServiceMock.updateEvent(any(), any())).thenReturn(Mono.just(testEventEntity));
        var id = "id2";
        var message = ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorMessage();

        var error = new StringBuilder(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorCode())
                .append(" - ")
                .append(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorMessage()).toString();

        webTestClient.put().uri(EVENTS_API + "/{id}", id)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").isEqualTo(message)
                .jsonPath("$.error").isNotEmpty()
                .jsonPath("$.error").isEqualTo(error)
                .jsonPath("$.code").isNotEmpty()
                .jsonPath("$.code").isEqualTo(ErrorDetail.REQUEST_BODY_IS_MISSING.getErrorCode());
    }

    @Test
    public void testDeleteEvent() {
        when(eventServiceMock.deleteEvent(Mockito.anyString())).thenReturn(Mono.empty());

        webTestClient.delete().uri(EVENTS_API + "/" + testEventEntity.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(eventServiceMock).deleteEvent(Mockito.anyString());
    }

//    @Test
//    public void testGetSingleTranslation() {
//        String lang = "en";
//        String translation = "Test translation";
//        Mockito.when(eventServiceMock.getSingleTranslation(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(translation));
//
//        webTestClient.get().uri("/api/v1/events" + testEvent.getId() + "/translations/" + lang)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class).isEqualTo(translation);
//
//        Mockito.verify(eventServiceImpl).getSingleTranslation(Mockito.anyString(), Mockito.anyString());
//    }

}
