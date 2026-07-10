package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Monument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MonumentRepository extends JpaRepository<Monument, Long>, JpaSpecificationExecutor<Monument> {

    List<Monument> findByRegionId(Long region_id);

    List<Monument> findBySettlementId(Long settlement_id);

    List<Monument> findByMonumentTypeHyOrMonumentTypeEnOrMonumentTypeFr(String monumentTypeHy, String monumentTypeEn, String monumentTypeFr);



}