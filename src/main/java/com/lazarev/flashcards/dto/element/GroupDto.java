package com.lazarev.flashcards.dto.element;

public record GroupDto (
        Integer id,
        String name,
        String description,
        Integer decksCount
) {}
