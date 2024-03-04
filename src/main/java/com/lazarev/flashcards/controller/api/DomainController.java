package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.dto.common.OperationResponse;
import com.lazarev.flashcards.dto.element.DomainDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.service.DomainService;
import com.lazarev.flashcards.service.GroupService;
import com.lazarev.flashcards.service.validation.DomainDtoValidator;
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
@RequestMapping("/api/v1/domains")
public class DomainController {
    private final DomainService domainService;

    @GetMapping
    public ResponseEntity<List<DomainDto>> getAllDomains(Authentication authentication){
        String username = authentication.getName();
        return ResponseEntity.ok(domainService.getAllDomains(username));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkDomain(#id)")
    public ResponseEntity<DomainDto> getDomainById(@PathVariable Integer id){
        return ResponseEntity.ok(domainService.getDomainById(id));
    }

    @PostMapping
    @Validate(DomainDtoValidator.class)
    public ResponseEntity<?> saveDomain(@RequestBody DomainDto domainDto, Authentication authentication){
        String username = authentication.getName();
        domainService.saveDomain(domainDto, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new OperationResponse("Saving a new domain", "success"));
    }

    @PutMapping("/{id}")
    @Validate(DomainDtoValidator.class)
    @PreAuthorize("@userAccessChecker.checkDomain(#id)")
    public ResponseEntity<?> updateGroup(@PathVariable Integer id,
                                         @RequestBody DomainDto domainDto){
        domainService.updateDomain(id, domainDto);
        return ResponseEntity
                .ok(new OperationResponse("Updating a domain", "success"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userAccessChecker.checkDomain(#id)")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer id){
        domainService.deleteDomainById(id);
        return ResponseEntity
                .ok(new OperationResponse("Deleting a domain", "success"));
    }
}
