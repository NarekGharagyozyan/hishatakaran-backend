package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.mapper.RegionMapper;
import org.hishatakaran.backend.model.RegionEditDto;
import org.hishatakaran.backend.model.RegionRequestDto;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.model.RegionTranslationDto;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final SettlementRepository settlementRepository;
    private final MonumentRepository monumentRepository;
    private final GeminiService geminiService;

    public List<RegionResponseDto> getAll() {
        return regionRepository.findAll()
            .stream()
            .map(RegionMapper::toDto)
            .sorted(Comparator.comparing(RegionResponseDto::getId).reversed())
            .toList();
    }

    public RegionResponseDto getById(Long id) {
        return RegionMapper.toDto(
            regionRepository.findById(id).orElseThrow()
        );
    }

    public RegionResponseDto createNewRegion(RegionRequestDto regionRequestDto) {
        RegionTranslationDto translation;

        try {
            translation = geminiService.translateRegion(regionRequestDto.getName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to translate region");
        }

        Region region = new Region();
        region.setNameHy(translation.getNameHy());
        region.setNameEn(translation.getNameEn());
        region.setNameFr(translation.getNameFr());

        return RegionMapper.toDto(regionRepository.save(region));
    }

    public RegionResponseDto editRegion(Long regionId, RegionEditDto regionEditDto) {
        Region region = regionRepository.findById(regionId)
            .orElseThrow(() -> new RuntimeException("Region not found"));

        region.setNameHy(regionEditDto.getName().getHy());
        region.setNameEn(regionEditDto.getName().getEn());
        region.setNameFr(regionEditDto.getName().getFr());

        return RegionMapper.toDto(regionRepository.save(region));
    }

    @Transactional
    public void deleteRegion(Long regionId) {
        Region region = regionRepository.findById(regionId)
            .orElseThrow(() -> new RuntimeException("Region not found"));

        List<Settlement> settlementsInRegion = settlementRepository.findAllByRegionId(regionId);
        if (!settlementsInRegion.isEmpty()) {
            throw new SomethingWentWrongException("Կան բնակավայրեր որոնք օգտագործում են տվյալ մարզը։ Այդ բնակավայրերն են՝ "
                + settlementsInRegion.stream()
                .map(Settlement::getNameHy)
                .collect(Collectors.joining(", "))
            );
        }

        List<Monument> monumentsInRegion = monumentRepository.findByRegionId(regionId);
        if (!monumentsInRegion.isEmpty()) {
            throw new SomethingWentWrongException("Կան հուշարձաններ որոնք օգտագործում են տվյալ մարզը։ Այդ հուշարձաններն են՝ "
                + monumentsInRegion.stream()
                .map(Monument::getNameHy)
                .collect(Collectors.joining(", "))
            );
        }

        regionRepository.delete(region);
    }
}
