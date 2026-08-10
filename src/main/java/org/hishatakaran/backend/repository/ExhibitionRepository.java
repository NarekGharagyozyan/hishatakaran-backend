package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>, JpaSpecificationExecutor<Exhibition> {

}
