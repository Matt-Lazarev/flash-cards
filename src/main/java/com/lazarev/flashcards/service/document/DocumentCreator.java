package com.lazarev.flashcards.service.document;

import com.lazarev.flashcards.dto.document.DocumentDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;

import java.util.List;

public interface DocumentCreator {
    DocumentDto create(String documentName, List<FlashCardDto> data);

    DocumentDto createZip(String documentName, List<String> elementsNames, List<List<FlashCardDto>> data);
}
