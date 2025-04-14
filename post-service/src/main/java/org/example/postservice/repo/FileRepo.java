package org.example.postservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qwerdsa53.shared.model.entity.File;

@Repository
public interface FileRepo extends JpaRepository<File, Long> {
}
