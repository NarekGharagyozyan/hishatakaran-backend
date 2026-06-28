package org.hishatakaran.backend.mapper;

import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.TopographicResponseDto;

public class TopographicMapper {

    public static TopographicResponseDto toDto(Topographic topographic) {

        return new TopographicResponseDto(
            topographic.getId(),
            topographic.getProvinceArmenian(),
            topographic.getProvinceEnglish(),
            topographic.getProvinceFrench(),
            topographic.getAddressArmenian(),
            topographic.getAddressEnglish(),
            topographic.getAddressFrench(),
            topographic.getTopographyArmenian(),
            topographic.getTopographyEnglish(),
            topographic.getTopographyFrench(),
            topographic.getDistanceFromResidenceArmenian(),
            topographic.getDistanceFromResidenceEnglish(),
            topographic.getDistanceFromResidenceFrench(),
            topographic.getLatitude(),
            topographic.getLongitude(),
            topographic.getAltitude(),
            topographic.getHydrographyArmenian(),
            topographic.getHydrographyEnglish(),
            topographic.getHydrographyFrench(),
            topographic.getDescriptionArmenian(),
            topographic.getDescriptionEnglish(),
            topographic.getDescriptionFrench()
        );
    }
}