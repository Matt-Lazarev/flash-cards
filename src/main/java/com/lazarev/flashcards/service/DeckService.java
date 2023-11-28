package com.lazarev.flashcards.service;

import com.lazarev.flashcards.dto.element.*;
import com.lazarev.flashcards.entity.Deck;
import com.lazarev.flashcards.entity.FlashCard;
import com.lazarev.flashcards.exception.ElementNotFoundException;
import com.lazarev.flashcards.repository.DeckRepository;
import com.lazarev.flashcards.service.mapper.DeckMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {
    private static final Sort DECKS_SORT = Sort.by("name");

    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;
    private final GroupService groupService;

    public GroupDecksDto getAllDeckByGroupId(Integer groupId) {
        GroupDto group = groupService.getGroupById(groupId);
        List<DeckDto> decks = deckRepository.findAllByGroupId(groupId, DECKS_SORT);
        return new GroupDecksDto(group.name(), decks);
    }

    public DeckDto getDeckById(Integer id) {
        return deckRepository.findDeckById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Getting deck",
                        "Deck with id = '%d' not found".formatted(id))
                );
    }

    public DeckDto getDeckFlashCardsById(Integer id) {
        Deck deck = deckRepository.findDeckFlashCardsById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Getting deck",
                        "Deck with id = '%d' not found".formatted(id))
                );
        return deckMapper.toDeckDto(deck);
    }

    public GroupDecksDto getGroupFlashCardsByGroupId(Integer groupId) {
        GroupDto group = groupService.getGroupById(groupId);
        List<Deck> decks = deckRepository.findGroupFlashCardsById(groupId);
        List<DeckDto> deckDtos = deckMapper.toDeckDtoList(decks);
        return new GroupDecksDto(group.name(), deckDtos);
    }

    @SneakyThrows
    @Transactional
    public void saveDeck(DeckDto deckDto) {
        Deck deck = deckMapper.toDeck(deckDto);

        groupService.addDeckToGroup(deckDto.getGroupId(), deck);

        deckRepository.save(deck);
    }

    public void addFlashCardToDeck(Integer deckId, FlashCard flashCard) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Adding flash-card to deck",
                        "Deck with id = '%d' not found".formatted(deckId))
                );
        deck.addFlashCard(flashCard);
    }

    @Transactional
    public void updateDeck(Integer id, DeckDto deckDto) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Updating deck",
                        "Deck with id = '%d' not found".formatted(id))
                );

        deckMapper.updateDeck(deck, deckDto);
    }

    @Transactional
    public void deleteDeckById(Integer id) {
        if(!deckRepository.existsById(id)) {
            throw new ElementNotFoundException(
                    "Deleting deck",
                    "Deck with id = '%d' not found".formatted(id)
            );
        }
        deckRepository.deleteById(id);
    }
}
