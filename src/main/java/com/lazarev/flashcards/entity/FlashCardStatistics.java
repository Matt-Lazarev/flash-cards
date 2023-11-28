package com.lazarev.flashcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "flash_card_statistics")
public class FlashCardStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer correctAnswers;

    private LocalDate primaryMemorizationDate;

    private LocalDate nextLearnDate;

    private Integer daysToNextLearn;

    @PrePersist
    void prePersist() {
        if(correctAnswers == null){
            correctAnswers = 0;
        }
    }
}
