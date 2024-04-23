package de.kessel.events.util;

import de.kessel.events.dto.*;
import de.kessel.events.model.EventEntity;
import de.kessel.events.model.EventMetadataEntity;
import de.kessel.events.model.TranslationEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityMapper
{

    public static EventResponseDto convertToEventResponseDto(EventEntity eventEntity) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(eventEntity.getId());
        eventResponseDto.setTranslationIds(eventEntity.getTranslationIds());
        return eventResponseDto;
    }

    public static EventEntity convertToEventEntity(EventRequestDto eventRequestDto) {
        return EventEntity.builder()
                .translationIds(eventRequestDto.getTranslationsIds())
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

    public static EventMetadataEntity convertToEventMetadataEntity(EventMetadataRequestDto eventMetadataRequestDto) {
        return EventMetadataEntity.builder()
                .personId(eventMetadataRequestDto.getPersonId())
                .locationId(eventMetadataRequestDto.getLocationId())
                .scope(eventMetadataRequestDto.getScope())
                .status(eventMetadataRequestDto.getStatus())
                .topicId(eventMetadataRequestDto.getTopicId())
                .reference(eventMetadataRequestDto.getReference())
                .year(eventMetadataRequestDto.getYear())
                .month(eventMetadataRequestDto.getMonth())
                .day(eventMetadataRequestDto.getDay())
                .alternativeDateString(eventMetadataRequestDto.getAlternativeDateString())
                .eventId(eventMetadataRequestDto.getEventId())
                .comment(eventMetadataRequestDto.getComment())
                .build();
    }

    public static EventMetadataResponseDto convertToEventMetadataResponseDto(EventMetadataEntity eventMetadataEntity){
        var eventMetadataResponseDto = new EventMetadataResponseDto();
        eventMetadataResponseDto.setId(eventMetadataEntity.getId());
        eventMetadataResponseDto.setPersonId(eventMetadataEntity.getPersonId());
        eventMetadataResponseDto.setLocationId(eventMetadataEntity.getLocationId());
        eventMetadataResponseDto.setScope(eventMetadataEntity.getScope());
        eventMetadataResponseDto.setStatus(eventMetadataEntity.getStatus());
        eventMetadataResponseDto.setTopicId(eventMetadataEntity.getTopicId());
        eventMetadataResponseDto.setReference(eventMetadataEntity.getReference());
        eventMetadataResponseDto.setDate(EventUtil.createDate(eventMetadataEntity.getYear(),
                eventMetadataEntity.getMonth(),
                eventMetadataEntity.getDay(), Locale.GERMAN));
        eventMetadataResponseDto.setEventId(eventMetadataEntity.getEventId());
        eventMetadataResponseDto.setComment(eventMetadataEntity.getComment());
        return eventMetadataResponseDto;
    }
}
