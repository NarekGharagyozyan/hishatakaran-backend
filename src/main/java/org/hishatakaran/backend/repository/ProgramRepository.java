package org.hishatakaran.backend.repository;

import java.util.List;

import org.hishatakaran.backend.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {

  @Query("""
    select count(*) from Program p
  """)
  Long countPrograms();

  List<Program> findAllByProgramTypesId(Long programTypeId);
}
