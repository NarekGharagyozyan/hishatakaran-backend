package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.News;
import org.hishatakaran.backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    List<News> findByStatus(Status status);
}