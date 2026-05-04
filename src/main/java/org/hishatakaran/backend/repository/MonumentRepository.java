package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.MonumentType;
import org.hishatakaran.backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MonumentRepository extends JpaRepository<Monument, UUID> {
    List<Monument> findByStatus(Status status);

    List<Monument> findByRegionId(Integer regionId);

    List<Monument> findBySettlementId(Integer settlementId);

    List<Monument> findByMonumentType(MonumentType monumentType);

    List<Monument> findByNameContainingIgnoreCase(String name);
}