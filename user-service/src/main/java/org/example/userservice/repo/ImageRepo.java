package org.example.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qwerdsa53.shared.model.entity.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
}
