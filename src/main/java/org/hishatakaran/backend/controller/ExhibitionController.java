package org.hishatakaran.backend.controller;

import java.util.List;

import org.hishatakaran.backend.model.ExhibitionEditDto;
import org.hishatakaran.backend.model.ExhibitionRequestDto;
import org.hishatakaran.backend.model.ExhibitionResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.service.ExhibitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExhibitionController {

  private final ExhibitionService exhibitionService;

  @PostMapping(path = "/admin/exhibitions")
  public ResponseEntity<ExhibitionResponseDto> addExhibition(
      @RequestBody ExhibitionRequestDto exhibitionRequestDto
  ){

    return ResponseEntity.ok(exhibitionService.postExhibition(exhibitionRequestDto));

  }

  @PostMapping("/admin/exhibitions/{id}/publish")
  public ExhibitionResponseDto publishExhibition(
      @PathVariable Long id
  ) {
    return exhibitionService.publish(id);
  }

  @PostMapping("/admin/exhibitions/{id}/translate/{language}")
  public ExhibitionResponseDto translate(
      @PathVariable Long id,
      @PathVariable TranslationLanguage language
  ) {
    return exhibitionService.translate(id, language);
  }

  @PutMapping("/admin/exhibitions/{id}")
  public ExhibitionResponseDto editExhibition(
      @PathVariable Long id,
      @RequestBody ExhibitionEditDto exhibitionRequestDto
  ) {
    return exhibitionService.editExhibition(id, exhibitionRequestDto);
  }

  @DeleteMapping("/admin/exhibitions/{id}")
  public void deleteExhibition(@PathVariable Long id) {
    exhibitionService.deleteExhibition(id);
  }

  @GetMapping("exhibitions/{id}")
  public ResponseEntity<ExhibitionResponseDto> findExhibitionById(
      @PathVariable("id") Long id
  ){
    return ResponseEntity.ok(exhibitionService.getExhibitionById(id));
  }

  @GetMapping("/exhibitions")
  public ResponseEntity<List<ExhibitionResponseDto>> findAllExhibitions(){
    return ResponseEntity.ok(exhibitionService.getAllExhibitions());
  }
}
