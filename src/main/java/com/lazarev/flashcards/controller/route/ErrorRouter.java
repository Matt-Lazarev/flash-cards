package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorRouter implements ErrorController {
    @GetMapping("/forbidden")
    public String getForbiddenPage(Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setTitle("Forbidden")
                .setError("Access to the page is forbidden");
        model.addAttribute("attributes", pageAttributes);
        return "error-page";
    }

    @GetMapping("/not-found")
    public String getNotFoundPage(Model model) {
        PageAttributes pageAttributes = new PageAttributes()
                .setTitle("Not Found")
                .setError("Requested page not found");
        model.addAttribute("attributes", pageAttributes);
        return "error-page";
    }

    @GetMapping("/error")
    public String redirectToNotFound(){
        return "forward:/not-found";
    }
}
