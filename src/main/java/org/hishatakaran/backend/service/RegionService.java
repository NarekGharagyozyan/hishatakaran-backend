package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;

import org.hishatakaran.backend.mapper.RegionMapper;
import org.hishatakaran.backend.model.RegionResponseDto;
import org.hishatakaran.backend.repository.RegionRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionResponseDto> getAll() {
        return regionRepository.findAll()
            .stream()
            .map(RegionMapper::toDto)
            .sorted(Comparator.comparing(RegionResponseDto::getId).reversed())
            .toList();
    }

    public RegionResponseDto getById(Long id) {
        return RegionMapper.toDto(
            regionRepository.findById(id).orElseThrow()
        );
    }
}
