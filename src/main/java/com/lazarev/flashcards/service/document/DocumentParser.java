package com.lazarev.flashcards.service.document;

import com.lazarev.flashcards.dto.document.ParsedDocumentResult;

import java.io.InputStream;

public interface DocumentParser {
    ParsedDocumentResult parse(InputStream inputStream);
}
