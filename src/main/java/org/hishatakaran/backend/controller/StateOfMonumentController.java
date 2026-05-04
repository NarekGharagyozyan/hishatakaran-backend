package org.hishatakaran.backend.controller;

import lombok.RequiredArgsConstructor;
import org.hishatakaran.backend.entity.StateOfMonument;
import org.hishatakaran.backend.repository.StateOfMonumentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state-of-monument")
@RequiredArgsConstructor
public class StateOfMonumentController {

    private final StateOfMonumentRepository stateOfMonumentRepository;

    @GetMapping
    public List<StateOfMonument> getAll() {
        return stateOfMonumentRepository.findAll();
    }

    @GetMapping("/{name}")
    public StateOfMonument getById(@PathVariable String name) {
        return stateOfMonumentRepository.findById(name).orElseThrow();
    }
}