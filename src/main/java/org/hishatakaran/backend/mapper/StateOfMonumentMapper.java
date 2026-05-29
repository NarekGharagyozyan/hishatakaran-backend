package org.hishatakaran.backend.mapper;

import java.util.List;

import org.hishatakaran.backend.entity.StateOfMonument;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.model.StateOfMonumentResponseDto;

public class StateOfMonumentMapper {

  public static StateOfMonumentResponseDto toDto(StateOfMonument state) {

    List<DescriptiveCharacteristicResponseDto> descriptiveCharacteristicDtos = state.getDescriptiveCharacteristics()
        .stream()
        .map(DescriptiveCharacteristicMapper::toDto)
        .toList();
    return new StateOfMonumentResponseDto(
        state.getName(),
        descriptiveCharacteristicDtos
    );
  }
}
