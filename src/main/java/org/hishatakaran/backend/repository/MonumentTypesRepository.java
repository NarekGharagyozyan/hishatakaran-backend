package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.MonumentTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonumentTypesRepository  extends JpaRepository<MonumentTypes, Long> {
}
