package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.MonumentVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonumentVideoRepository extends JpaRepository<MonumentVideo, Long> {

  @Query("""
    select count(*) from MonumentVideo mv
  """)
  Long countMonumentVideos();
}
