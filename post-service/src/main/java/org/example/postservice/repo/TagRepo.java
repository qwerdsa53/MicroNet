package org.example.postservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qwerdsa53.shared.model.entity.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    List<Tag> findByNameIn(List<String> names);

    Optional<Tag> findByName(String name);
}
