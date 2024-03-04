package com.lazarev.flashcards.repository;

import com.lazarev.flashcards.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findByUsername(String username);

    @Query("""
        select case when count(d) > 0 then true else false end
        from Domain d where d.user.username = :username
        and d.id = :domainId
    """)
    boolean checkAccessToDomain(String username, Integer domainId);

    @Query("""
        select case when count(g) > 0 then true else false end
        from Group g where g.domain.user.username = :username
        and g.id = :groupId
    """)
    boolean checkAccessToGroup(String username, Integer groupId);

    @Query("""
        select case when count(d) > 0 then true else false end
        from Deck d where d.group.domain.user.username = :username
        and d.id = :deckId
    """)
    boolean checkAccessToDeck(String username, Integer deckId);

    @Query("""
        select case when count(fc) > 0 then true else false end
        from FlashCard fc where fc.deck.group.domain.user.username = :username
        and fc.id = :flashCardId
    """)
    boolean checkAccessToFlashCard(String username, Integer flashCardId);
}
