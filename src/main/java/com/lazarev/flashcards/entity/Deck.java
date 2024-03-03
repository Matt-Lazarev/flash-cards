package com.lazarev.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @OrderBy("frontSide")
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<FlashCard> flashCards = new ArrayList<>();

    public void addFlashCard(FlashCard flashCard) {
        addFlashCardElement(flashCard);
    }

    public void setFlashCards(List<FlashCard> flashCards){
        for(FlashCard flashCard : flashCards){
            addFlashCardElement(flashCard);
        }
    }

    private void addFlashCardElement(FlashCard flashCard){
        flashCard.setDeck(this);
        flashCards.add(flashCard);
    }
}
