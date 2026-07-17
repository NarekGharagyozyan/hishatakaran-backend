package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.Footnote;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentTypes;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.mapper.MonumentTypeMapper;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.model.MonumentEditDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentTypeEditDto;
import org.hishatakaran.backend.model.MonumentTypeRequestDto;
import org.hishatakaran.backend.model.MonumentTypeTranslateDto;
import org.hishatakaran.backend.model.MonumentTypesResponseDto;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.MonumentTypesRepository;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final MonumentTypesRepository monumentTypesRepository;
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
            .isPublished(Boolean.FALSE)
            .nameHy(monumentRequestDto.getName())
            .monumentType(monumentTypesRepository.findById(monumentRequestDto.getMonumentTypeId()).orElseThrow(
                () -> new RuntimeException("There is no monument type with that id.")
            ))
            .specialNameHy(monumentRequestDto.getSpecialName())
            .region(regionRepository.findById(monumentRequestDto.getRegionId()).orElseThrow(
                () -> new RuntimeException("There is no region with that id.")))
            .settlement(settlementRepository.findById(monumentRequestDto.getSettlementId()).orElseThrow(
                () -> new RuntimeException("There is no settlement with that id.")))
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

        List<Footnote> footnotes = null;
        if(monumentRequestDto.getFootnotes() != null){
            footnotes = monumentRequestDto.getFootnotes()
                .stream()
                .map(footnoteRequestDto -> new Footnote(
                    monument,
                    footnoteRequestDto.getOrderNumber(),
                    footnoteRequestDto.getText(),
                    null,
                    null
                ))
                .toList();
        }

        List<Bibliography> bibliographies = null;
        if (monumentRequestDto.getBibliography() != null)
        {
            bibliographies = monumentRequestDto.getBibliography()
                .stream()
                .map(bibliographyRequestDto -> new Bibliography(
                    monument,
                    bibliographyRequestDto.getTitle(),
                    null,
                    null,
                    bibliographyRequestDto.getUrl())
                )
                .toList();
        }

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

        monument.setFootnotes(footnotes);
        monument.setBibliography(bibliographies);
        monument.setTopographics(topographic);
        monument.setHistoricalReferences(historicalReference);
        monument.setDescriptiveCharacteristics(descriptiveCharacteristicReference);

        if (monumentRequestDto.getVideos() != null)
        {
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
        }
        Monument savedMonument = monumentRepository.save(monument);
        return MonumentMapper.toDto(savedMonument);
    }

    @Transactional
    public MonumentResponseDto updateMonument(Long id, MonumentEditDto monumentEditDto) {
        Monument monument = monumentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Monument not found"));

        if (monumentEditDto.getName() != null)
        {
            monument.setNameHy(monumentEditDto.getName().getHy());
            monument.setNameEn(monumentEditDto.getName().getEn());
            monument.setNameFr(monumentEditDto.getName().getFr());
        }

        if (monumentEditDto.getSpecialName() != null){
            monument.setSpecialNameHy(monumentEditDto.getSpecialName().getHy());
            monument.setSpecialNameEn(monumentEditDto.getSpecialName().getEn());
            monument.setSpecialNameFr(monumentEditDto.getSpecialName().getFr());
        }

        if (monumentEditDto.getAnotherNames() != null)
        {
            monument.setAnotherNamesHy(monumentEditDto.getAnotherNames().getHy());
            monument.setAnotherNamesEn(monumentEditDto.getAnotherNames().getEn());
            monument.setAnotherNamesFr(monumentEditDto.getAnotherNames().getFr());
        }

        monument.setMonumentType(monumentTypesRepository.findById(monumentEditDto.getMonumentTypeId()).orElseThrow(() -> new RuntimeException("Monument type not found")));
        monument.setRegion(regionRepository.findById(monumentEditDto.getRegionId()).orElseThrow(() -> new RuntimeException("Region not found")));
        monument.setSettlement(settlementRepository.findById(monumentEditDto.getSettlementId()).orElseThrow(() -> new RuntimeException("Settlement not found")));

        if(monumentEditDto.getHistory() != null)
        {
            monument.setHistoryHy(monumentEditDto.getHistory().getHy());
            monument.setHistoryEn(monumentEditDto.getHistory().getEn());
            monument.setHistoryFr(monumentEditDto.getHistory().getFr());
        }

        if (monumentEditDto.getOriginalAffiliation() != null)
        {
            monument.setOriginalAffiliationHy(monumentEditDto.getOriginalAffiliation().getHy());
            monument.setOriginalAffiliationEn(monumentEditDto.getOriginalAffiliation().getEn());
            monument.setOriginalAffiliationFr(monumentEditDto.getOriginalAffiliation().getFr());
        }

        if(monumentEditDto.getStorageUnitName() != null)
        {
            monument.setStorageUnitNameHy(monumentEditDto.getStorageUnitName().getHy());
            monument.setStorageUnitNameEn(monumentEditDto.getStorageUnitName().getEn());
            monument.setStorageUnitNameFr(monumentEditDto.getStorageUnitName().getFr());
        }

        if (monumentEditDto.getCondition() != null)
        {
            monument.setConditionHy(monumentEditDto.getCondition().getHy());
            monument.setConditionEn(monumentEditDto.getCondition().getEn());
            monument.setConditionFr(monumentEditDto.getCondition().getFr());
        }

        monument.setImages(monumentEditDto.getImages());
        monument.setMeasurements(monumentEditDto.getMeasurements());

        monument.getVideos().clear();
        monumentEditDto.getVideos()
            .stream()
            .filter(dto -> dto.getTitle() != null)
            .map(dto -> new MonumentVideo(
                monument,
                dto.getTitle().getHy(),
                dto.getTitle().getEn(),
                dto.getTitle().getFr(),
                dto.getUrl()
            ))
            .forEach(monument.getVideos()::add);

        monument.getBibliography().clear();
        monumentEditDto.getBibliography()
            .stream()
            .filter(dto -> dto.getTitle() != null)
            .map(dto -> new Bibliography(
                monument,
                dto.getTitle().getHy(),
                dto.getTitle().getEn(),
                dto.getTitle().getFr(),
                dto.getUrl()
            ))
            .forEach(monument.getBibliography()::add);

        Topographic topographics = monument.getTopographics();

        if (topographics == null)
        {
            topographics = new Topographic();
            topographics.setMonument(monument);
            monument.setTopographics(topographics);
        }

        monument.getFootnotes().clear();
        if (monumentEditDto.getFootnotes() != null)
        {
            monumentEditDto.getFootnotes()
                .stream()
                .map(footnoteResponseDto -> new Footnote(
                    monument,
                    footnoteResponseDto.getOrderNumber(),
                    footnoteResponseDto.getText().getHy(),
                    footnoteResponseDto.getText().getEn(),
                    footnoteResponseDto.getText().getFr()
                ))
                .forEach(monument.getFootnotes()::add);
        }

        topographics.setRegionHy(
            monumentEditDto.getTopographics().getRegion() != null
            ? monumentEditDto.getTopographics().getRegion().getHy()
                : null);
        topographics.setRegionEn(
            monumentEditDto.getTopographics().getRegion() != null
            ? monumentEditDto.getTopographics().getRegion().getEn()
                : null);
        topographics.setRegionFr(
            monumentEditDto.getTopographics().getRegion() != null
            ? monumentEditDto.getTopographics().getRegion().getFr()
                : null);

        topographics.setAddressHy(
            monumentEditDto.getTopographics().getAddress() != null
                ? monumentEditDto.getTopographics().getAddress().getHy()
                : null);
        topographics.setAddressEn(
            monumentEditDto.getTopographics().getAddress() != null
                ? monumentEditDto.getTopographics().getAddress().getEn()
                : null);
        topographics.setAddressFr(
            monumentEditDto.getTopographics().getAddress() != null
                ? monumentEditDto.getTopographics().getAddress().getFr()
                : null);

        topographics.setTopographyHy(
            monumentEditDto.getTopographics().getRegion() != null
                ? monumentEditDto.getTopographics().getRegion().getHy()
                : null);
        topographics.setTopographyEn(
            monumentEditDto.getTopographics().getRegion() != null
                ? monumentEditDto.getTopographics().getRegion().getEn()
                : null);
        topographics.setTopographyFr(
            monumentEditDto.getTopographics().getRegion() != null
                ? monumentEditDto.getTopographics().getRegion().getFr()
                : null);

        topographics.setDistanceFromResidenceHy(
            monumentEditDto.getTopographics().getDistanceFromResidence() != null
                ? monumentEditDto.getTopographics().getDistanceFromResidence().getHy()
                : null);
        topographics.setDistanceFromResidenceEn(
            monumentEditDto.getTopographics().getDistanceFromResidence() != null
                ? monumentEditDto.getTopographics().getDistanceFromResidence().getEn()
                : null);
        topographics.setDistanceFromResidenceFr(
            monumentEditDto.getTopographics().getDistanceFromResidence() != null
                ? monumentEditDto.getTopographics().getDistanceFromResidence().getFr()
                : null);

        topographics.setLatitude(monumentEditDto.getTopographics().getLatitude());
        topographics.setLongitude(monumentEditDto.getTopographics().getLongitude());

        topographics.setAltitudeHy(
            monumentEditDto.getTopographics().getAltitude() != null
                ? monumentEditDto.getTopographics().getAltitude().getHy()
                : null);
        topographics.setAltitudeEn(
            monumentEditDto.getTopographics().getAltitude() != null
                ? monumentEditDto.getTopographics().getAltitude().getEn()
                : null);
        topographics.setAltitudeFr(
            monumentEditDto.getTopographics().getAltitude() != null
                ? monumentEditDto.getTopographics().getAltitude().getFr()
                : null);

        topographics.setHydrographyHy(
            monumentEditDto.getTopographics().getHydrography() != null
                ? monumentEditDto.getTopographics().getHydrography().getHy()
                : null);
        topographics.setHydrographyEn(
            monumentEditDto.getTopographics().getHydrography() != null
                ? monumentEditDto.getTopographics().getHydrography().getEn()
                : null);
        topographics.setHydrographyFr(
            monumentEditDto.getTopographics().getHydrography() != null
                ? monumentEditDto.getTopographics().getHydrography().getFr()
                : null);

        topographics.setDescriptionHy(
            monumentEditDto.getTopographics().getDescription() != null
                ? monumentEditDto.getTopographics().getDescription().getHy()
                : null);
        topographics.setDescriptionEn(
            monumentEditDto.getTopographics().getDescription() != null
                ? monumentEditDto.getTopographics().getDescription().getEn()
                : null);
        topographics.setDescriptionFr(
            monumentEditDto.getTopographics().getDescription() != null
                ? monumentEditDto.getTopographics().getDescription().getFr()
                : null);

        HistoricalReference historical = monument.getHistoricalReferences();

        if (historical == null) {
            historical = new HistoricalReference();
            historical.setMonument(monument);
            monument.setHistoricalReferences(historical);
        }

        historical.setCulturalAffiliationHy(
            monumentEditDto.getHistoricalReferences().getCulturalAffiliation() != null
                ? monumentEditDto.getHistoricalReferences().getCulturalAffiliation().getHy()
                : null);
        historical.setCulturalAffiliationEn(
            monumentEditDto.getHistoricalReferences().getCulturalAffiliation() != null
                ? monumentEditDto.getHistoricalReferences().getCulturalAffiliation().getEn()
                : null);
        historical.setCulturalAffiliationFr(
            monumentEditDto.getHistoricalReferences().getCulturalAffiliation() != null
                ? monumentEditDto.getHistoricalReferences().getCulturalAffiliation().getFr()
                : null);

        historical.setCenturyHy(
            monumentEditDto.getHistoricalReferences().getCentury() != null
                ? monumentEditDto.getHistoricalReferences().getCentury().getHy()
                : null);
        historical.setCenturyEn(
            monumentEditDto.getHistoricalReferences().getCentury() != null
                ? monumentEditDto.getHistoricalReferences().getCentury().getEn()
                : null);
        historical.setCenturyFr(
            monumentEditDto.getHistoricalReferences().getCentury() != null
                ? monumentEditDto.getHistoricalReferences().getCentury().getFr()
                : null);

        historical.setJustificationOfTheNumberingBasedOnLithographyHy(
            monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography() != null
                ? monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography().getHy()
                : null);
        historical.setJustificationOfTheNumberingBasedOnLithographyEn(
            monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography() != null
                ? monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography().getEn()
                : null);
        historical.setJustificationOfTheNumberingBasedOnLithographyFr(
            monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography() != null
                ? monumentEditDto.getHistoricalReferences().getJustificationOfTheNumberingBasedOnLithography().getFr()
                : null);

        historical.setChronologicalTableOfTheStudHy(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud().getHy()
                : null);
        historical.setChronologicalTableOfTheStudEn(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud().getEn()
                : null);
        historical.setChronologicalTableOfTheStudFr(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheStud().getFr()
                : null);

        historical.setChronologicalTableOfTheMonumentsStudyHy(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy().getHy()
                : null);
        historical.setChronologicalTableOfTheMonumentsStudyEn(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy().getEn()
                : null);
        historical.setChronologicalTableOfTheMonumentsStudyFr(
            monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy() != null
                ? monumentEditDto.getHistoricalReferences().getChronologicalTableOfTheMonumentsStudy().getFr()
                : null);

        historical.setAuthorHy(
            monumentEditDto.getHistoricalReferences().getAuthor() != null
                ? monumentEditDto.getHistoricalReferences().getAuthor().getHy()
                : null);
        historical.setAuthorEn(
            monumentEditDto.getHistoricalReferences().getAuthor() != null
                ? monumentEditDto.getHistoricalReferences().getAuthor().getEn()
                : null);
        historical.setAuthorFr(
            monumentEditDto.getHistoricalReferences().getAuthor() != null
                ? monumentEditDto.getHistoricalReferences().getAuthor().getFr()
                : null);

        DescriptiveCharacteristicReference descriptive = monument.getDescriptiveCharacteristics();

        if (descriptive == null) {
            descriptive = new DescriptiveCharacteristicReference();
            descriptive.setMonument(monument);
            monument.setDescriptiveCharacteristics(descriptive);
        }

        descriptive.setTheBuildingMaterialHy(
            monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial().getHy()
                : null);
        descriptive.setTheBuildingMaterialEn(
            monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial().getEn()
                : null);
        descriptive.setTheBuildingMaterialFr(
            monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getBuildingMaterial().getFr()
                : null);

        descriptive.setOpeningsEntrancesHy(
            monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances().getHy()
                : null);
        descriptive.setOpeningsEntrancesEn(
            monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances().getEn()
                : null);
        descriptive.setOpeningsEntrancesFr(
            monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getOpeningsEntrances().getFr()
                : null);

        descriptive.setConstructionsHy(
            monumentEditDto.getDescriptiveCharacteristics().getConstructions() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getConstructions().getHy()
                : null);
        descriptive.setConstructionsEn(
            monumentEditDto.getDescriptiveCharacteristics().getConstructions() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getConstructions().getEn()
                : null);
        descriptive.setConstructionsFr(
            monumentEditDto.getDescriptiveCharacteristics().getConstructions() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getConstructions().getFr()
                : null);

        descriptive.setRoofHy(
            monumentEditDto.getDescriptiveCharacteristics().getRoof() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getRoof().getHy()
                : null);
        descriptive.setRoofEn(
            monumentEditDto.getDescriptiveCharacteristics().getRoof() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getRoof().getEn()
                : null);
        descriptive.setRoofFr(
            monumentEditDto.getDescriptiveCharacteristics().getRoof() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getRoof().getFr()
                : null);

        descriptive.setTypeHy(
            monumentEditDto.getDescriptiveCharacteristics().getType() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getType().getHy()
                : null);
        descriptive.setTypeEn(
            monumentEditDto.getDescriptiveCharacteristics().getType() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getType().getEn()
                : null);
        descriptive.setTypeFr(
            monumentEditDto.getDescriptiveCharacteristics().getType() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getType().getFr()
                : null);

        descriptive.setColorHy(
            monumentEditDto.getDescriptiveCharacteristics().getColor() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getColor().getHy()
                : null);
        descriptive.setColorEn(
            monumentEditDto.getDescriptiveCharacteristics().getColor() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getColor().getEn()
                : null);
        descriptive.setColorFr(
            monumentEditDto.getDescriptiveCharacteristics().getColor() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getColor().getFr()
                : null);

        descriptive.setImplementationTechniqueHy(
            monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique().getHy()
                : null);
        descriptive.setImplementationTechniqueEn(
            monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique().getEn()
                : null);
        descriptive.setImplementationTechniqueFr(
            monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getImplementationTechnique().getFr()
                : null);

        descriptive.setStateOfMonumentHy(
            monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument().getHy()
                : null);
        descriptive.setStateOfMonumentEn(
            monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument().getEn()
                : null);
        descriptive.setStateOfMonumentFr(
            monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getStateOfMonument().getFr()
                : null);

        descriptive.setValuationHy(
            monumentEditDto.getDescriptiveCharacteristics().getValuation() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getValuation().getHy()
                : null);
        descriptive.setValuationEn(
            monumentEditDto.getDescriptiveCharacteristics().getValuation() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getValuation().getEn()
                : null);
        descriptive.setValuationFr(
            monumentEditDto.getDescriptiveCharacteristics().getValuation() != null
                ? monumentEditDto.getDescriptiveCharacteristics().getValuation().getFr()
                : null);

        monument.setSignature(monumentEditDto.getSignature());
        Monument editedMonument = monumentRepository.save(monument);
        return MonumentMapper.toDto(editedMonument);
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

    public MonumentTypesResponseDto editMonumentType(Long monumentTypeId, MonumentTypeEditDto monumentTypeEditDto) {
        MonumentTypes monumentType = monumentTypesRepository.findById(monumentTypeId).orElseThrow(
            () -> new RuntimeException("Monument type not found"));
        monumentType.setNameHy(monumentTypeEditDto.getName().getHy());
        monumentType.setNameEn(monumentTypeEditDto.getName().getEn());
        monumentType.setNameFr(monumentTypeEditDto.getName().getFr());
        MonumentTypes editedMonumentType = monumentTypesRepository.save(monumentType);
        return MonumentTypeMapper.toDto(editedMonumentType);
    }

    public MonumentTypesResponseDto createNewMonumentType(MonumentTypeRequestDto monumentTypeRequestDto)
    {
        MonumentTypeTranslateDto monumentTypeTranslateDto;
        try {
            monumentTypeTranslateDto = geminiService.translateMonumentType(
                monumentTypeRequestDto.getName()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to translate monument type");
        }

        MonumentTypes monumentType = new MonumentTypes(
            monumentTypeTranslateDto.getNameHy(),
            monumentTypeTranslateDto.getNameEn(),
            monumentTypeTranslateDto.getNameFr()
        );
        return MonumentTypeMapper.toDto(monumentTypesRepository.save(monumentType));
    }


    @Transactional
    public MonumentResponseDto publish(
        Long id
    ) {
        Monument monument = monumentRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Monument not found"));
        monument.setIsPublished(!monument.getIsPublished());
        Monument updatedMonument = monumentRepository.save(monument);
        return MonumentMapper.toDto(updatedMonument);
    }
}