package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.SettlementCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findAllByRegionId(Long regionId);

    @Query("""
      select count (*) from Settlement s
    """)
    Long countSettlements();

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