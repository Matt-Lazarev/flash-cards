package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/groups/{groupId}/decks/{deckId}/flash-cards")
public class FlashCardRouter {

    @GetMapping
    public String getFlashCardsPage(@PathVariable Integer groupId,
                                    @PathVariable Integer deckId) {
        return "flash-cards";
    }

    @GetMapping("/new")
    public String getNewFlashCardPage(@PathVariable Integer groupId,
                                      @PathVariable Integer deckId,
                                      Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/new-flash-card.js")
                .setTitle("New Flash-Card")
                .setElementType("flashCard")
                .setFrontSideText("Front-side:")
                .setBackSideText("Back-side:")
                .setExamplesText("Examples:")
                .setSubmitText("Create flash-card")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }

    @GetMapping("/{id}/edit")
    public String getEditFlashCardPage(@PathVariable Integer groupId,
                                       @PathVariable Integer deckId,
                                       @PathVariable Integer id,
                                       Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/edit-flash-card.js")
                .setTitle("Edit Flash-Card")
                .setElementType("flashCard")
                .setFrontSideText("Front-side:")
                .setBackSideText("Back-side:")
                .setExamplesText("Examples:")
                .setSubmitText("Edit flash-card")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }

    @GetMapping("/learn")
    public String getLearnFlashCardsPage(@PathVariable Integer groupId,
                                         @PathVariable Integer deckId) {
        return "learn";
    }
}
