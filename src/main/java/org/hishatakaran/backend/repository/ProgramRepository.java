package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {
}
