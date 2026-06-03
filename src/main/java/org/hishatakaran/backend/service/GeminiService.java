package org.hishatakaran.backend.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

@Service
public class GeminiService {

  private static String API_KEY = "AIzaSyAvXsUCT3iP7qecRpKblarfhuqyn51azVk";
  static Client client = Client.builder().apiKey(API_KEY).build();

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