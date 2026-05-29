package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;

import org.hishatakaran.backend.mapper.StateOfMonumentMapper;
import org.hishatakaran.backend.model.StateOfMonumentResponseDto;
import org.hishatakaran.backend.repository.StateOfMonumentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state-of-monument")
@RequiredArgsConstructor
public class StateOfMonumentController {

    private final StateOfMonumentRepository stateOfMonumentRepository;

    @GetMapping
    public List<StateOfMonumentResponseDto> getAll() {
        return stateOfMonumentRepository.findAll()
            .stream()
            .map(StateOfMonumentMapper::toDto)
            .toList();
    }

    @GetMapping("/{name}")
    public StateOfMonumentResponseDto getById(@PathVariable String name) {
        return StateOfMonumentMapper.toDto(
            stateOfMonumentRepository.findById(name).orElseThrow()
        );
    }
}