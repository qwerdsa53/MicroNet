package org.example.postservice.repo;

import org.example.postservice.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
