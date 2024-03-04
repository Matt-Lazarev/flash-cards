package com.lazarev.flashcards.dto.element;

public record DomainDto(
        Integer id,
        String name,
        String description,
        Integer groupsCount
) {}
