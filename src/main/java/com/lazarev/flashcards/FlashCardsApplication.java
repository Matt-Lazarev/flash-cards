package com.lazarev.flashcards;

import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.entity.FlashCard;
import com.lazarev.flashcards.entity.Group;
import com.lazarev.flashcards.repository.FlashCardRepository;
import com.lazarev.flashcards.repository.GroupRepository;
import com.lazarev.flashcards.repository.UserRepository;
import org.owasp.encoder.Encode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FlashCardsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashCardsApplication.class, args);
    }
}

class T {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String s = Encode.forHtml("<script>alert('hello');</script>");
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);
        String s1 = Encode.forHtml("English decks");
        String s2 = Encode.forHtml("English group!./\\=+-,:;");
        //System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
    }
}