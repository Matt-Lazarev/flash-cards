package com.lazarev.flashcards.service;

import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.entity.Deck;
import com.lazarev.flashcards.entity.Group;
import com.lazarev.flashcards.exception.ElementNotFoundException;
import com.lazarev.flashcards.repository.GroupRepository;
import com.lazarev.flashcards.service.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Sort GROUPS_SORT = Sort.by("name");

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserService userService;

    public List<GroupDto> getAllGroups(String username){
        return groupRepository.findAllByUsername(username, GROUPS_SORT);
    }

    public GroupDto getGroupById(Integer id) {
        return groupRepository.findGroupById(id)
                .orElseThrow(()->new ElementNotFoundException(
                        "Getting group",
                        "Group with id = '%d' not found".formatted(id)));
    }

    @Transactional
    public void saveGroup(GroupDto groupDto, String username){
        ApplicationUser user = userService.getUserByUsername(username);
        Group group = groupMapper.toGroup(groupDto);
        group.setUser(user);

        groupRepository.save(group);
    }

    public void addDeckToGroup(Integer groupId, Deck deck){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->new ElementNotFoundException(
                        "Adding deck to group",
                        "Group with id = '%d' not found".formatted(groupId))
                );
        group.addDeck(deck);
    }

    @Transactional
    public void updateGroup(Integer id, GroupDto groupDto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(()->new ElementNotFoundException(
                        "Updating group",
                        "Group with id = '%d' not found".formatted(id))
                );
        groupMapper.updateGroup(group, groupDto);
        groupRepository.save(group);
    }

    @Transactional
    public void deleteGroupById(Integer id) {
        if(!groupRepository.existsById(id)){
            throw new ElementNotFoundException(
                    "Deleting group",
                    "Group with id = '%d' not found".formatted(id)
            );
        }
        groupRepository.deleteById(id);
    }
}
