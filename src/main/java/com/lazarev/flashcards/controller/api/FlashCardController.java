package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.dto.element.DeckFlashCardsDto;
import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.dto.common.OperationResponse;
import com.lazarev.flashcards.enums.LearnMode;
import com.lazarev.flashcards.enums.LearnOption;
import com.lazarev.flashcards.enums.WordsOrder;
import com.lazarev.flashcards.enums.WordsSort;
import com.lazarev.flashcards.service.FlashCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flash-cards")
public class FlashCardController {
    private final FlashCardService flashCardService;

    @GetMapping(params = "deckId")
    @PreAuthorize("@userAccessChecker.checkDeck(#deckId)")
    public ResponseEntity<DeckFlashCardsDto> getAllFlashCardsByDeckId(@RequestParam Integer deckId){
        DeckFlashCardsDto flashCards = flashCardService.getAllFlashCardsByDeckId(deckId);
        return ResponseEntity.ok(flashCards);
    }

    @GetMapping(params = {"deckId", "mode", "sort", "order"})
    @PreAuthorize("@userAccessChecker.checkDeck(#deckId)")
    public ResponseEntity<DeckFlashCardsDto> getAllFlashCardsByParams(@RequestParam Integer deckId,
                                                                      @RequestParam LearnMode mode,
                                                                      @RequestParam WordsSort sort,
                                                                      @RequestParam(required = false) WordsOrder order,
                                                                      @RequestParam(required = false) LearnOption option){
        DeckFlashCardsDto flashCards = flashCardService.getAllFlashCardsByParams(deckId, mode, sort, order, option);
        return ResponseEntity.ok(flashCards);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkFlashCard(#id)")
    public ResponseEntity<FlashCardDto> getFlashCardById(@PathVariable Integer id){
        FlashCardDto flashCard = flashCardService.getFlashCardById(id);
        return ResponseEntity.ok(flashCard);
    }

    @PostMapping
    @PreAuthorize("@userAccessChecker.checkDeck(#flashCardDto.getDeckId())")
    public ResponseEntity<OperationResponse> saveFlashCard(@RequestBody FlashCardDto flashCardDto){
        flashCardService.saveFlashCard(flashCardDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OperationResponse("Saving a new flash-card", "success"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkFlashCard(#id)")
    public ResponseEntity<OperationResponse> updateFlashCard(@PathVariable Integer id,
                                                             @RequestBody FlashCardDto flashCardDto){
        flashCardService.updateFlashCard(id, flashCardDto);
        return ResponseEntity.ok(new OperationResponse("Updating a flash-card", "success"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkFlashCard(#id)")
    public ResponseEntity<OperationResponse> deleteFlashCard(@PathVariable Integer id){
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.ok(new OperationResponse("Deleting a flash-card", "success"));
    }
}
