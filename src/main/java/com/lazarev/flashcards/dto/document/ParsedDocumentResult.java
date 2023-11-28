package com.lazarev.flashcards.dto.document;

import com.lazarev.flashcards.dto.element.FlashCardDto;

import java.util.List;

public record ParsedDocumentResult(List<FlashCardDto> flashCards) {}
