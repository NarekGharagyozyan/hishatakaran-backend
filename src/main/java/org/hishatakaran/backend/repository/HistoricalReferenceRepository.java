package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoricalReferenceRepository extends JpaRepository<HistoricalReference, UUID> {
    List<HistoricalReference> findByMonumentId(UUID monumentId);
}