package com.lazarev.flashcards.dto.element;

import java.util.List;

public record DomainGroupsDto(
        String domainName,
        List<GroupDto> groups
) {}
