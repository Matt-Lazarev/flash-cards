package com.lazarev.flashcards.service;

import com.lazarev.flashcards.dto.element.DomainDto;
import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.entity.Domain;
import com.lazarev.flashcards.entity.Group;
import com.lazarev.flashcards.exception.ElementNotFoundException;
import com.lazarev.flashcards.repository.DomainRepository;
import com.lazarev.flashcards.service.mapper.DomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainService {
    private static final Sort DOMAIN_SORT = Sort.by("name");

    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final UserService userService;

    public List<DomainDto> getAllDomains(String username){
        return domainRepository.findAllByUsername(username, DOMAIN_SORT);
    }

    public DomainDto getDomainById(Integer id) {
        return domainRepository.findDomainById(id)
                .orElseThrow(()->new ElementNotFoundException(
                        "Getting domain",
                        "Domain with id = '%d' not found".formatted(id)));
    }

    @Transactional
    public void saveDomain(DomainDto domainDto, String username){
        ApplicationUser user = userService.getUserByUsername(username);
        Domain domain = domainMapper.toDomain(domainDto);
        domain.setUser(user);

        domainRepository.save(domain);
    }

    public void addGroupToDomain(Integer domainId, Group group){
        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(()->new ElementNotFoundException(
                        "Adding group to domain",
                        "Domain with id = '%d' not found".formatted(domainId))
                );
        domain.addGroup(group);
    }

    @Transactional
    public void updateDomain(Integer id, DomainDto domainDto) {
        Domain domain = domainRepository.findById(id)
                .orElseThrow(()->new ElementNotFoundException(
                        "Updating domain",
                        "Domain with id = '%d' not found".formatted(id))
                );
        domainMapper.updateDomain(domain, domainDto);
        domainRepository.save(domain);
    }

    @Transactional
    public void deleteDomainById(Integer id) {
        if(!domainRepository.existsById(id)){
            throw new ElementNotFoundException(
                    "Deleting domain",
                    "Domain with id = '%d' not found".formatted(id)
            );
        }
        domainRepository.deleteById(id);
    }
}
