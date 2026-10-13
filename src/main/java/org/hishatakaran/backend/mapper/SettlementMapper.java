package org.hishatakaran.backend.mapper;

import java.util.List;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MeasurementResponseDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.model.SettlementDetailsResponseDto;
import org.hishatakaran.backend.model.SettlementTopographicResponseDto;
import org.hishatakaran.backend.service.YouTubeService;

public class SettlementMapper {

  /** The whole settlement: every endpoint that returns a settlement, embedded or not, uses this form. */
  public static SettlementDetailsResponseDto toDetailsDto(Settlement settlement) {

    if (settlement == null) {
      return null;
    }

    SettlementDetailsResponseDto dto = new SettlementDetailsResponseDto();

    dto.setId(settlement.getId());
    dto.setRegionId(settlement.getRegion() == null ? null : settlement.getRegion().getId());
    dto.setName(LanguagesResponseDto.of(
        settlement.getNameHy(),
        settlement.getNameEn(),
        settlement.getNameFr()
    ));
    dto.setSettlementType(SettlementTypeMapper.toDto(settlement.getSettlementType()));
    dto.setAnotherNames(LanguagesResponseDto.of(
        settlement.getAnotherNamesHy(),
        settlement.getAnotherNamesEn(),
        settlement.getAnotherNamesFr()
    ));
    dto.setHistory(LanguagesResponseDto.of(
        settlement.getHistoryHy(),
        settlement.getHistoryEn(),
        settlement.getHistoryFr()
    ));
    dto.setDescription(LanguagesResponseDto.of(
        settlement.getDescriptionHy(),
        settlement.getDescriptionEn(),
        settlement.getDescriptionFr()
    ));
    dto.setDataUpdate(LanguagesResponseDto.of(
        settlement.getDataUpdateHy(),
        settlement.getDataUpdateEn(),
        settlement.getDataUpdateFr()
    ));
    dto.setTopographics(toDto(settlement.getTopographics()));

    dto.setBibliography(settlement.getBibliography() == null ? List.of() : settlement.getBibliography()
        .stream()
        .map(bibliography -> new BibliographyResponseDto(
            bibliography.getId(),
            LanguagesResponseDto.of(
                bibliography.getTitleHy(),
                bibliography.getTitleEn(),
                bibliography.getTitleFr()
            ),
            bibliography.getUrl()
        ))
        .toList());

    dto.setImages(images(settlement));

    dto.setMeasurements(settlement.getMeasurements() == null ? List.of() : settlement.getMeasurements()
        .stream()
        .map(measurement -> new MeasurementResponseDto(
            measurement.getId(),
            LanguagesResponseDto.of(
                measurement.getCaptionHy(),
                measurement.getCaptionEn(),
                measurement.getCaptionFr()
            ),
            measurement.getUrl()
        ))
        .toList());

    dto.setVideos(settlement.getVideos() == null ? List.of() : settlement.getVideos()
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
        .toList());

    return dto;
  }

  private static List<ImageResponseDto> images(Settlement settlement) {

    List<SettlementImage> images = settlement.getImages();

    if (images == null) {
      return List.of();
    }

    return images
        .stream()
        .map(image -> new ImageResponseDto(
            image.getId(),
            LanguagesResponseDto.of(
                image.getCaptionHy(),
                image.getCaptionEn(),
                image.getCaptionFr()
            ),
            image.getUrl()
        ))
        .toList();
  }

  private static SettlementTopographicResponseDto toDto(SettlementTopographic topographic) {

    if (topographic == null) {
      return null;
    }

    return new SettlementTopographicResponseDto(
        topographic.getId(),
        LanguagesResponseDto.of(
            topographic.getTopographyHy(),
            topographic.getTopographyEn(),
            topographic.getTopographyFr()
        ),
        LanguagesResponseDto.of(
            topographic.getAddressHy(),
            topographic.getAddressEn(),
            topographic.getAddressFr()
        ),
        topographic.getLatitude(),
        topographic.getLongitude(),
        LanguagesResponseDto.of(
            topographic.getAltitudeHy(),
            topographic.getAltitudeEn(),
            topographic.getAltitudeFr()
        ),
        LanguagesResponseDto.of(
            topographic.getHydrographyHy(),
            topographic.getHydrographyEn(),
            topographic.getHydrographyFr()
        ),
        LanguagesResponseDto.of(
            topographic.getDataUpdateHy(),
            topographic.getDataUpdateEn(),
            topographic.getDataUpdateFr()
        )
    );
  }
}
