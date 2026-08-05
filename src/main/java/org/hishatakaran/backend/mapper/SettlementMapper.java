package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;

public class SettlementMapper {

  public static SettlementResponseDto toDto(Settlement settlement) {

    return new SettlementResponseDto(
        settlement.getId(),
        LanguagesResponseDto.of(
            settlement.getNameHy(),
            settlement.getNameEn(),
            settlement.getNameFr()
        ),
        LanguagesResponseDto.of(
            settlement.getDescriptionHy(),
            settlement.getDescriptionEn(),
            settlement.getDescriptionFr()
        ),
        settlement.getLongitude(),
        settlement.getLatitude(),
        settlement.getImages()
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
              .toList(),
        settlement.getRegion().getId()
    );
  }
}
