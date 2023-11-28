package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.common.OperationResponse;
import com.lazarev.flashcards.dto.element.GroupDecksDto;
import com.lazarev.flashcards.service.DeckService;
import com.lazarev.flashcards.service.validation.DeckDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks")
public class DeckController {
    private final DeckService deckService;

    @GetMapping
    @PreAuthorize("@userAccessChecker.checkGroup(#groupId)")
    public ResponseEntity<GroupDecksDto> getAllDecksByGroupId(@RequestParam Integer groupId){
        return ResponseEntity.ok(deckService.getAllDeckByGroupId(groupId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkDeck(#id)")
    public ResponseEntity<DeckDto> getDeckById(@PathVariable Integer id){
        return ResponseEntity.ok(deckService.getDeckById(id));
    }

    @PostMapping
    @Validate(DeckDtoValidator.class)
    @PreAuthorize("@userAccessChecker.checkGroup(#deckDto.getGroupId())")
    public ResponseEntity<OperationResponse> saveDeck(@RequestBody DeckDto deckDto){
        deckService.saveDeck(deckDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OperationResponse("Saving a new deck", "success"));
    }

    @PutMapping("/{id}")
    @Validate(DeckDtoValidator.class)
    @PreAuthorize("@userAccessChecker.checkDeck(#id)")
    public ResponseEntity<?> updateDeck(@PathVariable Integer id,
                                         @RequestBody DeckDto groupDto){
        deckService.updateDeck(id, groupDto);
        return ResponseEntity
                .ok(new OperationResponse("Updating a new group", "success"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkDeck(#id)")
    public ResponseEntity<?> deleteDeck(@PathVariable Integer id){
        deckService.deleteDeckById(id);
        return ResponseEntity
                .ok(new OperationResponse("Deleting a deck", "success"));
    }
}
