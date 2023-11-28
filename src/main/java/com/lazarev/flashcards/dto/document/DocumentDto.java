package com.lazarev.flashcards.dto.document;

import com.lazarev.flashcards.enums.FileExtension;

public record DocumentDto (
        String filename,
        FileExtension extension,
        byte[] content
) { }
