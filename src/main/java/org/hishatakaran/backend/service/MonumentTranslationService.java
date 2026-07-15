package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.entity.Bibliography;
import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.Footnote;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.MonumentVideo;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.BibliographyTranslationDto;
import org.hishatakaran.backend.model.DescriptiveCharacteristicTranslationDto;
import org.hishatakaran.backend.model.FootnoteTranslationDto;
import org.hishatakaran.backend.model.HistoricalReferenceTranslationDto;
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
            monument.setConditionEn(clean(dto.getCondition()));

        } else {

            monument.setNameFr(clean(dto.getName()));
            monument.setSpecialNameFr(clean(dto.getSpecialName()));
            monument.setAnotherNamesFr(clean(dto.getAnotherNames()));
            monument.setHistoryFr(clean(dto.getHistory()));
            monument.setOriginalAffiliationFr(clean(dto.getOriginalAffiliation()));
            monument.setStorageUnitNameFr(clean(dto.getStorageUnitName()));
            monument.setConditionFr(clean(dto.getCondition()));
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

            historicalReference.setCenturyEn(
                clean(dto.getCentury())
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

        } else {

            historicalReference.setCulturalAffiliationFr(
                clean(dto.getCulturalAffiliation())
            );

            historicalReference.setCenturyFr(
                clean(dto.getCentury())
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

            descriptive.setOpeningsEntrancesEn(
                clean(dto.getOpeningsEntrances())
            );

            descriptive.setConstructionsEn(
                clean(dto.getConstructions())
            );

            descriptive.setRoofEn(
                clean(dto.getRoof())
            );

            descriptive.setTypeEn(
                clean(dto.getType())
            );

            descriptive.setColorEn(
                clean(dto.getColor())
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

            descriptive.setOpeningsEntrancesFr(
                clean(dto.getOpeningsEntrances())
            );

            descriptive.setConstructionsFr(
                clean(dto.getConstructions())
            );

            descriptive.setRoofFr(
                clean(dto.getRoof())
            );

            descriptive.setTypeFr(
                clean(dto.getType())
            );

            descriptive.setColorFr(
                clean(dto.getColor())
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