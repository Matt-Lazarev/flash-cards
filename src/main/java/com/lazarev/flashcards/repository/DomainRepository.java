package com.lazarev.flashcards.repository;

import com.lazarev.flashcards.dto.element.DomainDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.entity.Domain;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
    @Query(
            """
                select new DomainDto(d.id, d.name, d.description, size(d.groups))
                from Domain d where d.user.username = :username
            """)
    List<DomainDto> findAllByUsername(String username, Sort sort);

    @Query(
            """
                select new DomainDto(d.id, d.name, d.description, size(d.groups))
                from Domain d where d.id = :id
            """
    )
    Optional<DomainDto> findDomainById(Integer id);
}
