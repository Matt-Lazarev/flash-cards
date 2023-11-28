package com.lazarev.flashcards.service.mapper;

import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.entity.Deck;
import com.lazarev.flashcards.entity.FlashCard;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeckMapper {
    List<DeckDto> toDeckDtoList(List<Deck> deckList);

    DeckDto toDeckDto(Deck deck);

    @Mapping(target = "flashCards", source="flashCards", qualifiedByName = "flashCardsMapper")
    Deck toDeck(DeckDto deckDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flashCards", ignore = true)
    void updateDeck(@MappingTarget Deck deck, DeckDto deckDto);

    List<FlashCard> toDeckList(List<FlashCardDto> flashCardDtoList);

    @Named("flashCardsMapper")
    default List<FlashCard> jsonCustomMapper(List<FlashCardDto> flashCards) {
        if(flashCards == null){
            return List.of();
        }
        return toDeckList(flashCards);
    }
}
