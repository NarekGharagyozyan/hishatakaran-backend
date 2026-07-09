package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            LanguagesResponseDto.of(
                topographic.getRegionHy(),
                topographic.getRegionEn(),
                topographic.getRegionFr()
            ),
            LanguagesResponseDto.of(
                topographic.getAddressHy(),
                topographic.getAddressEn(),
                topographic.getAddressFr()
            ),
            LanguagesResponseDto.of(
                topographic.getTopographyHy(),
                topographic.getTopographyEn(),
                topographic.getTopographyFr()
            ),
            LanguagesResponseDto.of(
                topographic.getDistanceFromResidenceHy(),
                topographic.getDistanceFromResidenceEn(),
                topographic.getDistanceFromResidenceFr()
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
                topographic.getDescriptionHy(),
                topographic.getDescriptionEn(),
                topographic.getDescriptionFr()
            )
        );
    }
}