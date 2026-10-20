package org.hishatakaran.backend.repository;

import java.util.Optional;

import org.hishatakaran.backend.entity.PageContent;
import org.hishatakaran.backend.model.PageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageContentRepository extends JpaRepository<PageContent, Long> {

  Optional<PageContent> findByPage(PageType page);
}
