package org.hishatakaran.backend.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Region;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentAiResponseDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.NewsAiResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                monumentAiResponseDto.getNameArmenian(),
                monumentAiResponseDto.getNameEnglish(),
                monumentAiResponseDto.getNameFrench(),
                regionRepository.findById(monumentRequestDto.getRegionId()).orElse(null),
                settlementRepository.findById(monumentRequestDto.getSettlementId()).orElse(null),
                monumentAiResponseDto.getMonumentTypeArmenian(),
                monumentAiResponseDto.getMonumentTypeEnglish(),
                monumentAiResponseDto.getMonumentTypeFrench(),
                monumentAiResponseDto.getSpecialNameArmenian(),
                monumentAiResponseDto.getSpecialNameEnglish(),
                monumentAiResponseDto.getSpecialNameFrench(),
                monumentAiResponseDto.getAnotherNamesArmenian(),
                monumentAiResponseDto.getAnotherNamesEnglish(),
                monumentAiResponseDto.getAnotherNamesFrench(),
                monumentAiResponseDto.getProvinceArmenian(),
                monumentAiResponseDto.getProvinceEnglish(),
                monumentAiResponseDto.getProvinceFrench(),
                monumentAiResponseDto.getOriginalAffiliationArmenian(),
                monumentAiResponseDto.getOriginalAffiliationEnglish(),
                monumentAiResponseDto.getOriginalAffiliationFrench(),
                monumentAiResponseDto.getStorageUnitNameArmenian(),
                monumentAiResponseDto.getStorageUnitNameEnglish(),
                monumentAiResponseDto.getStorageUnitNameFrench(),
                monumentAiResponseDto.getConditionArmenian(),
                monumentAiResponseDto.getConditionEnglish(),
                monumentAiResponseDto.getConditionFrench(),
                generateImagePaths(monumentRequestDto.getPictures()),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                monumentRequestDto.getSignature()
            );
            List<Bibliography> bibliographies = monumentRequestDto.getBibliography()
                .stream()
                .map(dto -> new Bibliography(monument, dto.getUrls()))
                .toList();
            monument.setBibliography(bibliographies);

            MonumentAiResponseDto finalMonumentAiResponseDto = monumentAiResponseDto;
            List<Topographic> topographics = monumentRequestDto.getTopographics()
                .stream()
                .map(topographicRequestDto -> new Topographic(
                    monument,
                    finalMonumentAiResponseDto.getProvinceArmenian(),
                    finalMonumentAiResponseDto.getProvinceEnglish(),
                    finalMonumentAiResponseDto.getProvinceFrench(),
                    finalMonumentAiResponseDto.getAddressArmenian(),
                    finalMonumentAiResponseDto.getAddressEnglish(),
                    finalMonumentAiResponseDto.getAddressFrench(),
                    finalMonumentAiResponseDto.getTopographyArmenian(),
                    finalMonumentAiResponseDto.getTopographyEnglish(),
                    finalMonumentAiResponseDto.getTopographyFrench(),
                    finalMonumentAiResponseDto.getDistanceFromResidenceArmenian(),
                    finalMonumentAiResponseDto.getDistanceFromResidenceEnglish(),
                    finalMonumentAiResponseDto.getDistanceFromResidenceFrench(),
                    topographicRequestDto.getAltitude(),
                    finalMonumentAiResponseDto.getHydrographyArmenian(),
                    finalMonumentAiResponseDto.getHydrographyEnglish(),
                    finalMonumentAiResponseDto.getHydrographyFrench(),
                    finalMonumentAiResponseDto.getDescriptionArmenian(),
                    finalMonumentAiResponseDto.getDescriptionEnglish(),
                    finalMonumentAiResponseDto.getDescriptionFrench()
                ))
                .toList();
            monument.setTopographics(topographics);

            MonumentAiResponseDto finalMonumentAiResponseDto1 = monumentAiResponseDto;
            List<HistoricalReference> historicalReferences = monumentRequestDto.getHistoricalReferences()
                .stream()
                .map(historicalReferenceRequestDto -> new HistoricalReference(
                    monument,
                    finalMonumentAiResponseDto1.getCulturalAffiliationArmenian(),
                    finalMonumentAiResponseDto1.getOriginalAffiliationEnglish(),
                    finalMonumentAiResponseDto1.getOriginalAffiliationFrench(),
                    finalMonumentAiResponseDto1.getCenturyArmenian(),
                    finalMonumentAiResponseDto.getCenturyEnglish(),
                    finalMonumentAiResponseDto.getCenturyFrench(),
                    finalMonumentAiResponseDto1.getJustificationOfTheNumberingBasedOnLithographyArmenian(),
                    finalMonumentAiResponseDto1.getJustificationOfTheNumberingBasedOnLithographyEnglish(),
                    finalMonumentAiResponseDto1.getJustificationOfTheNumberingBasedOnLithographyFrench(),
                    finalMonumentAiResponseDto1.getChronologicalTableOfTheStudArmenian(),
                    finalMonumentAiResponseDto1.getChronologicalTableOfTheStudEnglish(),
                    finalMonumentAiResponseDto1.getChronologicalTableOfTheStudFrench(),
                    finalMonumentAiResponseDto.getAuthorArmenian(),
                    finalMonumentAiResponseDto.getAuthorEnglish(),
                    finalMonumentAiResponseDto.getAuthorFrench()
                ))
                .toList();
            monument.setHistoricalReferences(historicalReferences);

            MonumentAiResponseDto finalMonumentAiResponseDto2 = monumentAiResponseDto;
            List<DescriptiveCharacteristicReference> descriptiveCharacteristicReferences = monumentRequestDto.getDescriptiveCharacteristics()
                .stream()
                .map(descriptiveCharacteristicReferenceRequestDto -> new DescriptiveCharacteristicReference(
                    monument,
                    finalMonumentAiResponseDto2.getTheBuildingMaterialArmenian(),
                    finalMonumentAiResponseDto2.getTheBuildingMaterialEnglish(),
                    finalMonumentAiResponseDto2.getTheBuildingMaterialFrench(),
                    finalMonumentAiResponseDto2.getTypeArmenian(),
                    finalMonumentAiResponseDto2.getTypeEnglish(),
                    finalMonumentAiResponseDto2.getTypeFrench(),
                    finalMonumentAiResponseDto2.getColorArmenian(),
                    finalMonumentAiResponseDto2.getColorEnglish(),
                    finalMonumentAiResponseDto2.getColorFrench(),
                    finalMonumentAiResponseDto2.getImplementationTechniqueArmenian(),
                    finalMonumentAiResponseDto2.getImplementationTechniqueEnglish(),
                    finalMonumentAiResponseDto2.getImplementationTechniqueFrench(),
                    finalMonumentAiResponseDto2.getStateOfMonumentArmenian(),
                    finalMonumentAiResponseDto2.getStateOfMonumentEnglish(),
                    finalMonumentAiResponseDto2.getStateOfMonumentFrench(),
                    finalMonumentAiResponseDto2.getValuationArmenian(),
                    finalMonumentAiResponseDto2.getValuationEnglish(),
                    finalMonumentAiResponseDto2.getValuationFrench()
                ))
                .toList();
            monument.setDescriptiveCharacteristics(descriptiveCharacteristicReferences);
            monumentRepository.save(monument);
            return MonumentMapper.toDto(monument);
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

        if (request.getMonumentType() != null) {

            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("monumentType"),
                                    request.getMonumentType()
                            )
            );
        }

        return monumentRepository
                .findAll(spec, Sort.unsorted())
                .stream()
                .map(MonumentMapper::toDto)
                .toList();
    }

    List<String> generateImagePaths(List<MultipartFile> files) {
        if (files != null) {
            return files.stream()
                .map(fileStorageService::saveNewsImage)
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