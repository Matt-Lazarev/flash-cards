package com.lazarev.flashcards.dto.element;

import com.lazarev.flashcards.enums.LearnMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlashCardDto {
    private Integer id;
    private String frontSide;
    private String backSide;
    private String examples;
    private Boolean isCorrectAnswer;
    private LearnMode mode;
    private Integer deckId;

    public FlashCardDto(Integer id, String frontSide,
                        String backSide, String examples,
                        Integer deckId) {
        this.id = id;
        this.frontSide = frontSide;
        this.backSide = backSide;
        this.examples = examples;
        this.deckId = deckId;
    }
}
