package com.lazarev.flashcards.controller.route;

import com.lazarev.flashcards.dto.ui.PageAttributes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthRouter {

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        PageAttributes pageAttributes = new PageAttributes()
                .setTitle("Registration")
                .setHeader("Registration Form")
                .setFormId("registerForm")
                .setAuthType("registration")
                .setSubmitText("Sign up");
        model.addAttribute("attributes", pageAttributes);
        return "auth";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletResponse response){
        PageAttributes pageAttributes = new PageAttributes()
                .setTitle("Login")
                .setHeader("Login Form")
                .setFormId("loginForm")
                .setAuthType("login")
                .setSubmitText("Sign in");
        model.addAttribute("attributes", pageAttributes);
        return "auth";
    }
}