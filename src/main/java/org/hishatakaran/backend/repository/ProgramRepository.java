package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
