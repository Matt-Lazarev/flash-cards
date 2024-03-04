package com.lazarev.flashcards.service.mapper;

import com.lazarev.flashcards.dto.element.DomainDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.entity.Domain;
import com.lazarev.flashcards.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    Domain toDomain(DomainDto groupDto);

    DomainDto toDomainDto(Domain domain);

    List<DomainDto> toDomainDtoList(List<Domain> domains);

    @Mapping(target = "id", ignore = true)
    void updateDomain(@MappingTarget Domain domain, DomainDto domainDto);
}
