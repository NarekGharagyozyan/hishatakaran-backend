package org.hishatakaran.backend.controller;

import java.util.UUID;

import org.hishatakaran.backend.Scraper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/scrape")
@RequiredArgsConstructor
public class ScrapeController {

  private final Scraper scraper;

  @GetMapping
  public void scrape() throws JsonProcessingException {
    scraper.scrape();
  }
}
