package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Topographic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TopographicRepository extends JpaRepository<Topographic, UUID> {
    List<Topographic> findByMonumentId(UUID monumentId);
}