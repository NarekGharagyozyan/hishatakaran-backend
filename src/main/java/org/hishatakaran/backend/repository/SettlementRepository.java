package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Integer> {
    List<Settlement> findByRegionId(Integer regionId);
}