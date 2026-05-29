package org.hishatakaran.backend.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StateOfMonumentResponseDto {

    private final String name;
    private final List<DescriptiveCharacteristicResponseDto> descriptiveCharacteristics;
}