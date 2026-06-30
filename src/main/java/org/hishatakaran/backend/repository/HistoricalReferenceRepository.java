package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.HistoricalReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalReferenceRepository extends JpaRepository<HistoricalReference, Long> {
    List<HistoricalReference> findByMonumentId(Long monumentId);
}