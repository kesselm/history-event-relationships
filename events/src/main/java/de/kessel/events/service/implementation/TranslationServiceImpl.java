package de.kessel.events.service.implementation;

import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import de.kessel.events.exception.ErrorDetail;
import de.kessel.events.exception.PropertyValidationException;
import de.kessel.events.exception.TranslationNotFoundException;
import de.kessel.events.model.TranslationEntity;
import de.kessel.events.util.EntityMapper;
import de.kessel.events.util.ErrorMessageUtil;
import de.kessel.events.repository.TranslationRepository;
import de.kessel.events.service.TranslationService;
import de.kessel.events.util.EventUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final Asciidoctor asciidoctor;

    @Override
    public Mono<TranslationResponseDto> createTranslation(TranslationRequestDto translationRequestDto) {
        String validationMessage = translationRequestDto.validate();
        if (StringUtils.isNotEmpty(validationMessage)) {
            Optional<ErrorDetail> errorDetail = EventUtil.getErrorDetailFromErrorMessage(validationMessage);
            if (errorDetail.isPresent()) {
                log.error(errorDetail.get().getErrorCode() + " - " + errorDetail.get().getErrorMessage());
                return Mono.error(new PropertyValidationException(errorDetail.get().getErrorMessage(),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NOT_ACCEPTABLE,
                                errorDetail.get())));
            }
        }

        TranslationEntity translationEntity = EntityMapper.convertToTranslationEntity(translationRequestDto);
        String htmlContent = asciidoctor.convert(translationRequestDto.getText(), Options.builder().build());
        translationEntity.setText(htmlContent);
        translationEntity.setLanguage(translationRequestDto.getLanguage());
        Mono<TranslationEntity> savedEventEntity = translationRepository.save(translationEntity);
        return savedEventEntity.map(EntityMapper::convertToTranslationResponseDto);
    }

    @Override
    public Mono<TranslationResponseDto> findTranslationById(String id) {
        return translationRepository.findById(id)
                .map(EntityMapper::convertToTranslationResponseDto)
                .switchIfEmpty(Mono.error(new TranslationNotFoundException(ErrorMessageUtil.getErrorMessage(id,
                        ErrorDetail.TRANSLATION_NOT_FOUND),
                        ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NO_CONTENT,
                                ErrorDetail.TRANSLATION_NOT_FOUND))));
    }

    @Override
    public Flux<TranslationResponseDto> findAllTranslation() {
        return translationRepository.findAll().map(EntityMapper::convertToTranslationResponseDto);
    }

    @Override
    public Flux<TranslationResponseDto> findAllTranslationsAndPagination(Pageable pageable) {
        return translationRepository.findAllBy(pageable)
                .map(EntityMapper::convertToTranslationResponseDto);
    }

    @Override
    public Mono<TranslationResponseDto> updateTranslation(String id, TranslationRequestDto translationRequestDto) {
        return translationRepository.findById(id)
                .flatMap(dbEvent -> {
                    dbEvent.setText(translationRequestDto.getText());
                    dbEvent.setLanguage(translationRequestDto.getLanguage());
                    return translationRepository.save(dbEvent).map(EntityMapper::convertToTranslationResponseDto);
                }).switchIfEmpty(Mono.error(new TranslationNotFoundException(ErrorMessageUtil.getErrorMessage(id,
                        ErrorDetail.TRANSLATION_NOT_FOUND),
                ErrorMessageUtil.getCustomErrorResponse(HttpStatus.NO_CONTENT,
                        ErrorDetail.TRANSLATION_NOT_FOUND))));
    }

    @Override
    public Mono<Void> deleteTranslationById(String id) {
        return translationRepository.deleteById(id);
    }

}
