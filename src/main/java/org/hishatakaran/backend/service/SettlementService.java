package org.hishatakaran.backend.service;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

  private final GeminiService geminiService;
  private final RegionRepository regionRepository;
  private final SettlementRepository settlementRepository;

  public SettlementResponseDto createNewSettlement(Long regionId, SettlementRequestDto settlementRequestDto) {
    SettlementTranslationDto translation;

    try {
      translation = geminiService.translateSettlement(
          settlementRequestDto.getName()
      );
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to translate settlement");
    }

    Settlement settlement = new Settlement(
        translation.getNameHy(),
        translation.getNameEn(),
        translation.getNameFr(),
        regionRepository.findById(regionId).orElseThrow(() -> new RuntimeException("Region not found"))
    );

    settlementRepository.save(settlement);
    return SettlementMapper.toDto(settlement);
  }

  public SettlementResponseDto editSettlement(Long settlementId, SettlementEditDto settlementEditDto) {
    Settlement settlement = settlementRepository.findById(settlementId).orElseThrow(
        () -> new RuntimeException("Settlement not found"));
    settlement.setNameHy(settlementEditDto.getName().getHy());
    settlement.setNameEn(settlementEditDto.getName().getEn());
    settlement.setNameFr(settlementEditDto.getName().getFr());
    settlement.setRegion(regionRepository.findById(settlementEditDto.getRegionId()).orElseThrow(() -> new RuntimeException("Region not found")));
    Settlement savedSettlement = settlementRepository.save(settlement);
    return SettlementMapper.toDto(savedSettlement);
  }
}
