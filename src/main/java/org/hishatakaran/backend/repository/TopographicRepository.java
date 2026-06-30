package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Topographic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopographicRepository extends JpaRepository<Topographic, Long> {
    List<Topographic> findByMonumentId(Long monumentId);
}