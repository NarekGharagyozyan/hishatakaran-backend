package org.hishatakaran.backend.controller;

import org.hishatakaran.backend.model.SearchResponseDto;
import org.hishatakaran.backend.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponseDto> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(searchService.globalSearch(query));
    }
}