package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.MonumentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonumentImageRepository extends JpaRepository<MonumentImage, Integer> {

  @Query("""
    select count(*) from MonumentImage m
  """)
  Long countMonumentImages();
}
