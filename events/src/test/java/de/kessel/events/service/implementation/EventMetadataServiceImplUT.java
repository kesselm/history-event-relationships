package de.kessel.events.service.implementation;

import de.kessel.events.dto.EventMetadataRequestDto;
import de.kessel.events.dto.EventMetadataResponseDto;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.exception.EventNotFoundException;
import de.kessel.events.exception.PropertyValidationException;
import de.kessel.events.model.EventMetadataEntity;
import de.kessel.events.repository.EventMetadataRepository;
import de.kessel.events.util.EventUtil;
import org.apache.commons.lang3.StringUtils;
import org.asciidoctor.Asciidoctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Locale;

import static de.kessel.events.exception.ErrorConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@ExtendWith(OutputCaptureExtension.class)
class EventMetadataServiceImplUT {

    private static final String EVENT_ID = "event_01";

    @MockBean
    private EventMetadataRepository eventMetadataRepositoryMock;

    @MockBean
    private Asciidoctor asciidoctorMock;

    private EventMetadataServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new EventMetadataServiceImpl(eventMetadataRepositoryMock, asciidoctorMock);
    }

    @Test
    @DisplayName("Test for year AD for germany.")
    void test_should_return_just_a_year() {
        var year = EventUtil.createDate(2024, -1, -1, Locale.GERMANY);

        assertThat(year).isEqualTo("2024 n. Chr.");
    }

    @Test
    @DisplayName("Test for year BC for germany.")
    void test_should_return_just_a_negativ_year_in_german() {
        var year = EventUtil.createDate(-210, -1, -1, Locale.GERMANY);

        assertThat(year).isEqualTo("210 v. Chr.");
    }

    @Test
    @DisplayName("Test for year BC for english.")
    void test_should_return_just_a_negativ_year_in_english() {
        var year = EventUtil.createDate(-210, -1, -1, Locale.ENGLISH);

        assertThat(year).isEqualTo("210 BC");
    }

    @Test
    @DisplayName("Test for full date in german.")
    void test_should_return_just_a_negativ_date_in_german() {
        var year = EventUtil.createDate(-210, 3, 12, Locale.GERMAN);

        assertThat(year).isEqualTo("12. April 210 v. Chr.");
    }

    @Test
    @DisplayName("Test for validate EventMetadataRequest creation.")
    void test_should_validate_the_correct_date_and_eventId() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .month(4)
                .day(15)
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).isEqualTo(StringUtils.EMPTY);
    }

    @Test
    @DisplayName("Test for year validation on EventMetadataRequest.")
    void test_should_validate_the_year() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_YEAR_MESSAGE);
    }

    @Test
    @DisplayName("Test for day validation on EventMetadataRequest.")
    void test_should_validate_the_day_range() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .day(32)
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_MAX_MESSAGE);
    }

    @Test
    @DisplayName("Test for day validation on EventMetadataRequest.")
    void test_should_validate_the_day_if_positive() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .day(-1)
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_MIN_MESSAGE);
    }

    @Test
    @DisplayName("Test for month validation ond EventMetadataRequest.")
    void test_should_validate_the_month_range() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .month(13)
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_MAX_MESSAGE);
    }

    @Test
    @DisplayName("Test for month validation on EventMetadataRequest.")
    void test_should_validate_the_month_if_positive() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .month(-1)
                .eventId(EVENT_ID)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_MIN_MESSAGE);
    }

    @Test
    @DisplayName("Test for eventId validation on EventMetadataRequest.")
    void test_should_validate_eventId_if_not_set() {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .build();

        assertThat(eventMetadataRequestDto.validate()).contains(VALIDATION_TRANSLATION_ID_MESSAGE);
    }

    @Test
    @DisplayName("Test for EventMetadataRequest creation.")
    void test_should_create_eventMetadata_object_without_error() {
        var eventMetadataEntity = EventMetadataEntity.builder().build();
        eventMetadataEntity.setYear(2024);
        eventMetadataEntity.setDay(0);
        eventMetadataEntity.setMonth(0);
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .eventId(EVENT_ID)
                .build();

        when(eventMetadataRepositoryMock.save(any())).thenReturn(Mono.just(eventMetadataEntity));
        when(asciidoctorMock.convert(anyString(), any())).thenReturn(StringUtils.EMPTY);

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .consumeNextWith(event -> assertThat(event.getId()).contains("eventMetadata"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest for year.")
    void test_should_log_error_on_eventMetadata_creation_for_no_year(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .eventId(EVENT_ID)
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_YEAR_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_YEAR.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_YEAR.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest for eventId.")
    void test_should_log_error_on_eventMetadata_creation_for_no_eventId(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_TRANSLATION_ID_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_TRANSLATION_ID.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_TRANSLATION_ID.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest on day.")
    void test_should_log_error_on_eventMetadata_creation_for_negative_day(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .day(-2)
                .eventId(EVENT_ID)
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_MIN_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_MIN.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_MIN.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest on day.")
    void test_should_log_error_on_eventMetadata_creation_for_wrong_day(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .day(32)
                .eventId(EVENT_ID)
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_MAX_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_MAX.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_MAX.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest on month.")
    void test_should_log_error_on_eventMetadata_creation_for_wrong_month(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .month(32)
                .eventId("event-003")
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_MAX_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_MAX.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_MAX.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error logging on EventMetadataRequest on month.")
    void test_should_log_error_on_eventMetadata_creation_for_negativ_month(CapturedOutput output) {
        var eventMetadataRequestDto = EventMetadataRequestDto.builder()
                .year(2024)
                .month(-1)
                .eventId(EVENT_ID)
                .build();

        StepVerifier.create(sut.createEventMetadata(eventMetadataRequestDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(PropertyValidationException.class);
                    assertThat(error.getMessage()).isEqualTo(VALIDATION_MIN_MESSAGE);
                })
                .verify();

        assertThat(output.getOut()).contains(ErrorDetail.VALIDATION_MIN.getErrorCode() + " - "
                + ErrorDetail.VALIDATION_MIN.getErrorMessage());
    }

    @Test
    @DisplayName("Test for error on find EventMetadata by id.")
    void findEventMetadataById_should_return_error() {
        when(eventMetadataRepositoryMock.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(sut.findEventMetadataById("007"))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EventNotFoundException.class);
                }).verify();
    }

    @Test
    @DisplayName("Test for error on find all EventMetadata.")
    void findAllEventMetadata_should_return_error() {
        when(eventMetadataRepositoryMock.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(sut.findAllEventMetadata())
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EventNotFoundException.class);
                }).verify();
    }

    @Test
    @DisplayName("Test for error on find all EventMetadata asc sorted by Year.")
    void findAllEventMetadataByYearAsc_should_return_an_error() {
        when(eventMetadataRepositoryMock.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(sut.findAllByOrderByYearAsc())
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EventNotFoundException.class);
                }).verify();
    }

    @Test
    @DisplayName("Test for error on find all EventMetadata desc sorted by Year.")
    void findAllEventMetadataByYearDesc_should_return_an_error() {
        when(eventMetadataRepositoryMock.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(sut.findAllByOrderByYearDesc())
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EventNotFoundException.class);
                }).verify();
    }
}
