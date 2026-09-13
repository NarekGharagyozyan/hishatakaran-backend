package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.mapper.HistoricalReferenceMapper;
import org.hishatakaran.backend.model.HistoricalReferenceResponseDto;
import org.hishatakaran.backend.repository.HistoricalReferenceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricalReferenceService {

    private final HistoricalReferenceRepository historicalReferenceRepository;

    public List<HistoricalReferenceResponseDto> getAll() {
        return historicalReferenceRepository.findAll()
            .stream()
            .map(HistoricalReferenceMapper::toDto)
            .toList();
    }

    public HistoricalReferenceResponseDto getById(Long id) {
        return HistoricalReferenceMapper.toDto(
            historicalReferenceRepository.findById(id).orElseThrow()
        );
    }
}
