package org.hishatakaran.backend.service;

import java.util.List;

import org.hishatakaran.backend.mapper.TopographicMapper;
import org.hishatakaran.backend.model.TopographicResponseDto;
import org.hishatakaran.backend.repository.TopographicRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopographicService {

    private final TopographicRepository topographicRepository;

    public List<TopographicResponseDto> getAll() {
        return topographicRepository.findAll()
            .stream()
            .map(TopographicMapper::toDto)
            .toList();
    }

    public TopographicResponseDto getById(Long id) {
        return TopographicMapper.toDto(
            topographicRepository.findById(id).orElseThrow()
        );
    }

    public List<TopographicResponseDto> getByMonument(Long monumentId) {
        return topographicRepository.findByMonumentId(monumentId)
            .stream()
            .map(TopographicMapper::toDto)
            .toList();
    }
}
