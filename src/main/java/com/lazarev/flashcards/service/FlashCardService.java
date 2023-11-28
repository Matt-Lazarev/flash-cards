package com.lazarev.flashcards.service;

import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.element.DeckFlashCardsDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.entity.FlashCard;
import com.lazarev.flashcards.entity.FlashCardStatistics;
import com.lazarev.flashcards.enums.LearnMode;
import com.lazarev.flashcards.enums.LearnOption;
import com.lazarev.flashcards.enums.WordsOrder;
import com.lazarev.flashcards.enums.WordsSort;
import com.lazarev.flashcards.exception.ElementNotFoundException;
import com.lazarev.flashcards.repository.FlashCardRepository;
import com.lazarev.flashcards.service.mapper.FlashCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashCardService {
    private static final Sort.Order FRONT_FORWARD_SORT_ORDER = new Sort.Order(Sort.Direction.ASC, "frontSide").ignoreCase();
    private static final Sort.Order FRONT_BACKWARD_SORT_ORDER = new Sort.Order(Sort.Direction.DESC, "frontSide").ignoreCase();

    private static final Sort.Order BACK_FORWARD_SORT_ORDER = new Sort.Order(Sort.Direction.ASC, "backSide").ignoreCase();
    private static final Sort.Order BACK_BACKWARD_SORT_ORDER = new Sort.Order(Sort.Direction.DESC, "backSide").ignoreCase();

    private final FlashCardRepository flashCardRepository;
    private final FlashCardMapper flashCardMapper;
    private final DeckService deckService;
    private final FlashCardStatisticsService flashCardStatisticsService;

    public DeckFlashCardsDto getAllFlashCardsByDeckId(Integer deckId) {
        List<FlashCardDto> flashCards = flashCardRepository.findAllByDeckId(deckId, Sort.by(FRONT_FORWARD_SORT_ORDER));
        DeckDto deck = deckService.getDeckById(deckId);
        return new DeckFlashCardsDto(deck.getName(), flashCards);
    }

    public DeckFlashCardsDto getAllFlashCardsByParams(Integer deckId, LearnMode mode,
                                                      WordsSort sort, WordsOrder order,
                                                      LearnOption option) {
        List<FlashCardDto> flashCards = getFlashCards(deckId, mode, order, option);

        if (sort == WordsSort.RANDOM) {
            shuffleFlashCards(flashCards);
        }

        if(option == LearnOption.REVERSE){
            reverseFlashCards(flashCards);
        }

        DeckDto deck = deckService.getDeckById(deckId);
        return new DeckFlashCardsDto(deck.getName(), flashCards);
    }

    public FlashCardDto getFlashCardById(Integer id) {
        FlashCard flashCard = flashCardRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Getting flash-card",
                        "Flash-card with id = '%d' not found".formatted(id))
                );
        return flashCardMapper.toFlashCardDto(flashCard);
    }

    @Transactional
    public void saveFlashCard(FlashCardDto flashCardDto) {
        FlashCard flashCard = flashCardMapper.toFlashCard(flashCardDto);

        deckService.addFlashCardToDeck(flashCardDto.getDeckId(), flashCard);

        flashCardRepository.save(flashCard);
    }

    @Transactional
    public void updateFlashCard(Integer id, FlashCardDto flashCardDto) {
        if (flashCardDto.getIsCorrectAnswer() == null) {
            FlashCard flashCard = flashCardRepository.findFlashCardStatisticsById(id)
                    .orElseThrow(() -> new ElementNotFoundException(
                            "Updating flash-card",
                            "Flash-card with id = '%d' not found".formatted(id))
                    );
            flashCardMapper.updateFlashCard(flashCard, flashCardDto);
            flashCardStatisticsService.resetFlashCardStatistics(flashCard);

            flashCardRepository.save(flashCard);
        } else if (flashCardDto.getMode() == LearnMode.UNSTUDIED_WORDS) {
            updateFlashCardStatistics(id, flashCardDto.getIsCorrectAnswer());
        }
    }

    @Transactional
    public void deleteFlashCard(Integer id) {
        if(!flashCardRepository.existsById(id)){
            throw new ElementNotFoundException(
                    "Deleting flash-card",
                    "Flash-card with id = '%d' not found".formatted(id)
            );
        }
        flashCardRepository.deleteById(id);
    }

    private List<FlashCardDto> getFlashCards(Integer deckId, LearnMode mode, WordsOrder order, LearnOption option) {
        return switch (mode) {
            case ALL_WORDS -> flashCardRepository.findAllByDeckId(deckId, getSort(order, option));
            case UNSTUDIED_WORDS ->
                    flashCardRepository.findAllAfter(deckId, LocalDate.now(), getSort(order, option));
        };
    }

    private Sort getSort(WordsOrder order, LearnOption option) {
        if (order == null) {
            return null;
        }
        if (option != LearnOption.REVERSE) {
            return switch (order) {
                case FORWARD -> Sort.by(FRONT_FORWARD_SORT_ORDER);
                case BACKWARD -> Sort.by(FRONT_BACKWARD_SORT_ORDER);
            };
        }

        return switch (order) {
            case FORWARD -> Sort.by(BACK_FORWARD_SORT_ORDER);
            case BACKWARD -> Sort.by(BACK_BACKWARD_SORT_ORDER);
        };
    }

    private void shuffleFlashCards(List<FlashCardDto> flashCards){
        Collections.shuffle(flashCards);
    }

    private void reverseFlashCards(List<FlashCardDto> flashCards){
        flashCards.forEach(
                flashCard -> {
                    String frontSide = flashCard.getFrontSide();
                    String backSide = flashCard.getBackSide();
                    flashCard.setFrontSide(backSide);
                    flashCard.setBackSide(frontSide);
                }
        );
    }

    private void updateFlashCardStatistics(Integer id, boolean isCorrectAnswer) {
        FlashCard flashCard = flashCardRepository.findFlashCardStatisticsById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Updating flash-card",
                        "Flash-card with id = '%d' not found".formatted(id))
                );
        flashCardStatisticsService.updateFlashCardStatistics(flashCard, isCorrectAnswer);
        flashCardRepository.save(flashCard);
    }
}
