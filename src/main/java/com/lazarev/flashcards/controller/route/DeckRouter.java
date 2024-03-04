package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/domains/{domainId}/groups/{groupId}/decks")
public class DeckRouter {

    @GetMapping
    public String getDecksPage(@PathVariable Integer domainId, @PathVariable String groupId, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/decks.js")
                .setTitle("Decks Cards")
                .setHeader("Decks")
                .setElementType("deck")
                .setButtonText("New Deck")
                .setDefaultElementTypes(Set.of("group", "deck"));
        model.addAttribute("attributes", pageAttributes);
        return "elements";
    }

    @GetMapping("/new")
    public String getNewDeckPage(@PathVariable Integer domainId, @PathVariable Integer groupId, Model model) {
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
    public String getEditDeckPage(@PathVariable Integer domainId,
                                  @PathVariable Integer groupId,
                                  @PathVariable Integer deckId,
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
