package org.hishatakaran.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentAiResponseDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonumentService {

    private final MonumentRepository monumentRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final RegionRepository regionRepository;
    private final SettlementRepository settlementRepository;
    private final FileStorageService fileStorageService;

    public MonumentResponseDto postMonument(MonumentRequestDto monumentRequestDto) {
        String requestGeminiForMonuments = geminiService.requestGeminiForMonuments(monumentRequestDto);
        MonumentAiResponseDto monumentAiResponseDto = null;
        try {
            var cleanJson = extractJson(requestGeminiForMonuments);
            monumentAiResponseDto = objectMapper.readValue(cleanJson, MonumentAiResponseDto.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        if (monumentAiResponseDto != null)
        {

            Monument monument = new Monument(
                Status.DRAFT,
                monumentAiResponseDto.getNameHy(),
                monumentAiResponseDto.getNameEn(),
                monumentAiResponseDto.getNameFr(),
                regionRepository.findById(monumentRequestDto.getRegionId()).orElse(null),
                settlementRepository.findById(monumentRequestDto.getSettlementId()).orElse(null),
                monumentAiResponseDto.getMonumentTypeHy(),
                monumentAiResponseDto.getMonumentTypeEn(),
                monumentAiResponseDto.getMonumentTypeFr(),
                monumentAiResponseDto.getSpecialNameHy(),
                monumentAiResponseDto.getSpecialNameEn(),
                monumentAiResponseDto.getSpecialNameFr(),
                monumentAiResponseDto.getAnotherNamesHy(),
                monumentAiResponseDto.getAnotherNamesEn(),
                monumentAiResponseDto.getAnotherNamesFr(),
                monumentAiResponseDto.getProvinceHy(),
                monumentAiResponseDto.getProvinceEn(),
                monumentAiResponseDto.getProvinceFr(),
                monumentAiResponseDto.getOriginalAffiliationHy(),
                monumentAiResponseDto.getOriginalAffiliationEn(),
                monumentAiResponseDto.getOriginalAffiliationFr(),
                monumentAiResponseDto.getStorageUnitNameHy(),
                monumentAiResponseDto.getStorageUnitNameEn(),
                monumentAiResponseDto.getStorageUnitNameFr(),
                monumentAiResponseDto.getConditionHy(),
                monumentAiResponseDto.getConditionEn(),
                monumentAiResponseDto.getConditionFr(),
                monumentRequestDto.getImages(),
                monumentRequestDto.getVideos(),
                monumentRequestDto.getMeasurements(),
                new ArrayList<>(),
                null,
                null,
                null,
                monumentRequestDto.getSignature()
            );
            monument.setBibliography(
                monumentRequestDto.getBibliography()
                    .stream()
                    .map(bibliographyRequestDto -> new Bibliography(
                        monument,
                        bibliographyRequestDto.getTitle(),
                        bibliographyRequestDto.getUrl())
                    )
                    .toList()
            );

            Topographic topographic = new Topographic(
                monument,
                monumentAiResponseDto.getProvinceHy(),
                monumentAiResponseDto.getProvinceEn(),
                monumentAiResponseDto.getProvinceFr(),
                monumentAiResponseDto.getAddressHy(),
                monumentAiResponseDto.getAddressEn(),
                monumentAiResponseDto.getAddressFr(),
                monumentAiResponseDto.getTopographyHy(),
                monumentAiResponseDto.getTopographyEn(),
                monumentAiResponseDto.getTopographyFr(),
                monumentAiResponseDto.getDistanceFromResidenceHy(),
                monumentAiResponseDto.getDistanceFromResidenceEn(),
                monumentAiResponseDto.getDistanceFromResidenceFr(),
                monumentRequestDto.getTopographics().getLatitude(),
                monumentRequestDto.getTopographics().getLongitude(),
                monumentRequestDto.getTopographics().getAltitude(),
                monumentAiResponseDto.getHydrographyHy(),
                monumentAiResponseDto.getHydrographyEn(),
                monumentAiResponseDto.getHydrographyFr(),
                monumentAiResponseDto.getDescriptionHy(),
                monumentAiResponseDto.getDescriptionEn(),
                monumentAiResponseDto.getDescriptionFr()
            );
            monument.setTopographics(topographic);

            HistoricalReference historicalReference = new HistoricalReference(
                monument,
                monumentAiResponseDto.getCulturalAffiliationHy(),
                monumentAiResponseDto.getOriginalAffiliationEn(),
                monumentAiResponseDto.getOriginalAffiliationFr(),
                monumentAiResponseDto.getCenturyHy(),
                monumentAiResponseDto.getCenturyEn(),
                monumentAiResponseDto.getCenturyFr(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyHy(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyEn(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyFr(),
                monumentAiResponseDto.getChronologicalTableOfTheStudHy(),
                monumentAiResponseDto.getChronologicalTableOfTheStudEn(),
                monumentAiResponseDto.getChronologicalTableOfTheStudFr(),
                monumentAiResponseDto.getAuthorHy(),
                monumentAiResponseDto.getAuthorEn(),
                monumentAiResponseDto.getAuthorFr()
            );
            monument.setHistoricalReferences(historicalReference);

            DescriptiveCharacteristicReference descriptiveCharacteristicReference = new DescriptiveCharacteristicReference(
                monument,
                monumentAiResponseDto.getTheBuildingMaterialHy(),
                monumentAiResponseDto.getTheBuildingMaterialEn(),
                monumentAiResponseDto.getTheBuildingMaterialFr(),
                monumentAiResponseDto.getTypeHy(),
                monumentAiResponseDto.getTypeEn(),
                monumentAiResponseDto.getTypeFr(),
                monumentAiResponseDto.getColorHy(),
                monumentAiResponseDto.getColorEn(),
                monumentAiResponseDto.getColorFr(),
                monumentAiResponseDto.getImplementationTechniqueHy(),
                monumentAiResponseDto.getImplementationTechniqueEn(),
                monumentAiResponseDto.getImplementationTechniqueFr(),
                monumentAiResponseDto.getStateOfMonumentHy(),
                monumentAiResponseDto.getStateOfMonumentEn(),
                monumentAiResponseDto.getStateOfMonumentFr(),
                monumentAiResponseDto.getValuationHy(),
                monumentAiResponseDto.getValuationEn(),
                monumentAiResponseDto.getValuationFr()
            );
            monument.setDescriptiveCharacteristics(descriptiveCharacteristicReference);
            Monument savedMonument = monumentRepository.save(monument);
            return MonumentMapper.toDto(savedMonument);
        } else {
          throw new RuntimeException("MonumentAiResponseDto is null");
        }
    }

    public List<MonumentResponseDto> filter(
            MonumentFilterRequest request
    ) {

        if (request.getSettlementId() != null
                && request.getRegionId() == null) {

            throw new IllegalArgumentException(
                    "Settlement cannot be used without region"
            );
        }

        Specification<Monument> spec = (root, query, cb) -> cb.conjunction();

        if (request.getRegionId() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("region").get("id"),
                                    request.getRegionId()
                            )
            );
        }

        if (request.getSettlementId() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("settlement").get("id"),
                                    request.getSettlementId()
                            )
            );
        }

        if (request.getMonumentType() != null && !request.getMonumentType().isBlank()) {

            String monumentType = request.getMonumentType().trim().toLowerCase();

            spec = spec.and((root, query, cb) ->
                cb.or(
                    cb.equal(cb.lower(root.get("monumentTypeHy")), monumentType),
                    cb.equal(cb.lower(root.get("monumentTypeEn")), monumentType),
                    cb.equal(cb.lower(root.get("monumentTypeFr")), monumentType)
                )
            );
        }

        return monumentRepository
                .findAll(spec, Sort.unsorted())
                .stream()
                .map(MonumentMapper::toDto)
                .toList();
    }

    public List<String> generateImagesPaths(List<MultipartFile> files) {
        if (files != null) {
            return files.stream()
                .map(file -> fileStorageService.saveImage(file, "monument_images"))
                .toList();
        }
        return null;
    }

    public List<String> generateMeasurementsPaths(List<MultipartFile> files) {
        if (files != null) {
            return files.stream()
                .map(file -> fileStorageService.saveImage(file, "monument_measurements"))
                .toList();
        }
        return null;
    }

    private String extractJson(String response) {
        var start = response.indexOf('{');
        var end = response.lastIndexOf('}');
        if (start == -1 || end == -1 || start >= end) {
            throw new IllegalArgumentException("No valid JSON found in response");
        }
        return response.substring(start, end + 1);
    }
}