package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.ProgramEditDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.service.ProgramService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @PostMapping(path = "/admin/programs")
    public ResponseEntity<ProgramResponseDto> addProgram(
        @RequestBody ProgramRequestDto programRequestDto
    ){

        return ResponseEntity.ok(programService.postProgram(programRequestDto));

    }

    @PostMapping("/admin/programs/{id}/translate/{language}")
    public ProgramResponseDto translate(
        @PathVariable Long id,
        @PathVariable TranslationLanguage language
    ) {
        return programService.translate(id, language);
    }

    @PutMapping("/admin/programs/{id}")
    public ProgramResponseDto editProgram(
        @PathVariable Long id,
        @RequestBody ProgramEditDto programRequestDto
    ) {
        return programService.editProgram(id, programRequestDto);
    }

    @DeleteMapping("/admin/programs/{id}")
    public void deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
    }

    @GetMapping("programs/{id}")
    public ResponseEntity<ProgramResponseDto> findProgramById(
        @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @GetMapping("/programs")
    public ResponseEntity<List<ProgramResponseDto>> findAllProgram(){
        return ResponseEntity.ok(programService.getAllPrograms());
    }
}