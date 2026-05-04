package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}