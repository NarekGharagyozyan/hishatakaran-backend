package org.hishatakaran.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import jakarta.annotation.PostConstruct;

@Service
public class GeminiService {

  @Value("${gemini.api.key}")
  private String apiKey;
  private Client client;

  @PostConstruct
  public void init() {
    this.client = new Client.Builder()
        .apiKey(apiKey)
        .build();
  }

  public String askGemini(String titleArmenian, String textArmenian)
  {
    String prompt = """
        I will provide you with a title and text in Armenian.
        You must translate them into English and French and return the result in the following format:
        {
          "titleArmenian": "...",
          "titleEnglish": "...",
          "titleFrench": "..."
          "textArmenian": "...",
          "textEnglish": "...",
          "textFrench": "..."
        }
        
        Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors.
        If there are any issues, correct them while preserving the original meaning.
        
        Here is the title: """ + titleArmenian + " And here is the text: " + textArmenian;
    Content content = Content.builder()
        .parts(
            List.of(
                Part.builder()
                    .text(prompt)
                    .build()
            )
        )
        .build();
    GenerateContentResponse response = client.models.generateContent(
            "gemini-3.5-flash",
            content,
            null);

    return response.text();
  }
}