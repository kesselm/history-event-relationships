//package de.kessel.launcher.endpoints.router;
//
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.core.Options;
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import de.kessel.events.dto.EventRequestDto;
//import de.kessel.events.dto.EventResponseDto;
//import de.kessel.events.dto.TranslationRequestDto;
//import de.kessel.events.dto.TranslationResponseDto;
//import de.kessel.launcher.endpoints.handler.implementation.EventHandlerImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//import wiremock.org.eclipse.jetty.http.HttpStatus;
//
//import java.io.IOException;
//import java.util.List;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static de.kessel.launcher.tools.EventConstants.EVENTS_API;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.http.HttpStatus.OK;
//import static wiremock.com.google.common.base.Charsets.UTF_8;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
//@WireMockTest
////@ContextConfiguration(initializers = {WireMockInitializer.class})
//class EventRouteIT {
//
//    @Autowired
//    private WebTestClient webTestClient;
//    @Autowired
//    private EventHandlerImpl eventHandlerImpl;
//    @Autowired
//    private WireMockServer wireMockServer;
//    private EventRequestDto eventRequestDto;
//    private EventResponseDto eventResponseDto;
//    private TranslationResponseDto translationResponseDto;
//    private TranslationRequestDto translationRequestDto;
//
//    @BeforeEach
//    public void setup() {
//        wireMockServer = new WireMockServer();
//        wireMockServer.start();
//        WireMock mockClient = new WireMock("localhost", wireMockServer.port());
//
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
//    @AfterEach
//    void close() {
//        wireMockServer.stop();
//    }
//
//    @Test
//    @DirtiesContext
//    void ddd() throws IOException {
//        //when(eventServiceMock.createEvent(any())).thenReturn(Mono.just(eventResponseDto));
//        var eventRequestDto = new EventRequestDto("test", List.of(translationRequestDto));
//
//        wireMockServer.stubFor(post(urlPathEqualTo(EVENTS_API))
//                .willReturn(aResponse()
//                        .withStatus(OK.value())
//                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
//                        .withBody(new ClassPathResource("createEvent.json").getContentAsString(UTF_8))));
//
//        webTestClient.post().uri(EVENTS_API)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(eventRequestDto), EventRequestDto.class)
//                .exchange()
//                .expectStatus()
//                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR_500);
//
//       // assertThat(response.getText()).isEqualTo("<div class=\"paragraph\">\n<p>test</p>\n</div>");
//    }
//}
