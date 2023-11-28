package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.dto.common.OperationResponse;
import com.lazarev.flashcards.dto.document.DocumentDto;
import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.service.document.docx.DocumentFlashCardsService;
import com.lazarev.flashcards.service.validation.DeckDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentFlashCardsService documentFlashCardsService;

    @GetMapping(value = "/download", params = "deckId")
    @PreAuthorize("@userAccessChecker.checkDeck(#deckId)")
    public ResponseEntity<byte[]> downloadDeck(@RequestParam Integer deckId){
        DocumentDto document = documentFlashCardsService.createDocumentFromDeck(deckId);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.filename())
                .contentType(MediaType.parseMediaType(document.extension().getMediaType()))
                .body(document.content());
    }

    @GetMapping(value = "/download", params = "groupId")
    @PreAuthorize("@userAccessChecker.checkGroup(#groupId)")
    public ResponseEntity<byte[]> downloadGroup(@RequestParam Integer groupId){
        DocumentDto document = documentFlashCardsService.createZipDocumentFromGroup(groupId);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.filename())
                .contentType(MediaType.parseMediaType(document.extension().getMediaType()))
                .body(document.content());
    }

    @PostMapping(value = "parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Validate(DeckDtoValidator.class)
    @PreAuthorize("@userAccessChecker.checkGroup(#deckDto.getGroupId())")
    public ResponseEntity<OperationResponse> createDeckFromFile(@ModelAttribute DeckDto deckDto,
                                                                @RequestParam MultipartFile file){
        documentFlashCardsService.parseDocumentFlashCards(deckDto, file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OperationResponse("Saving a new deck", "success"));
    }
}
