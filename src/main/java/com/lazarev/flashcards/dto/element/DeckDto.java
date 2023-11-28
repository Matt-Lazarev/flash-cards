package com.lazarev.flashcards.dto.element;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeckDto {
    private Integer id;
    private String name;
    private String description;
    private Integer groupId;
    private String groupName;
    private Integer flashCardsCount;
    private List<FlashCardDto> flashCards;

    public DeckDto(Integer id, String name,
                   String description, Integer groupId,
                   String groupName, Integer flashCardsCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.groupId = groupId;
        this.groupName = groupName;
        this.flashCardsCount = flashCardsCount;
    }
}

