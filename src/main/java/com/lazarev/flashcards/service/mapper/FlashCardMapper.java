package com.lazarev.flashcards.service.mapper;

import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.entity.FlashCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {
    List<FlashCardDto> toFlashCardDtoList(List<FlashCard> flashCardList);

    FlashCardDto toFlashCardDto(FlashCard flashCard);

    FlashCard toFlashCard(FlashCardDto flashCardDto);

    @Mapping(target = "id", ignore = true)
    void updateFlashCard(@MappingTarget FlashCard flashCard, FlashCardDto flashCardDto);
}
