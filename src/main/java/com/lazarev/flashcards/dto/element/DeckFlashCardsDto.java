package com.lazarev.flashcards.dto.element;

import java.util.List;

public record DeckFlashCardsDto(
        String deckName,
        List<FlashCardDto> flashCards
) {}
