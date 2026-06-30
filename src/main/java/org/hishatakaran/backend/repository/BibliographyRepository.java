package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Bibliography;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliographyRepository extends JpaRepository<Bibliography, Long> {
    List<Bibliography> findByMonumentId(Long monumentId);
}