package de.kessel.events.util;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.dto.TranslationRequestDto;
import de.kessel.events.dto.TranslationResponseDto;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.TranslationEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityConverter {

    public static EventResponseDto convertToEventResponseDto(EventEntity eventEntity) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(eventEntity.getId());
        eventResponseDto.setText(eventEntity.getText());
        if (Objects.nonNull(eventEntity.getTranslations())) {
            List<TranslationResponseDto> translationResponseDtoList = eventEntity.getTranslations()
                    .stream().map(EntityConverter::convertToTranslationResponseDto)
                    .toList();
            eventResponseDto.setTranslations(translationResponseDtoList);
        }
        return eventResponseDto;
    }

    public static EventEntity convertToEventEntity(EventRequestDto eventRequestDto) {
        List<TranslationEntity> translationEntityList = Collections.emptyList();
        if (Objects.nonNull(eventRequestDto.getTranslations())) {
            translationEntityList = eventRequestDto.getTranslations()
                    .stream().map(EntityConverter::convertToTranslationEntity)
                    .toList();

        }
        return EventEntity.builder()
                .text(eventRequestDto.getText())
                .translations(translationEntityList)
                .build();
    }

    public static TranslationResponseDto convertToTranslationResponseDto(TranslationEntity translationEntity) {
        TranslationResponseDto translationResponseDto = new TranslationResponseDto();
        translationResponseDto.setId(translationEntity.getId());
        translationResponseDto.setLanguage(translationEntity.getLanguage());
        translationResponseDto.setText(translationEntity.getText());
        return translationResponseDto;
    }

    public static TranslationEntity convertToTranslationEntity(TranslationRequestDto translationRequestDto) {
        return TranslationEntity.builder()
                .text(translationRequestDto.getText())
                .language(translationRequestDto.getLanguage())
                .build();
    }
}
