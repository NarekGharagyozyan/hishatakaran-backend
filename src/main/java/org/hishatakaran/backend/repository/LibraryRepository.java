package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}