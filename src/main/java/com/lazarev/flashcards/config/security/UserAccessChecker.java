package com.lazarev.flashcards.config.security;

import com.lazarev.flashcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessChecker {
    private final UserService userService;

    public boolean checkDomain(Integer domainId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.checkAccessToDomain(username, domainId);
    }

    public boolean checkGroup(Integer groupId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.checkAccessToGroup(username, groupId);
    }

    public boolean checkDeck(Integer deckId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.checkAccessToDeck(username, deckId);
    }

    public boolean checkFlashCard(Integer flashCardId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.checkAccessToFlashCard(username, flashCardId);
    }
}
