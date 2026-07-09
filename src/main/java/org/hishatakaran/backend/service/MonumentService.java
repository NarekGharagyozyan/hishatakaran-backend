package org.hishatakaran.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.model.MonumentAiResponseDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.Status;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonumentService {

    private final MonumentRepository monumentRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final RegionRepository regionRepository;
    private final SettlementRepository settlementRepository;
    private final MonumentTranslationService monumentTranslationService;
    private final FileStorageService fileStorageService;

//    public MonumentResponseDto postMonument(MonumentRequestDto monumentRequestDto) {
//        String requestGeminiForMonuments = geminiService.requestGeminiForMonuments(monumentRequestDto);
//        MonumentAiResponseDto monumentAiResponseDto = null;
//        try {
//            var cleanJson = extractJson(requestGeminiForMonuments);
//            monumentAiResponseDto = objectMapper.readValue(cleanJson, MonumentAiResponseDto.class);
//        }
//        catch (JsonProcessingException e)
//        {
//            e.printStackTrace();
//        }
//        if (monumentAiResponseDto != null)
//        {
//
//            Monument monument = new Monument(
//                Status.DRAFT,
//                monumentAiResponseDto.getNameHy(),
//                monumentAiResponseDto.getNameEn(),
//                monumentAiResponseDto.getNameFr(),
//                regionRepository.findById(monumentRequestDto.getRegionId()).orElse(null),
//                settlementRepository.findById(monumentRequestDto.getSettlementId()).orElse(null),
//                monumentAiResponseDto.getMonumentTypeHy(),
//                monumentAiResponseDto.getMonumentTypeEn(),
//                monumentAiResponseDto.getMonumentTypeFr(),
//                monumentAiResponseDto.getSpecialNameHy(),
//                monumentAiResponseDto.getSpecialNameEn(),
//                monumentAiResponseDto.getSpecialNameFr(),
//                monumentAiResponseDto.getAnotherNamesHy(),
//                monumentAiResponseDto.getAnotherNamesEn(),
//                monumentAiResponseDto.getAnotherNamesFr(),
//                monumentAiResponseDto.getHistoryHy(),
//                monumentAiResponseDto.getHistoryEn(),
//                monumentAiResponseDto.getHistoryFr(),
//                monumentAiResponseDto.getOriginalAffiliationHy(),
//                monumentAiResponseDto.getOriginalAffiliationEn(),
//                monumentAiResponseDto.getOriginalAffiliationFr(),
//                monumentAiResponseDto.getStorageUnitNameHy(),
//                monumentAiResponseDto.getStorageUnitNameEn(),
//                monumentAiResponseDto.getStorageUnitNameFr(),
//                monumentAiResponseDto.getConditionHy(),
//                monumentAiResponseDto.getConditionEn(),
//                monumentAiResponseDto.getConditionFr(),
//                monumentRequestDto.getImages(),
//                new ArrayList<>(),
//                monumentRequestDto.getMeasurements(),
//                new ArrayList<>(),
//                null,
//                null,
//                null,
//                monumentRequestDto.getSignature()
//            );
//            monument.setBibliography(
//                monumentRequestDto.getBibliography()
//                    .stream()
//                    .map(bibliographyRequestDto -> new Bibliography(
//                        monument,
//                        bibliographyRequestDto.getTitle(),
//                        bibliographyRequestDto.getUrl())
//                    )
//                    .toList()
//            );
//
//            Topographic topographic = new Topographic(
//                monument,
//                monumentAiResponseDto.getTopographicRegionHy(),
//                monumentAiResponseDto.getTopographicRegionEn(),
//                monumentAiResponseDto.getTopographicRegionFr(),
//                monumentAiResponseDto.getAddressHy(),
//                monumentAiResponseDto.getAddressEn(),
//                monumentAiResponseDto.getAddressFr(),
//                monumentAiResponseDto.getTopographyHy(),
//                monumentAiResponseDto.getTopographyEn(),
//                monumentAiResponseDto.getTopographyFr(),
//                monumentAiResponseDto.getDistanceFromResidenceHy(),
//                monumentAiResponseDto.getDistanceFromResidenceEn(),
//                monumentAiResponseDto.getDistanceFromResidenceFr(),
//                monumentRequestDto.getTopographics().getLatitude(),
//                monumentRequestDto.getTopographics().getLongitude(),
//                monumentRequestDto.getTopographics().getAltitude(),
//                monumentAiResponseDto.getHydrographyHy(),
//                monumentAiResponseDto.getHydrographyEn(),
//                monumentAiResponseDto.getHydrographyFr(),
//                monumentAiResponseDto.getDescriptionHy(),
//                monumentAiResponseDto.getDescriptionEn(),
//                monumentAiResponseDto.getDescriptionFr()
//            );
//            monument.setTopographics(topographic);
//
//            HistoricalReference historicalReference = new HistoricalReference(
//                monument,
//                monumentAiResponseDto.getCulturalAffiliationHy(),
//                monumentAiResponseDto.getCulturalAffiliationEn(),
//                monumentAiResponseDto.getCulturalAffiliationFr(),
//                monumentAiResponseDto.getCenturyHy(),
//                monumentAiResponseDto.getCenturyEn(),
//                monumentAiResponseDto.getCenturyFr(),
//                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyHy(),
//                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyEn(),
//                monumentAiResponseDto.getJustificationOfTheNumberingBasedOnLithographyFr(),
//                monumentAiResponseDto.getChronologicalTableOfTheStudHy(),
//                monumentAiResponseDto.getChronologicalTableOfTheStudEn(),
//                monumentAiResponseDto.getChronologicalTableOfTheStudFr(),
//                monumentAiResponseDto.getChronologicalTableOfTheMonumentsStudyHy(),
//                monumentAiResponseDto.getChronologicalTableOfTheMonumentsStudyEn(),
//                monumentAiResponseDto.getChronologicalTableOfTheMonumentsStudyFr(),
//                monumentAiResponseDto.getAuthorHy(),
//                monumentAiResponseDto.getAuthorEn(),
//                monumentAiResponseDto.getAuthorFr()
//            );
//            monument.setHistoricalReferences(historicalReference);
//
//            DescriptiveCharacteristicReference descriptiveCharacteristicReference = new DescriptiveCharacteristicReference(
//                monument,
//                monumentAiResponseDto.getTheBuildingMaterialHy(),
//                monumentAiResponseDto.getTheBuildingMaterialEn(),
//                monumentAiResponseDto.getTheBuildingMaterialFr(),
//                monumentAiResponseDto.getOpeningsEntrancesHy(),
//                monumentAiResponseDto.getOpeningsEntrancesEn(),
//                monumentAiResponseDto.getOpeningsEntrancesFr(),
//                monumentAiResponseDto.getConstructionsHy(),
//                monumentAiResponseDto.getConstructionsEn(),
//                monumentAiResponseDto.getConstructionsFr(),
//                monumentAiResponseDto.getRoofHy(),
//                monumentAiResponseDto.getRoofEn(),
//                monumentAiResponseDto.getRoofFr(),
//                monumentAiResponseDto.getTypeHy(),
//                monumentAiResponseDto.getTypeEn(),
//                monumentAiResponseDto.getTypeFr(),
//                monumentAiResponseDto.getColorHy(),
//                monumentAiResponseDto.getColorEn(),
//                monumentAiResponseDto.getColorFr(),
//                monumentAiResponseDto.getImplementationTechniqueHy(),
//                monumentAiResponseDto.getImplementationTechniqueEn(),
//                monumentAiResponseDto.getImplementationTechniqueFr(),
//                monumentAiResponseDto.getStateOfMonumentHy(),
//                monumentAiResponseDto.getStateOfMonumentEn(),
//                monumentAiResponseDto.getStateOfMonumentFr(),
//                monumentAiResponseDto.getValuationHy(),
//                monumentAiResponseDto.getValuationEn(),
//                monumentAiResponseDto.getValuationFr()
//            );
//            monument.setDescriptiveCharacteristics(descriptiveCharacteristicReference);
//            monument.setVideos(
//                monumentAiResponseDto.getVideos()
//                    .stream()
//                    .map(video -> new MonumentVideo(
//                        monument,
//                        video.getVideoTitleHy(),
//                        video.getVideoTitleEn(),
//                        video.getVideoTitleFr(),
//                        video.getUrl()
//                    ))
//                    .toList()
//            );
//            Monument savedMonument = monumentRepository.save(monument);
//            return MonumentMapper.toDto(savedMonument);
//        } else {
//          throw new RuntimeException("MonumentAiResponseDto is null");
//        }
//    }

    public MonumentResponseDto postMonument(MonumentRequestDto monumentRequestDto) {
        Monument monument = Monument.builder()
            .status(Status.DRAFT)
            .nameHy(monumentRequestDto.getName())
            .monumentTypeHy(monumentRequestDto.getMonumentType())
            .specialNameHy(monumentRequestDto.getSpecialName())
            .region(regionRepository.findById(monumentRequestDto.getRegionId()).orElseThrow(
                () -> new RuntimeException("There is no region with that id.")))
            .settlement(settlementRepository.findById(monumentRequestDto.getSettlementId()).orElseThrow(
                () -> new RuntimeException("There is no region with that id.")))
            .anotherNamesHy(monumentRequestDto.getAnotherNames())
            .historyHy(monumentRequestDto.getHistory())
            .originalAffiliationHy(monumentRequestDto.getOriginalAffiliation())
            .conditionHy(monumentRequestDto.getCondition())
            .measurements(monumentRequestDto.getMeasurements())
            .images(monumentRequestDto.getImages())
            .storageUnitNameHy(monumentRequestDto.getStorageUnitName())
            .signature(monumentRequestDto.getSignature())
            .showInMainPage(monumentRequestDto.getShowInMainPage())
            .build();

        List<Bibliography> bibliographies = monumentRequestDto.getBibliography()
            .stream()
            .map(bibliographyRequestDto -> new Bibliography(
                monument,
                bibliographyRequestDto.getTitle(),
                null,
                null,
                bibliographyRequestDto.getUrl())
            )
            .toList();

        Topographic topographic = Topographic.builder()
            .monument(monument)
            .regionHy(monumentRequestDto.getTopographics().getRegion())
            .topographyHy(monumentRequestDto.getTopographics().getTopography())
            .addressHy(monumentRequestDto.getTopographics().getAddress())
            .latitude(monumentRequestDto.getTopographics().getLatitude())
            .longitude(monumentRequestDto.getTopographics().getLongitude())
            .altitudeHy(monumentRequestDto.getTopographics().getAltitude())
            .descriptionHy(monumentRequestDto.getTopographics().getDescription())
            .distanceFromResidenceHy(monumentRequestDto.getTopographics().getDistanceFromResidence())
            .hydrographyHy(monumentRequestDto.getTopographics().getHydrography())
            .build();

        HistoricalReference historicalReference = HistoricalReference.builder()
            .monument(monument)
            .culturalAffiliationHy(monumentRequestDto.getHistoricalReferences().getCulturalAffiliation())
            .chronologicalTableOfTheStudHy(
                monumentRequestDto.getHistoricalReferences().getChronologicalTableOfTheStud())
            .chronologicalTableOfTheMonumentsStudyHy(
                monumentRequestDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy())
            .centuryHy(monumentRequestDto.getHistoricalReferences().getCentury())
            .authorHy(monumentRequestDto.getHistoricalReferences().getAuthor())
            .justificationOfTheNumberingBasedOnLithographyHy(
                monumentRequestDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography())
            .build();

        DescriptiveCharacteristicReference descriptiveCharacteristicReference = DescriptiveCharacteristicReference.builder()
            .monument(monument)
            .theBuildingMaterialHy(monumentRequestDto.getDescriptiveCharacteristics().getTheBuildingMaterial())
            .colorHy(monumentRequestDto.getDescriptiveCharacteristics().getColor())
            .openingsEntrancesHy(monumentRequestDto.getDescriptiveCharacteristics().getOpeningsEntrances())
            .constructionsHy(monumentRequestDto.getDescriptiveCharacteristics().getConstructions())
            .roofHy(monumentRequestDto.getDescriptiveCharacteristics().getRoof())
            .typeHy(monumentRequestDto.getDescriptiveCharacteristics().getType())
            .implementationTechniqueHy(monumentRequestDto.getDescriptiveCharacteristics().getImplementationTechnique())
            .stateOfMonumentHy(monumentRequestDto.getDescriptiveCharacteristics().getStateOfMonument())
            .valuationHy(monumentRequestDto.getDescriptiveCharacteristics().getValuation())
            .build();

        monument.setBibliography(bibliographies);
        monument.setTopographics(topographic);
        monument.setHistoricalReferences(historicalReference);
        monument.setDescriptiveCharacteristics(descriptiveCharacteristicReference);
        monument.setVideos(
            monumentRequestDto.getVideos()
                .stream()
                .map(video -> new MonumentVideo(
                    monument,
                    video.getTitle(),
                    null,
                    null,
                    video.getUrl()
                ))
                .toList()
        );
        Monument savedMonument = monumentRepository.save(monument);
        return MonumentMapper.toDto(savedMonument);
    }

    @Transactional
    public void deleteMonument(Long id) {
        Monument monument = monumentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Monument not found"));

        deleteFiles(monument.getImages());
        deleteFiles(monument.getMeasurements());

        monumentRepository.delete(monument);
    }

    private void deleteFiles(List<String> paths) {
        if (paths == null) {
            return;
        }

        paths.forEach(fileStorageService::deleteImage);
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

    @Transactional
    public MonumentResponseDto translate(
        Long id,
        TranslationLanguage language
    ) {

        Monument monument =
            monumentRepository.findById(id)
                .orElseThrow(
                    () -> new RuntimeException(
                        "Monument not found"
                    )
                );


        monumentTranslationService.translate(
            monument,
            language
        );


        Monument saved =
            monumentRepository.save(monument);


        return MonumentMapper.toDto(saved);
    }


    @Transactional
    public MonumentResponseDto publish(
        Long id
    ) {
        Monument monument = monumentRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Monument not found"));
        monument.setStatus(Status.PUBLISHED);
        Monument updatedMonument = monumentRepository.save(monument);
        return MonumentMapper.toDto(updatedMonument);
    }
}