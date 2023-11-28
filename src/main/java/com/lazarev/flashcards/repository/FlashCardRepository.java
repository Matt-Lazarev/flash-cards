package com.lazarev.flashcards.repository;

import com.lazarev.flashcards.dto.element.FlashCardDto;
import com.lazarev.flashcards.entity.FlashCard;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlashCardRepository extends JpaRepository<FlashCard, Integer> {
    @Query(
    """
        select new FlashCardDto(fc.id, fc.frontSide, fc.backSide, fc.examples, fc.deck.id)
        from FlashCard fc
        where fc.deck.id = :deckId
    """)
    List<FlashCardDto> findAllByDeckId(Integer deckId, Sort sort);

    @Query("""
           select new FlashCardDto(fc.id, fc.frontSide, fc.backSide, fc.examples, fc.deck.id)
           from FlashCard fc
           where fc.deck.id = :deckId and
           (fc.flashCardStatistics.nextLearnDate is null or fc.flashCardStatistics.nextLearnDate <= :now)
           """)
    List<FlashCardDto> findAllAfter(Integer deckId, LocalDate now, Sort sort);

    @Query(
    """
        select fc from FlashCard fc
        left join fetch fc.flashCardStatistics
        where fc.id = :id
    """)
    Optional<FlashCard> findFlashCardStatisticsById(Integer id);
}
