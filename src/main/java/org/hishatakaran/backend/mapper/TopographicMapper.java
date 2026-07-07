package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            new LanguagesResponseDto(
                topographic.getRegionHy(),
                topographic.getRegionEn(),
                topographic.getRegionFr()
            ),
            new LanguagesResponseDto(
                topographic.getAddressHy(),
                topographic.getAddressEn(),
                topographic.getAddressFr()
            ),
            new LanguagesResponseDto(
                topographic.getTopographyHy(),
                topographic.getTopographyEn(),
                topographic.getTopographyFr()
            ),
            new LanguagesResponseDto(
                topographic.getDistanceFromResidenceHy(),
                topographic.getDistanceFromResidenceEn(),
                topographic.getDistanceFromResidenceFr()
            ),
            topographic.getLatitude(),
            topographic.getLongitude(),
            topographic.getAltitude(),
            new LanguagesResponseDto(
                topographic.getHydrographyHy(),
                topographic.getHydrographyEn(),
                topographic.getHydrographyFr()
            ),
            new LanguagesResponseDto(
                topographic.getDescriptionHy(),
                topographic.getDescriptionEn(),
                topographic.getDescriptionFr()
            )
        );
    }
}