package com.lazarev.flashcards.service.validation;

import com.lazarev.flashcards.dto.element.DomainDto;
import com.lazarev.flashcards.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.lazarev.flashcards.service.validation.ValidationConstants.isValidElementDescription;
import static com.lazarev.flashcards.service.validation.ValidationConstants.isValidElementName;

@Component
@RequiredArgsConstructor
public class DomainDtoValidator implements Validator<DomainDto> {

    @Override
    public void validate(DomainDto domainDto) {
        Map<String, String> errors = new LinkedHashMap<>();

        String name = domainDto.name();
        String description = domainDto.description();

        errors.putAll(isValidElementName(name));
        errors.putAll(isValidElementDescription(description));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
