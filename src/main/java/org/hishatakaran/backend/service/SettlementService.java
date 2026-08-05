package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentImage;
import org.hishatakaran.backend.entity.MonumentMeasurement;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

  private final GeminiService geminiService;
  private final RegionRepository regionRepository;
  private final SettlementRepository settlementRepository;
  private final FileStorageService fileStorageService;

  public SettlementResponseDto createNewSettlement(Long regionId, SettlementRequestDto settlementRequestDto) {
    SettlementTranslationDto translation;

    try {
      translation = geminiService.translateSettlement(settlementRequestDto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to translate settlement");
    }

    Settlement settlement = new Settlement(
        translation.getNameHy(),
        translation.getNameEn(),
        translation.getNameFr(),
        translation.getDescriptionHy(),
        translation.getDescriptionEn(),
        translation.getDescriptionFr(),
        settlementRequestDto.getLongitude(),
        settlementRequestDto.getLatitude(),
        regionRepository.findById(regionId).orElseThrow(() -> new RuntimeException("Region not found"))
    );

    if (settlementRequestDto.getImages() != null)
    {
      settlement.setImages(
          settlementRequestDto.getImages()
              .stream()
              .map(image -> new SettlementImage(
                  image.getUrl(),
                  image.getCaption(),
                  null,
                  null,
                  settlement
              ))
              .toList()
      );
    }

    Settlement savedSettlement = settlementRepository.save(settlement);
    return SettlementMapper.toDto(savedSettlement);
  }

  public SettlementResponseDto editSettlement(Long settlementId, SettlementEditDto settlementEditDto) {
    Settlement settlement = settlementRepository.findById(settlementId).orElseThrow(
        () -> new RuntimeException("Settlement not found"));
    settlement.setNameHy(settlementEditDto.getName().getHy());
    settlement.setNameEn(settlementEditDto.getName().getEn());
    settlement.setNameFr(settlementEditDto.getName().getFr());
    settlement.setDescriptionHy(settlementEditDto.getDescription().getHy());
    settlement.setDescriptionEn(settlementEditDto.getDescription().getEn());
    settlement.setDescriptionFr(settlementEditDto.getDescription().getFr());

    settlement.getImages().clear();
    settlementEditDto.getImages()
        .stream()
        .map(dto -> new SettlementImage(
            dto.getUrl(),
            dto.getCaption() != null ? dto.getCaption().getHy() : null,
            dto.getCaption() != null ? dto.getCaption().getEn() : null,
            dto.getCaption() != null ? dto.getCaption().getFr() : null,
            settlement
        ))
        .forEach(settlement.getImages()::add);

    settlement.setLongitude(settlementEditDto.getLongitude());
    settlement.setLatitude(settlementEditDto.getLatitude());
    settlement.setRegion(regionRepository.findById(settlementEditDto.getRegionId()).orElseThrow(() -> new RuntimeException("Region not found")));
    Settlement savedSettlement = settlementRepository.save(settlement);
    return SettlementMapper.toDto(savedSettlement);
  }

  @Transactional
  public void deleteSettlement(Long id) {
    Settlement settlement = settlementRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Settlement not found"));

    deleteFiles(settlement.getImages().stream().map(SettlementImage::getUrl).toList());

    settlementRepository.delete(settlement);
  }

  private void deleteFiles(List<String> paths) {
    if (paths == null) {
      return;
    }

    paths.forEach(fileStorageService::deleteImage);
  }
}
