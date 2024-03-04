package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/domains/{domainId}/groups")
public class GroupRouter {

    @GetMapping
    public String getGroupsPage(@PathVariable Integer domainId, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/groups.js")
                .setTitle("Group Cards")
                .setHeader("Groups")
                .setElementType("group")
                .setButtonText("New Group")
                .setDefaultElementTypes(Set.of("group", "deck"));
        model.addAttribute("attributes", pageAttributes);
        return "elements";
    }

    @GetMapping("/new")
    public String getNewGroupPage(@PathVariable Integer domainId, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/new-group.js")
                .setTitle("New Group")
                .setElementType("group")
                .setNameLabelText("Group name:")
                .setDescriptionLabelText("Group description:")
                .setSubmitText("Create group")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }

    @GetMapping("/{id}/edit")
    public String getEditGroupPage(@PathVariable Integer domainId, @PathVariable Integer id, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/edit-group.js")
                .setTitle("Edit Group")
                .setElementType("group")
                .setNameLabelText("Group name:")
                .setDescriptionLabelText("Group description:")
                .setSubmitText("Edit group")
                .setDefaultElementTypes(Set.of("group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }
}