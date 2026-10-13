package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.SettlementCountDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long>, JpaSpecificationExecutor<Settlement> {
    // topographics is the inverse side of a one-to-one: not joined, it costs one query per settlement
    @EntityGraph(attributePaths = "topographics")
    List<Settlement> findAllByRegionId(Long regionId);

    @Override
    @EntityGraph(attributePaths = "topographics")
    List<Settlement> findAll();

    @Override
    @EntityGraph(attributePaths = "topographics")
    List<Settlement> findAll(Specification<Settlement> spec);

    @Query("""
      select count (*) from Settlement s
    """)
    Long countSettlements();

    @Query("""
      select count (*) from SettlementImage si
    """)
    Long countSettlementImages();

    @Query("""
    SELECT new org.hishatakaran.backend.model.SettlementCountDto(
        s.id,
        s.nameHy,
        s.nameEn,
        s.nameFr,
        COUNT(m)
    )
    FROM Settlement s
    LEFT JOIN s.monuments m
    GROUP BY s.id, s.nameHy, s.nameEn, s.nameFr
    HAVING COUNT(m) > 0
    ORDER BY s.nameEn
""")
    List<SettlementCountDto> getSettlementCounts();
}