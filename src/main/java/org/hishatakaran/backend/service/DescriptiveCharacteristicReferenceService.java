package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.mapper.DescriptiveCharacteristicMapper;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.repository.DescriptiveCharacteristicReferenceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DescriptiveCharacteristicReferenceService {

    private final DescriptiveCharacteristicReferenceRepository descriptiveRepository;

    public List<DescriptiveCharacteristicResponseDto> getAll() {
        return descriptiveRepository.findAll()
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }

    public DescriptiveCharacteristicResponseDto getById(Long id) {
        return DescriptiveCharacteristicMapper.toDto(
            descriptiveRepository.findById(id).orElseThrow()
        );
    }

    public List<DescriptiveCharacteristicResponseDto> getByMonument(Long monumentId) {
        return descriptiveRepository.findByMonumentId(monumentId)
            .stream()
            .map(DescriptiveCharacteristicMapper::toDto)
            .toList();
    }
}
