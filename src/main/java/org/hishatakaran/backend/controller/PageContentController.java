package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.PageContentEditDto;
import org.hishatakaran.backend.model.PageContentRequestDto;
import org.hishatakaran.backend.model.PageContentResponseDto;
import org.hishatakaran.backend.model.PageType;
import org.hishatakaran.backend.service.PageContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * /settlementItems, /monumentPassport and /documentedMonument: the same three calls as
 * MainPageController, one set per page.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PageContentController {

  private static final String PAGE = "{page:settlementItems|monumentPassport|documentedMonument}";

  private final PageContentService pageContentService;

  @PostMapping("/admin/" + PAGE)
  public ResponseEntity<PageContentResponseDto> createPageContent(
      @PathVariable PageType page,
      @RequestBody PageContentRequestDto pageContentRequestDto
  ) {
    return ResponseEntity.ok(pageContentService.createPageContent(page, pageContentRequestDto));
  }

  @GetMapping("/" + PAGE)
  public ResponseEntity<PageContentResponseDto> getPageContent(@PathVariable PageType page) {
    return ResponseEntity.ok(pageContentService.getPageContent(page));
  }

  @PutMapping("/admin/" + PAGE)
  public ResponseEntity<PageContentResponseDto> editPageContent(
      @PathVariable PageType page,
      @RequestBody PageContentEditDto pageContentEditDto
  ) {
    return ResponseEntity.ok(pageContentService.editPageContent(page, pageContentEditDto));
  }
}
