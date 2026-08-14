package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.MonumentMeasurement;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonumentMeasurementRepository extends JpaRepository<MonumentMeasurement, Long> {

  @Query("""
    select count(*) from MonumentMeasurement mm
  """)
  Long countMonumentMeasurements();
}
