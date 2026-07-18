package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.FootnoteResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.service.YouTubeService;

public class MonumentMapper {

    public static MonumentResponseDto toDto(Monument m) {
        if (m == null) return null;

        MonumentResponseDto.MonumentResponseDtoBuilder monumentDtoBuilder = MonumentResponseDto.builder();

        monumentDtoBuilder.id(m.getId());
        monumentDtoBuilder.name(LanguagesResponseDto.of(
            m.getNameHy(),
            m.getNameEn(),
            m.getNameFr()
        ));
        monumentDtoBuilder.isPublished(m.getIsPublished());
        monumentDtoBuilder.monumentType(MonumentTypeMapper.toDto(m.getMonumentType()));

        if (m.getRegion() != null) {
            monumentDtoBuilder.region(RegionMapper.toDto(m.getRegion()));
        }

        if (m.getSettlement() != null) {
            monumentDtoBuilder.settlement(SettlementMapper.toDto(m.getSettlement()));
        }

        monumentDtoBuilder.specialName(LanguagesResponseDto.of(
            m.getSpecialNameHy(),
            m.getSpecialNameEn(),
            m.getSpecialNameFr()
        ));
        monumentDtoBuilder.anotherNames(LanguagesResponseDto.of(
            m.getAnotherNamesHy(),
            m.getAnotherNamesEn(),
            m.getAnotherNamesFr()
        ));

        monumentDtoBuilder.history(LanguagesResponseDto.of(
            m.getHistoryHy(),
            m.getHistoryEn(),
            m.getHistoryFr()
        ));
        monumentDtoBuilder.originalAffiliation(LanguagesResponseDto.of(
            m.getOriginalAffiliationHy(),
            m.getOriginalAffiliationEn(),
            m.getOriginalAffiliationFr()
        ));
        monumentDtoBuilder.storageUnitName(LanguagesResponseDto.of(
            m.getStorageUnitNameHy(),
            m.getStorageUnitNameEn(),
            m.getStorageUnitNameFr()
        ));
        monumentDtoBuilder.individuallyCertifiablePartsOfTheStorageUnit(LanguagesResponseDto.of(
            m.getIndividuallyCertifiablePartsOfTheStorageUnitHy(),
            m.getIndividuallyCertifiablePartsOfTheStorageUnitEn(),
            m.getIndividuallyCertifiablePartsOfTheStorageUnitFr()
        ));

        monumentDtoBuilder.images(m.getImages());
        monumentDtoBuilder.videos(m.getVideos()
            .stream()
            .map(video -> new MonumentVideoResponseDto(
                LanguagesResponseDto.of(
                    video.getTitleHy(),
                    video.getTitleEn(),
                    video.getTitleFr()
                ),
                YouTubeService.extractVideoId(video.getUrl()),
                video.getUrl()
            ))
            .toList()
        );
        monumentDtoBuilder.measurements(m.getMeasurements());

        monumentDtoBuilder.footnotes(
            m.getFootnotes()
                .stream()
                .map(footnote -> new FootnoteResponseDto(
                    footnote.getId(),
                    footnote.getOrderNumber(),
                    LanguagesResponseDto.of(
                        footnote.getTextHy(),
                        footnote.getTextEn(),
                        footnote.getTextFr()
                    )
                ))
                .toList()
        );

        monumentDtoBuilder.createdAt(m.getCreatedAt().toEpochSecond());
        monumentDtoBuilder.updatedAt(m.getUpdatedAt().toEpochSecond());

        monumentDtoBuilder.bibliography(
            m.getBibliography()
                .stream()
                .map(bibliography -> new BibliographyResponseDto(
                    bibliography.getId(),
                    LanguagesResponseDto.of(
                        bibliography.getTitleHy(),
                        bibliography.getTitleEn(),
                        bibliography.getTitleFr()
                    ),
                    bibliography.getUrl()))
                .toList()
        );

        monumentDtoBuilder.topographics(TopographicMapper.toDto(m.getTopographics()));
        monumentDtoBuilder.historicalReferences(HistoricalReferenceMapper.toDto(m.getHistoricalReferences()));
        monumentDtoBuilder.descriptiveCharacteristics(DescriptiveCharacteristicMapper.toDto(m.getDescriptiveCharacteristics()));
        monumentDtoBuilder.signature(m.getSignature());

        return monumentDtoBuilder.build();
    }
}