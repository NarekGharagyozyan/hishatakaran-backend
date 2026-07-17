package org.hishatakaran.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;

import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.service.GeminiService;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Scraper {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    public void scrape() throws JsonProcessingException {

        String url = "https://hishatakaran.org/hy/documents/520";

        try (Playwright playwright = Playwright.create()) {

            BrowserContext context =
                playwright.chromium().launchPersistentContext(
                    Paths.get("playwright-profile"),
                    new BrowserType.LaunchPersistentContextOptions()
                        .setChannel("chrome")
                        .setHeadless(false)
                        .setArgs(List.of(
                            "--disable-blink-features=AutomationControlled"
                        ))
                );

            Page page = context.pages().getFirst();
            page.navigate(url);

            System.out.println(page.locator("body").innerText());
            page.waitForSelector("text=Տիպային անվանումը",
                new Page.WaitForSelectorOptions().setTimeout(60000));

            var text = page.locator("body").innerText();

            if (text.contains("Please wait while your request is being verified...")){
                throw new RuntimeException("Cloudflare did not pass");
            }
            MonumentRequestDto monumentRequestDto;
            String json = geminiService.requestGeminiFromHtml(text);
            var cleanJson = extractJson(json);
            monumentRequestDto = objectMapper.readValue(cleanJson, MonumentRequestDto.class);
            System.out.println(monumentRequestDto.toString());
        }
    }

    private String extractJson(String response) {
        var start = response.indexOf('{');
        var end = response.lastIndexOf('}');
        if (start == -1 || end == -1 || start >= end) {
            throw new IllegalArgumentException("No valid JSON found in response");
        }
        return response.substring(start, end + 1);
    }
}