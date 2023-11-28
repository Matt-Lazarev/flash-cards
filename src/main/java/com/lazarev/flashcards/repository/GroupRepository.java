package com.lazarev.flashcards.repository;

import com.lazarev.flashcards.dto.element.DeckDto;
import com.lazarev.flashcards.dto.element.GroupDto;
import com.lazarev.flashcards.entity.Group;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(
    """
        select new GroupDto(g.id, g.name, g.description, size(g.decks))
        from Group g where g.user.username = :username
    """)
    List<GroupDto> findAllByUsername(String username, Sort sort);

    @Query(
    """
        select new GroupDto(g.id, g.name, g.description, size(g.decks))
        from Group g where g.id = :id
    """
    )
    Optional<GroupDto> findGroupById(Integer id);
}
