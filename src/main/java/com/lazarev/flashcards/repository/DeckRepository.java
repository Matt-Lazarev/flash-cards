package com.lazarev.flashcards.repository;

import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.entity.Deck;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DeckRepository extends JpaRepository<Deck, Integer> {

    @Query(
    """
        select new DeckDto(d.id, d.name, d.description, d.group.id, d.group.name, size(d.flashCards))
        from Deck d where d.group.id = :groupId
    """)
    List<DeckDto> findAllByGroupId(Integer groupId, Sort createdAt);

    @Query(
    """
        select new DeckDto(d.id, d.name, d.description, d.group.id, d.group.name, size(d.flashCards))
        from Deck d where d.id = :id
    """
    )
    Optional<DeckDto> findDeckById(Integer id);

    @Query(
    """
        select d from Deck d
        left join fetch d.flashCards
        where d.id = :id
    """)
    Optional<Deck> findDeckFlashCardsById(Integer id);

    @Query(
    """
        select d from Deck d
        left join fetch d.flashCards
        where d.group.id = :groupId
    """)
    List<Deck> findGroupFlashCardsById(Integer groupId);
}
