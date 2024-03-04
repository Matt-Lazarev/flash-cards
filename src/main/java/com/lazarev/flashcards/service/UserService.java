package com.lazarev.flashcards.service;

import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.exception.ElementNotFoundException;
import com.lazarev.flashcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ApplicationUser getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Getting user by username",
                        "User with login = '%s' is not found".formatted(username))
                );
    }

    public boolean checkAccessToDomain(String username, Integer domainId) {
        return userRepository.checkAccessToDomain(username, domainId);
    }

    public boolean checkAccessToGroup(String username, Integer groupId) {
        return userRepository.checkAccessToGroup(username, groupId);
    }

    public boolean checkAccessToDeck(String username, Integer deckId) {
        return userRepository.checkAccessToDeck(username, deckId);
    }

    public boolean checkAccessToFlashCard(String username, Integer flashCardId) {
        return userRepository.checkAccessToFlashCard(username, flashCardId);
    }
}
