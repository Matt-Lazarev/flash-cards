package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/groups/{groupId}/decks")
public class DeckRouter {

    @GetMapping
    public String getDecksPage(@PathVariable String groupId, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/decks.js")
                .setTitle("Decks Cards")
                .setHeader("Decks")
                .setElementType("deck")
                .setButtonText("New Deck");
        model.addAttribute("attributes", pageAttributes);
        return "elements";
    }

    @GetMapping("/new")
    public String getNewDeckPage(@PathVariable String groupId, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/new-deck.js")
                .setTitle("New Deck")
                .setElementType("deck#add")
                .setNameLabelText("Deck name:")
                .setDescriptionLabelText("Deck description:")
                .setFileLabelText("Create deck from Word document:")
                .setSubmitText("Create deck")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }

    @GetMapping("/{deckId}/edit")
    public String getEditDeckPage(@PathVariable Integer groupId,
                                   @PathVariable String deckId,
                                   Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/edit-deck.js")
                .setTitle("Edit Deck")
                .setElementType("deck#edit")
                .setNameLabelText("Deck name:")
                .setDescriptionLabelText("Deck description:")
                .setSubmitText("Edit deck")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }
}
