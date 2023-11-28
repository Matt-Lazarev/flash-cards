package com.lazarev.flashcards.service.validation;

import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.lazarev.flashcards.service.validation.ValidationConstants.*;

@Component
@RequiredArgsConstructor
public class GroupDtoValidator implements Validator<GroupDto> {

    @Override
    public void validate(GroupDto groupDto) {
        Map<String, String> errors = new LinkedHashMap<>();

        String name = groupDto.name();
        String description = groupDto.description();

        errors.putAll(isValidElementName(name));
        errors.putAll(isValidElementDescription(description));

        if (errors.size() != 0) {
            throw new ValidationException(errors);
        }
    }
}
