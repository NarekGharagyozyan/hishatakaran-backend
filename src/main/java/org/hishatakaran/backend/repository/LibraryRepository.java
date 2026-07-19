package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {
}