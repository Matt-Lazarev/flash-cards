package com.lazarev.flashcards.service.validation;

import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.lazarev.flashcards.service.validation.ValidationConstants.isValidElementDescription;
import static com.lazarev.flashcards.service.validation.ValidationConstants.isValidElementName;

@Component
@RequiredArgsConstructor
public class DeckDtoValidator implements Validator<DeckDto> {

    @Override
    public void validate(DeckDto deckDto) {
        Map<String, String> errors = new LinkedHashMap<>();

        String name = deckDto.getName();
        String description = deckDto.getDescription();

        errors.putAll(isValidElementName(name));
        errors.putAll(isValidElementDescription(description));

        if (errors.size() != 0) {
            throw new ValidationException(errors);
        }
    }
}
