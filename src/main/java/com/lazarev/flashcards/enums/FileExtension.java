package com.lazarev.flashcards.enums;

import lombok.Getter;

@Getter
public enum FileExtension {
    DOCX("docx", "application/docx"),
    ZIP("zip", "application/zip"),;

    private final String name;
    private final String mediaType;

    FileExtension(String name, String mediaType) {
        this.name = name;
        this.mediaType = mediaType;
    }
}
