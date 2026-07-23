package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.Footnote;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentImage;
import org.hishatakaran.backend.entity.MonumentMeasurement;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.BibliographyTranslationDto;
import org.hishatakaran.backend.model.DescriptiveCharacteristicTranslationDto;
import org.hishatakaran.backend.model.FootnoteTranslationDto;
import org.hishatakaran.backend.model.HistoricalReferenceTranslationDto;
import org.hishatakaran.backend.model.ImageAndMeasurementTranslationDto;
import org.hishatakaran.backend.model.MonumentTranslationDto;
import org.hishatakaran.backend.model.TopographicTranslationDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.model.VideoTranslationDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonumentTranslationService {

    private final GeminiService geminiService;


    public void translate(
            Monument monument,
            TranslationLanguage language
    ) {

        MonumentTranslationDto translation =
                geminiService.translateMonument(
                    monument,
                    language
                );


        applyTranslation(
            monument,
            translation,
            language
        );
    }


    private void applyTranslation(
        Monument monument,
        MonumentTranslationDto dto,
        TranslationLanguage language
    ) {

        if (dto == null) {
            return;
        }


        boolean en = language == TranslationLanguage.en;


        if(en){

            monument.setNameEn(clean(dto.getName()));
            monument.setSpecialNameEn(clean(dto.getSpecialName()));
            monument.setAnotherNamesEn(clean(dto.getAnotherNames()));
            monument.setHistoryEn(clean(dto.getHistory()));
            monument.setOriginalAffiliationEn(clean(dto.getOriginalAffiliation()));
            monument.setStorageUnitNameEn(clean(dto.getStorageUnitName()));
            monument.setIndividuallyCertifiablePartsOfTheStorageUnitEn(clean(dto.getIndividuallyCertifiablePartsOfTheStorageUnit()));

        } else {

            monument.setNameFr(clean(dto.getName()));
            monument.setSpecialNameFr(clean(dto.getSpecialName()));
            monument.setAnotherNamesFr(clean(dto.getAnotherNames()));
            monument.setHistoryFr(clean(dto.getHistory()));
            monument.setOriginalAffiliationFr(clean(dto.getOriginalAffiliation()));
            monument.setStorageUnitNameFr(clean(dto.getStorageUnitName()));
            monument.setIndividuallyCertifiablePartsOfTheStorageUnitFr(clean(dto.getIndividuallyCertifiablePartsOfTheStorageUnit()));
        }


        if (dto.getTopographics() != null) {
            applyTopographicTranslation(
                monument.getTopographics(),
                dto.getTopographics(),
                language
            );
        }


        if (dto.getHistoricalReferences() != null) {
            applyHistoricalTranslation(
                monument.getHistoricalReferences(),
                dto.getHistoricalReferences(),
                language
            );
        }


        if (dto.getDescriptiveCharacteristics() != null) {
            applyDescriptiveTranslation(
                monument.getDescriptiveCharacteristics(),
                dto.getDescriptiveCharacteristics(),
                language
            );
        }


        if (dto.getVideos() != null) {
            applyVideosTranslation(
                monument.getVideos(),
                dto.getVideos(),
                language
            );
        }

        if (dto.getImages() != null) {
            applyImagesTranslation(
                monument.getImages(),
                dto.getImages(),
                language
            );
        }

        if (dto.getMeasurements() != null) {
            applyMeasurementTranslation(
                monument.getMeasurements(),
                dto.getMeasurements(),
                language
            );
        }

        if (dto.getFootnotes() != null) {
            applyFootnotesTranslation(
                monument.getFootnotes(),
                dto.getFootnotes(),
                language
            );
        }


        if (dto.getBibliography() != null) {
            applyBibliographyTranslation(
                monument.getBibliography(),
                dto.getBibliography(),
                language
            );
        }
    }

    private void applyFootnotesTranslation(
        List<Footnote> footnotes,
        List<FootnoteTranslationDto> dto,
        TranslationLanguage language
    ) {

        if (footnotes == null || dto == null) {
            return;
        }

        int size = Math.min(
            footnotes.size(),
            dto.size()
        );

        for (int i = 0; i < size; i++) {

            Footnote footnote = footnotes.get(i);

            FootnoteTranslationDto translation = dto.get(i);

            if (language == TranslationLanguage.en) {

                footnote.setTextEn(
                    clean(translation.getText())
                );

            } else {

                footnote.setTextFr(
                    clean(translation.getText())
                );
            }
        }
    }

    private void applyTopographicTranslation(
        Topographic topographic,
        TopographicTranslationDto dto,
        TranslationLanguage language
    ) {

        if (topographic == null || dto == null) {
            return;
        }


        if (language == TranslationLanguage.en) {

            topographic.setRegionEn(clean(dto.getRegion()));
            topographic.setAddressEn(clean(dto.getAddress()));
            topographic.setTopographyEn(clean(dto.getTopography()));
            topographic.setAltitudeEn(
                dto.getAltitude()
            );
            topographic.setDistanceFromResidenceEn(
                clean(dto.getDistanceFromResidence())
            );
            topographic.setHydrographyEn(clean(dto.getHydrography()));
            topographic.setDescriptionEn(clean(dto.getDescription()));

        } else {

            topographic.setRegionFr(clean(dto.getRegion()));
            topographic.setAddressFr(clean(dto.getAddress()));
            topographic.setTopographyFr(clean(dto.getTopography()));
            topographic.setAltitudeFr(
                dto.getAltitude()
            );
            topographic.setDistanceFromResidenceFr(
                clean(dto.getDistanceFromResidence())
            );
            topographic.setHydrographyFr(clean(dto.getHydrography()));
            topographic.setDescriptionFr(clean(dto.getDescription()));

        }
    }

    private void applyHistoricalTranslation(
        HistoricalReference historicalReference,
        HistoricalReferenceTranslationDto dto,
        TranslationLanguage language
    ) {

        if (historicalReference == null || dto == null) {
            return;
        }


        if (language == TranslationLanguage.en) {

            historicalReference.setCulturalAffiliationEn(
                clean(dto.getCulturalAffiliation())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnReliableDocumentEn(
                clean(dto.getJustificationOfTheNumberingBasedOnReliableDocument())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnBibliographicalSourcesEn(
                clean(dto.getJustificationOfTheNumberingBasedOnBibliographicalSources())
            );

            historicalReference.setJustificationOfTheNumberingAccordingIconographyEn(
                clean(dto.getJustificationOfTheNumberingAccordingIconography())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnEvidenceEn(
                clean(dto.getJustificationOfTheNumberingBasedOnEvidence())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnLithographyEn(
                clean(dto.getJustificationOfTheNumberingBasedOnLithography())
            );

            historicalReference.setChronologicalTableOfTheStudEn(
                clean(dto.getChronologicalTableOfTheStud())
            );

            historicalReference.setChronologicalTableOfTheMonumentsStudyEn(
                clean(dto.getChronologicalTableOfTheMonumentsStudy())
            );

            historicalReference.setAuthorEn(
                clean(dto.getAuthor())
            );

            historicalReference.setSourceForDeterminingTheAuthorEn(
                clean(dto.getSourceForDeterminingTheAuthor())
            );

            historicalReference.setBriefHistoricalOverviewEn(
                clean(dto.getBriefHistoricalOverview())
            );

        } else {

            historicalReference.setCulturalAffiliationFr(
                clean(dto.getCulturalAffiliation())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnReliableDocumentFr(
                clean(dto.getJustificationOfTheNumberingBasedOnReliableDocument())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnBibliographicalSourcesFr(
                clean(dto.getJustificationOfTheNumberingBasedOnBibliographicalSources())
            );

            historicalReference.setJustificationOfTheNumberingAccordingIconographyFr(
                clean(dto.getJustificationOfTheNumberingAccordingIconography())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnEvidenceFr(
                clean(dto.getJustificationOfTheNumberingBasedOnEvidence())
            );

            historicalReference.setJustificationOfTheNumberingBasedOnLithographyFr(
                clean(dto.getJustificationOfTheNumberingBasedOnLithography())
            );

            historicalReference.setChronologicalTableOfTheStudFr(
                clean(dto.getChronologicalTableOfTheStud())
            );

            historicalReference.setChronologicalTableOfTheMonumentsStudyFr(
                clean(dto.getChronologicalTableOfTheMonumentsStudy())
            );

            historicalReference.setAuthorFr(
                clean(dto.getAuthor())
            );

            historicalReference.setSourceForDeterminingTheAuthorFr(
                clean(dto.getSourceForDeterminingTheAuthor())
            );

            historicalReference.setBriefHistoricalOverviewFr(
                clean(dto.getBriefHistoricalOverview())
            );
        }
    }

    private void applyDescriptiveTranslation(
        DescriptiveCharacteristicReference descriptive,
        DescriptiveCharacteristicTranslationDto dto,
        TranslationLanguage language
    ) {

        if (descriptive == null || dto == null) {
            return;
        }


        if (language == TranslationLanguage.en) {


            descriptive.setTheBuildingMaterialEn(
                clean(dto.getTheBuildingMaterial())
            );

            descriptive.setArcheologicalOverviewStratigraphyFindingsEn(
                clean(dto.getArcheologicalOverviewStratigraphyFindings())
            );

            descriptive.setArchitecturalOverviewEn(
                clean(dto.getArchitecturalOverview())
            );

            descriptive.setDecorativeAndMonumentalFeaturesCompositionColoursEn(
                clean(dto.getDecorativeAndMonumentalFeaturesCompositionColours())
            );

            descriptive.setOpeningsWindowsEn(
                clean(dto.getOpeningsWindows())
            );

            descriptive.setOpeningsEntrancesEn(
                clean(dto.getOpeningsEntrances())
            );

            descriptive.setConstructionsEn(
                clean(dto.getConstructions())
            );

            descriptive.setRoofEn(
                clean(dto.getRoof())
            );

            descriptive.setLevelsOfConstructionEn(
                clean(dto.getLevelsOfConstruction())
            );

            descriptive.setTypeEn(
                clean(dto.getType())
            );

            descriptive.setExteriorEn(
                clean(dto.getExterior())
            );

            descriptive.setLengthEn(
                clean(dto.getLength())
            );

            descriptive.setWidthEn(
                clean(dto.getWidth())
            );

            descriptive.setHeightEn(
                clean(dto.getHeight())
            );

            descriptive.setDepthThicknessEn(
                clean(dto.getDepthThickness())
            );

            descriptive.setAreaEn(
                clean(dto.getArea())
            );

            descriptive.setLengthOfSpanEn(
                clean(dto.getLengthOfSpan())
            );

            descriptive.setImplementationTechniqueEn(
                clean(dto.getImplementationTechnique())
            );

            descriptive.setStateOfMonumentEn(
                clean(dto.getStateOfMonument())
            );

            descriptive.setValuationEn(
                clean(dto.getValuation())
            );


        } else {


            descriptive.setTheBuildingMaterialFr(
                clean(dto.getTheBuildingMaterial())
            );

            descriptive.setArcheologicalOverviewStratigraphyFindingsFr(
                clean(dto.getArcheologicalOverviewStratigraphyFindings())
            );

            descriptive.setArchitecturalOverviewFr(
                clean(dto.getArchitecturalOverview())
            );

            descriptive.setDecorativeAndMonumentalFeaturesCompositionColoursFr(
                clean(dto.getDecorativeAndMonumentalFeaturesCompositionColours())
            );

            descriptive.setOpeningsWindowsFr(
                clean(dto.getOpeningsWindows())
            );

            descriptive.setOpeningsEntrancesFr(
                clean(dto.getOpeningsEntrances())
            );

            descriptive.setConstructionsFr(
                clean(dto.getConstructions())
            );

            descriptive.setLevelsOfConstructionFr(
                clean(dto.getLevelsOfConstruction())
            );

            descriptive.setRoofFr(
                clean(dto.getRoof())
            );

            descriptive.setTypeFr(
                clean(dto.getType())
            );

            descriptive.setExteriorFr(
                clean(dto.getExterior())
            );

            descriptive.setLengthFr(
                clean(dto.getLength())
            );

            descriptive.setWidthFr(
                clean(dto.getWidth())
            );

            descriptive.setHeightFr(
                clean(dto.getHeight())
            );

            descriptive.setDepthThicknessFr(
                clean(dto.getDepthThickness())
            );

            descriptive.setAreaFr(
                clean(dto.getArea())
            );

            descriptive.setLengthOfSpanFr(
                clean(dto.getLengthOfSpan())
            );

            descriptive.setImplementationTechniqueFr(
                clean(dto.getImplementationTechnique())
            );

            descriptive.setStateOfMonumentFr(
                clean(dto.getStateOfMonument())
            );

            descriptive.setValuationFr(
                clean(dto.getValuation())
            );
        }
    }

    private void applyVideosTranslation(
        List<MonumentVideo> videos,
        List<VideoTranslationDto> dto,
        TranslationLanguage language
    ) {

        if (videos == null || dto == null) {
            return;
        }


        int size = Math.min(
            videos.size(),
            dto.size()
        );


        for (int i = 0; i < size; i++) {

            MonumentVideo video = videos.get(i);

            VideoTranslationDto translation =
                dto.get(i);


            if(language == TranslationLanguage.en) {

                video.setTitleEn(
                    clean(translation.getTitle())
                );

            } else {

                video.setTitleFr(
                    clean(translation.getTitle())
                );
            }
        }
    }

    private void applyImagesTranslation(
        List<MonumentImage> images,
        List<ImageAndMeasurementTranslationDto> dto,
        TranslationLanguage language
    ) {

        if (images == null || dto == null) {
            return;
        }


        int size = Math.min(
            images.size(),
            dto.size()
        );


        for (int i = 0; i < size; i++) {

            MonumentImage image = images.get(i);

            ImageAndMeasurementTranslationDto translation =
                dto.get(i);


            if(language == TranslationLanguage.en) {

                image.setCaptionEn(
                    clean(translation.getCaption())
                );

            } else {

                image.setCaptionFr(
                    clean(translation.getCaption())
                );
            }
        }
    }

    private void applyMeasurementTranslation(
        List<MonumentMeasurement> images,
        List<ImageAndMeasurementTranslationDto> dto,
        TranslationLanguage language
    ) {

        if (images == null || dto == null) {
            return;
        }


        int size = Math.min(
            images.size(),
            dto.size()
        );


        for (int i = 0; i < size; i++) {

            MonumentMeasurement image = images.get(i);

            ImageAndMeasurementTranslationDto translation =
                dto.get(i);


            if(language == TranslationLanguage.en) {

                image.setCaptionEn(
                    clean(translation.getCaption())
                );

            } else {

                image.setCaptionFr(
                    clean(translation.getCaption())
                );
            }
        }
    }

    private void applyBibliographyTranslation(
        List<Bibliography> bibliography,
        List<BibliographyTranslationDto> dto,
        TranslationLanguage language
    ) {

        if (bibliography == null || dto == null) {
            return;
        }


        int size = Math.min(
            bibliography.size(),
            dto.size()
        );


        for (int i = 0; i < size; i++) {


            Bibliography item =
                bibliography.get(i);


            BibliographyTranslationDto translation =
                dto.get(i);



            if(language == TranslationLanguage.en) {

                item.setTitleEn(
                    clean(translation.getTitle())
                );

            } else {

                item.setTitleFr(
                    clean(translation.getTitle())
                );
            }
        }
    }

    private String clean(String value) {

        if (value == null) {
            return null;
        }

        if (value.isBlank()) {
            return null;
        }

        if (value.equalsIgnoreCase("null")) {
            return null;
        }

        return value;
    }
}