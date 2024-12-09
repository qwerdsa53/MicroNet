package org.example.postservice.repo;

import org.example.postservice.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
}
