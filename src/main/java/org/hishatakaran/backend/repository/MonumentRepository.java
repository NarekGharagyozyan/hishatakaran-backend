package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentStatus;
import org.hishatakaran.backend.model.MonumentTypeCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonumentRepository extends JpaRepository<Monument, Long>, JpaSpecificationExecutor<Monument> {

    List<Monument> findByRegionId(Long region_id);

    List<Monument> findBySettlementId(Long settlement_id);

    //List<Monument> findByMonumentTypeHyOrMonumentTypeEnOrMonumentTypeFr(String monumentTypeHy, String monumentTypeEn, String monumentTypeFr);

    List<Monument> findByMonumentTypeId(Long monument_type_id);

    @Query("""
    SELECT new org.hishatakaran.backend.model.MonumentTypeCountDto(
        mt.id,
        mt.nameHy,
        mt.nameEn,
        mt.nameFr,
        COUNT(m)
    )
    FROM MonumentTypes mt
    LEFT JOIN mt.monuments m
    where m.monumentStatus = :status
    GROUP BY mt.id, mt.nameHy, mt.nameEn, mt.nameFr
    HAVING COUNT(m) > 0
    ORDER BY mt.id
""")
    List<MonumentTypeCountDto> getMonumentTypeCounts(@Param("status") MonumentStatus status);

    @Query("""
        SELECT count(m)
        FROM Monument m
        WHERE m.monumentStatus = :status
    """)
    Long countMonuments(@Param("status") MonumentStatus status);

}