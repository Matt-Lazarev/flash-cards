package com.lazarev.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@Table(name = "flash_cards")
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String frontSide;

    private String backSide;

    private String examples;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = {PERSIST, MERGE, DETACH, REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", referencedColumnName = "id")
    private Deck deck;

    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "flash_card_statistics_id", referencedColumnName = "id")
    private FlashCardStatistics flashCardStatistics;

    @PrePersist
    void prePersist() {
        if(flashCardStatistics == null){
            flashCardStatistics = new FlashCardStatistics();
        }
    }
}
