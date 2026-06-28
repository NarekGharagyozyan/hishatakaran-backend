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
                monumentRequestDto.getPictures(),
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
                monumentAiResponseDto.getProvinceArmenian(),
                monumentAiResponseDto.getProvinceEnglish(),
                monumentAiResponseDto.getProvinceFrench(),
                monumentAiResponseDto.getAddressArmenian(),
                monumentAiResponseDto.getAddressEnglish(),
                monumentAiResponseDto.getAddressFrench(),
                monumentAiResponseDto.getTopographyArmenian(),
                monumentAiResponseDto.getTopographyEnglish(),
                monumentAiResponseDto.getTopographyFrench(),
                monumentAiResponseDto.getDistanceFromResidenceArmenian(),
                monumentAiResponseDto.getDistanceFromResidenceEnglish(),
                monumentAiResponseDto.getDistanceFromResidenceFrench(),
                monumentRequestDto.getTopographics().getLatitude(),
                monumentRequestDto.getTopographics().getLongitude(),
                monumentRequestDto.getTopographics().getAltitude(),
                monumentAiResponseDto.getHydrographyArmenian(),
                monumentAiResponseDto.getHydrographyEnglish(),
                monumentAiResponseDto.getHydrographyFrench(),
                monumentAiResponseDto.getDescriptionArmenian(),
                monumentAiResponseDto.getDescriptionEnglish(),
                monumentAiResponseDto.getDescriptionFrench()
            );
            monument.setTopographics(topographic);

            HistoricalReference historicalReference = new HistoricalReference(
                monument,
                monumentAiResponseDto.getCulturalAffiliationArmenian(),
                monumentAiResponseDto.getOriginalAffiliationEnglish(),
                monumentAiResponseDto.getOriginalAffiliationFrench(),
                monumentAiResponseDto.getCenturyArmenian(),
                monumentAiResponseDto.getCenturyEnglish(),
                monumentAiResponseDto.getCenturyFrench(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyArmenian(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyEnglish(),
                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyFrench(),
                monumentAiResponseDto.getChronologicalTableOfTheStudArmenian(),
                monumentAiResponseDto.getChronologicalTableOfTheStudEnglish(),
                monumentAiResponseDto.getChronologicalTableOfTheStudFrench(),
                monumentAiResponseDto.getAuthorArmenian(),
                monumentAiResponseDto.getAuthorEnglish(),
                monumentAiResponseDto.getAuthorFrench()
            );
            monument.setHistoricalReferences(historicalReference);

            DescriptiveCharacteristicReference descriptiveCharacteristicReference = new DescriptiveCharacteristicReference(
                monument,
                monumentAiResponseDto.getTheBuildingMaterialArmenian(),
                monumentAiResponseDto.getTheBuildingMaterialEnglish(),
                monumentAiResponseDto.getTheBuildingMaterialFrench(),
                monumentAiResponseDto.getTypeArmenian(),
                monumentAiResponseDto.getTypeEnglish(),
                monumentAiResponseDto.getTypeFrench(),
                monumentAiResponseDto.getColorArmenian(),
                monumentAiResponseDto.getColorEnglish(),
                monumentAiResponseDto.getColorFrench(),
                monumentAiResponseDto.getImplementationTechniqueArmenian(),
                monumentAiResponseDto.getImplementationTechniqueEnglish(),
                monumentAiResponseDto.getImplementationTechniqueFrench(),
                monumentAiResponseDto.getStateOfMonumentArmenian(),
                monumentAiResponseDto.getStateOfMonumentEnglish(),
                monumentAiResponseDto.getStateOfMonumentFrench(),
                monumentAiResponseDto.getValuationArmenian(),
                monumentAiResponseDto.getValuationEnglish(),
                monumentAiResponseDto.getValuationFrench()
            );
            monument.setDescriptiveCharacteristics(descriptiveCharacteristicReference);
            Monument savedMonument = monumentRepository.save(monument);
            System.out.println("saved monument:  === " + savedMonument);
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

    public List<String> generateImagePaths(List<MultipartFile> files) {
        if (files != null) {
            return files.stream()
                .map(file -> fileStorageService.saveImage(file, "monuments"))
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