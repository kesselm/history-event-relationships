//package de.kessel.launcher.endpoints.router;
//
//import de.kessel.events.dto.EventRequestDto;
//import de.kessel.events.dto.EventResponseDto;
//import de.kessel.events.dto.TranslationRequestDto;
//import de.kessel.events.dto.TranslationResponseDto;
//import de.kessel.events.exception.CustomErrorResponse;
//import de.kessel.events.exception.ErrorDetail;
//import de.kessel.events.exception.EventNotFoundException;
//import de.kessel.events.service.implementation.EventServiceImpl;
//import de.kessel.launcher.endpoints.handler.implementation.EventHandlerImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.system.CapturedOutput;
//import org.springframework.boot.test.system.OutputCaptureExtension;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static de.kessel.events.exception.ErrorDetail.EVENT_NOT_FOUND;
//import static de.kessel.events.exception.ErrorDetail.REQUEST_BODY_IS_MISSING;
//import static de.kessel.launcher.tools.EventConstants.*;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest
//@ComponentScan(basePackages = {"de.kessel.events.*"})
//@AutoConfigureWebTestClient
//@ExtendWith(OutputCaptureExtension.class)
//class EventRouterUT {
//
//    @Autowired
//    private WebTestClient webTestClient;
//    @Autowired
//    private EventHandlerImpl eventHandlerImpl;
//    @MockBean
//    private EventServiceImpl eventServiceMock;
//    private EventRequestDto eventRequestDto;
//    private EventResponseDto eventResponseDto;
//    private TranslationResponseDto translationResponseDto;
//    private TranslationRequestDto translationRequestDto;
//
//    @BeforeEach
//    public void setup() {
//        // Request
//        translationRequestDto = TranslationRequestDto.builder()
//                .language("en")
//                .text("Hello World")
//                .build();
//        eventRequestDto = EventRequestDto.builder()
//                .text("Hallo Welt!")
//                .translations(List.of(translationRequestDto))
//                .build();
//
//        // Response
//        translationResponseDto = new TranslationResponseDto();
//        translationResponseDto.setLanguage("en");
//        translationResponseDto.setText("Hello World!");
//
//        eventResponseDto = new EventResponseDto();
//        eventResponseDto.setId("1");
//        eventResponseDto.setText("Test Text");
//        eventResponseDto.setTranslations(List.of(translationResponseDto));
//    }
//
//    @Test
//    @DisplayName("Event could be created.")
//    void createEvent_test_should_return_is_created_status() {
//        when(eventServiceMock.createEvent(any())).thenReturn(Mono.just(eventResponseDto));
//
//        webTestClient.post().uri(EVENTS_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(eventRequestDto), EventRequestDto.class)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody()
//                .jsonPath("$.id").isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("Event could not be created.")
//    void createEvent_test_should_return_is_bad_request_status(CapturedOutput output) throws IOException {
//        var wrongEventRequestDto = new EventRequestDto("", null);
//
//        when(eventServiceMock.createEvent(any())).thenReturn(Mono.just(eventResponseDto));
//
//        webTestClient.post().uri(EVENTS_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(wrongEventRequestDto), EventRequestDto.class)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody()
//                .jsonPath("$.message").isNotEmpty()
//                .jsonPath("$.message").isEqualTo(ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage());
//
//        assertThat(output.getOut()).contains(ErrorDetail.REQUEST_MISSING_PROPERTY.getErrorMessage());
//    }
//
//    @Test
//    @DisplayName("List of Events is shown.")
//    void findAll_test_should_return_is_ok_status() {
//        when(eventServiceMock.findAllEvents()).thenReturn(Flux.just(eventResponseDto));
//
//        webTestClient.get()
//                .uri(EVENTS_API)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(EventRequestDto.class)
//                .hasSize(1);
//    }
//
//    @Test
//    @DisplayName("No Event is found.")
//    void findAll_test_should_return_no_content_status() {
//        when(eventServiceMock.findAllEvents()).thenReturn(Flux.empty());
//
//        webTestClient.get()
//                .uri(EVENTS_API)
//                .exchange()
//                .expectStatus().isNoContent()
//                .expectBodyList(EventRequestDto.class)
//                .hasSize(0);
//    }
//
//    @Test
//    @DisplayName("Event by Id should be returned.")
//    void findById_test_should_return_is_ok_status() {
//        when(eventServiceMock.findEventById(any())).thenReturn(Mono.just(eventResponseDto));
//
//        webTestClient.get().uri(EVENTS_API + "/{id}", "007")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.id").isNotEmpty()
//                .jsonPath("$.id").isEqualTo("1");
//    }
//
//    @Test
//    @DisplayName("Event could not be found.")
//    void findById_test_should_return_no_content_status() {
//
//        String errorMessage = new StringBuilder(EVENT_NOT_FOUND.getErrorCode())
//                .append(" - ").append(String.format(EVENT_NOT_FOUND.getErrorMessage(), "007")).toString();
//
//        CustomErrorResponse customErrorResponse = CustomErrorResponse
//                .builder()
//                .traceId(UUID.randomUUID().toString())
//                .timestamp(OffsetDateTime.now().now())
//                .status(HttpStatus.NO_CONTENT)
//                .errors(List.of(EVENT_NOT_FOUND))
//                .build();
//
//        when(eventServiceMock.findEventById(any())).thenThrow(new EventNotFoundException(errorMessage, customErrorResponse));
//        webTestClient.get().uri(EVENTS_API + "/{id}", "007")
//                .exchange()
//                .expectStatus().isNoContent();
//    }
//
//    @Test
//    @DisplayName("Event should be updated.")
//    void update_test_should_return_http_status_ok() {
//        when(eventServiceMock.updateEvent(any(), any())).thenReturn(Mono.just(eventResponseDto));
//
//        webTestClient.put().uri(EVENTS_API + "/{id}", "id2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(eventRequestDto), EventRequestDto.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.id").isNotEmpty()
//                .jsonPath("$.id").isEqualTo("1");
//    }
//
//    @Test
//    @DisplayName("Event should be updated.")
//    void update_test_should_return_http_status_ok2() {
//        when(eventServiceMock.updateEvent(any(), any())).thenReturn(Mono.just(eventResponseDto));
//        var id = "id2";
//        var message = REQUEST_BODY_IS_MISSING.getErrorMessage();
//
//        var error = new StringBuilder(REQUEST_BODY_IS_MISSING.getErrorCode())
//                .append(" - ")
//                .append(REQUEST_BODY_IS_MISSING.getErrorMessage()).toString();
//
//        webTestClient.put().uri(EVENTS_API + "/{id}", id)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectBody()
//                .jsonPath("$.message").isNotEmpty()
//                .jsonPath("$.message").isEqualTo(message)
//                .jsonPath("$.error").isNotEmpty()
//                .jsonPath("$.error").isEqualTo(error)
//                .jsonPath("$.code").isNotEmpty()
//                .jsonPath("$.code").isEqualTo(REQUEST_BODY_IS_MISSING.getErrorCode());
//    }
//
//    @Test
//    void testDeleteEvent() {
//        when(eventServiceMock.deleteEventById(anyString())).thenReturn(Mono.empty());
//
//        webTestClient.delete().uri(EVENTS_API + "/" + eventResponseDto.getId())
//                .exchange()
//                .expectStatus().isOk();
//
//        verify(eventServiceMock).deleteEventById(anyString());
//    }
//
//    @Test
//    void testGetSingleTranslation() {
//        String lang = "en";
//        String translation = "Test translation";
//        Mockito.when(eventServiceMock.getSingleTranslation(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(translationResponseDto));
//
//        webTestClient.get().uri(uriBuilder -> uriBuilder
//                        .path(TRANSLATION_API)
//                        .queryParam("eventId", "007")
//                        .queryParam("language", "de")
//                        .build())
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(TranslationResponseDto.class);
//
//        Mockito.verify(eventServiceMock).getSingleTranslation(Mockito.anyString(), Mockito.anyString());
//    }
//}
