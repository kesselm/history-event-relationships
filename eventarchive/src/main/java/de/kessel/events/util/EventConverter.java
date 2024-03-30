package de.kessel.events.util;

import de.kessel.events.dto.EventRequestDto;
import de.kessel.events.dto.EventResponseDto;
import de.kessel.events.model.EventEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventConverter {

    public static EventResponseDto convertToResponseDto(EventEntity eventEntity){
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(eventEntity.getId());
        eventResponseDto.setText(eventEntity.getText());
        eventResponseDto.setTranslations(eventEntity.getTranslations());
        return eventResponseDto;
    }

    public static EventEntity convertToEventEntity(EventRequestDto eventRequestDto){
        return EventEntity.builder()
                .text(eventRequestDto.getText())
                .translations(eventRequestDto.getTranslations())
                .build();
    }
}
