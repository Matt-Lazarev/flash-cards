package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/domains")
public class DomainRouter {

    @GetMapping
    public String getDomainsPage(Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/domains.js")
                .setTitle("Domain Cards")
                .setHeader("Domains")
                .setElementType("domain")
                .setButtonText("New Domain")
                .setDefaultElementTypes(Set.of("group", "deck"));
        model.addAttribute("attributes", pageAttributes);
        return "elements";
    }

    @GetMapping("/new")
    public String getNewGroupPage(Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/new-domain.js")
                .setTitle("New Domain")
                .setElementType("domain")
                .setNameLabelText("Domain name:")
                .setDescriptionLabelText("Domain description:")
                .setSubmitText("Create domain")
                .setDefaultElementTypes(Set.of("domain", "group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }

    @GetMapping("/{id}/edit")
    public String getEditDomainPage(@PathVariable Integer id, Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setMainScript("/js/edit-domain.js")
                .setTitle("Edit Domain")
                .setElementType("domain")
                .setNameLabelText("Domain name:")
                .setDescriptionLabelText("Domain description:")
                .setSubmitText("Edit domain")
                .setDefaultElementTypes(Set.of("domain", "group", "deck#add", "deck#edit"));

        model.addAttribute("attributes", pageAttributes);
        return "new-element";
    }
}