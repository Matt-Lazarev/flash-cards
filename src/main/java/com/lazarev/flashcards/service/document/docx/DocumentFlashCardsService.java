package com.lazarev.flashcards.service.document.docx;

import com.lazarev.flashcards.dto.document.DocumentDto;
import com.lazarev.flashcards.dto.document.ParsedDocumentResult;
import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.dto.element.GroupDecksDto;
import com.lazarev.flashcards.service.DeckService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentFlashCardsService {
    private final DocxDocumentCreator docxDocumentCreator;
    private final DocxDocumentParser docxDocumentParser;
    private final DeckService deckService;

    public DocumentDto createDocumentFromDeck(Integer deckId){
        DeckDto deck = deckService.getDeckFlashCardsById(deckId);
        List<FlashCardDto> flashCards = deck.getFlashCards();
        return docxDocumentCreator.create(deck.getName(), flashCards);
    }

    public DocumentDto createZipDocumentFromGroup(Integer groupId){
        GroupDecksDto groupDecks = deckService.getGroupFlashCardsByGroupId(groupId);
        List<DeckDto> decks = groupDecks.decks();
        List<String> deckNames = decks.stream().map(DeckDto::getName).toList();
        List<List<FlashCardDto>> groupFlashCards = decks.stream().map(DeckDto::getFlashCards).toList();
        return docxDocumentCreator.createZip(groupDecks.groupName(), deckNames, groupFlashCards);
    }

    @SneakyThrows
    public void parseDocumentFlashCards(DeckDto deck, MultipartFile file){
        InputStream inputStream = file.getInputStream();
        ParsedDocumentResult parsedDocument = docxDocumentParser.parse(inputStream);

        deck.setFlashCards(parsedDocument.flashCards());
        deckService.saveDeck(deck);
    }
}
