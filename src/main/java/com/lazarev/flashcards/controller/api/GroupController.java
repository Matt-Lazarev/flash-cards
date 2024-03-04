package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.dto.common.OperationResponse;
import com.lazarev.flashcards.dto.element.DomainGroupsDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.service.GroupService;
import com.lazarev.flashcards.service.validation.GroupDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<DomainGroupsDto> getAllGroups(@RequestParam Integer domainId){
        return ResponseEntity.ok(groupService.getAllGroups(domainId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkGroup(#id)")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Integer id){
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    @Validate(GroupDtoValidator.class)
    public ResponseEntity<?> saveGroup(@RequestBody GroupDto groupDto){
        groupService.saveGroup(groupDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OperationResponse("Saving a new group", "success"));
    }

    @PutMapping("/{id}")
    @Validate(GroupDtoValidator.class)
    @PreAuthorize("@userAccessChecker.checkGroup(#id)")
    public ResponseEntity<?> updateGroup(@PathVariable Integer id,
                                         @RequestBody GroupDto groupDto){
        groupService.updateGroup(id, groupDto);
        return ResponseEntity
                .ok(new OperationResponse("Updating a group", "success"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkGroup(#id)")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id){
        groupService.deleteGroupById(id);
        return ResponseEntity
                .ok(new OperationResponse("Deleting a group", "success"));
    }
}
