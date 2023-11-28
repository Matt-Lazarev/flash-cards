package com.lazarev.flashcards.dto.element;

import java.util.List;

public record GroupDecksDto (
        String groupName,
        List<DeckDto> decks
) {}
