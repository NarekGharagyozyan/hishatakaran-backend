package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Bibliography;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BibliographyRepository extends JpaRepository<Bibliography, UUID> {
    List<Bibliography> findByMonumentId(UUID monumentId);
}