package com.lazarev.flashcards.service.mapper;

import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    List<GroupDto> toGroupDtoList(List<Group> groupList);
    GroupDto toGroupDto(Group group);
    Group toGroup(GroupDto groupDto);

    @Mapping(target = "id", ignore = true)
    void updateGroup(@MappingTarget Group group, GroupDto groupDto);
}
