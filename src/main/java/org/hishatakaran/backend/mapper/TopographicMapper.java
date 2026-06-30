package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            topographic.getProvinceHy(),
            topographic.getProvinceEn(),
            topographic.getProvinceFr(),
            topographic.getAddressHy(),
            topographic.getAddressEn(),
            topographic.getAddressFr(),
            topographic.getTopographyHy(),
            topographic.getTopographyEn(),
            topographic.getTopographyFr(),
            topographic.getDistanceFromResidenceHy(),
            topographic.getDistanceFromResidenceEn(),
            topographic.getDistanceFromResidenceFr(),
            topographic.getLatitude(),
            topographic.getLongitude(),
            topographic.getAltitude(),
            topographic.getHydrographyHy(),
            topographic.getHydrographyEn(),
            topographic.getHydrographyFr(),
            topographic.getDescriptionHy(),
            topographic.getDescriptionEn(),
            topographic.getDescriptionFr()
        );
    }
}