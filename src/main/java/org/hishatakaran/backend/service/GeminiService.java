package org.hishatakaran.backend.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hishatakaran.backend.model.MonumentRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;

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

  public String requestGeminiForNews(String titleArmenian, String textArmenian) {
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

  @Retryable(
      retryFor = { Exception.class },
      maxAttempts = 4,
      backoff = @Backoff(delay = 10000, multiplier = 2.0)
  )
  public String requestGeminiForMonuments(MonumentRequestDto monumentRequestDto) {

    System.out.println("method requestGeminiForMonuments called");
    // 1. Создаем ОДНУ общую карту для всех свойств
    Map<String, Schema> allProperties = new HashMap<>();

    // Базовые текстовые поля
    allProperties.put("nameArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("specialNameArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("specialNameEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("specialNameFrench", Schema.builder().type(Type.Known.STRING).build());

    // Массивы (anotherNames)
    allProperties.put("anotherNamesArmenian", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder().type(Type.Known.STRING).build())
        .build());
    allProperties.put("anotherNamesEnglish", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder().type(Type.Known.STRING).build())
        .build());
    allProperties.put("anotherNamesFrench", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder().type(Type.Known.STRING).build())
        .build());


    allProperties.put("monumentTypeArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("monumentTypeEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("monumentTypeFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("provinceArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("provinceEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("provinceFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("originalAffiliationArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("originalAffiliationEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("originalAffiliationFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("storageUnitNameArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("storageUnitNameEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("storageUnitNameFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("conditionArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("conditionEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("conditionFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("addressArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("addressEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("addressFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("topographyArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographyEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographyFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("distanceFromResidenceArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("distanceFromResidenceEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("distanceFromResidenceFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("hydrographyArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("hydrographyEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("hydrographyFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("descriptionArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("culturalAffiliationArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("culturalAffiliationEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("culturalAffiliationFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("centuryArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("centuryEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("centuryFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("justificationOfTheNumberingBasedOnLithographyArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("justificationOfTheNumberingBasedOnLithographyEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("justificationOfTheNumberingBasedOnLithographyFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("chronologicalTableOfTheStudArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheStudEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheStudFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("authorArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("theBuildingMaterialArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("theBuildingMaterialEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("theBuildingMaterialFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("typeArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("typeEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("typeFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("colorArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("colorEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("colorFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("implementationTechniqueArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("implementationTechniqueEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("implementationTechniqueFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("stateOfMonumentArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("stateOfMonumentEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("stateOfMonumentFrench", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("valuationArmenian", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("valuationEnglish", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("valuationFrench", Schema.builder().type(Type.Known.STRING).build());

    // 2. Строим финальную схему, передавая карту целиком ОДИН РАЗ
    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

    // Дальше ваш код остается без изменений...
    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(schema)
        .build();

    String prompt = """
      I am providing you with a DTO containing Armenian historical monument data.
      You must translate all text fields into English and French.
              
      CRITICAL RULES:
      1. Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors. If there are issues, correct them while preserving the original meaning.
      2. Populate all fields according to the requested JSON schema.
              
      Here is the DTO data:
      """ + monumentRequestDto.toString();

    Content content = Content.builder()
        .parts(List.of(Part.builder().text(prompt).build()))
        .build();

    GenerateContentResponse response = client.models.generateContent(
        "gemini-2.5-flash",
        content,
        config
    );

    System.out.println(response.text());
    return response.text();
  }
    /*String prompt = """
        I will provide you DTO with armenian fields.
        You must translate fields into English and French and return the row data result (json) in the following format:
        
        {
          "nameArmenian" : "...",
          "nameEnglish" : "...",
          "nameFrench" : "...",
        
          "specialNameArmenian" : "...",
          "specialNameEnglish" : "...",
          "specialNameFrench" : "...",
        
          "anotherNamesArmenian" : ["...","..."],
          "anotherNamesEnglish" : ["...","..."],
          "anotherNamesFrench" : ["...","..."],
        
          "provinceArmenian" : "...",
          "provinceEnglish" : "...",
          "provinceFrench" : "...",
        
          "originalAffiliationArmenian" : "...",
          "originalAffiliationEnglish" : "...",
          "originalAffiliationFrench" : "...",
        
          "storageUnitNameArmenian" : "...",
          "storageUnitNameEnglish" : "...",
          "storageUnitNameFrench" : "...",
        
          "conditionArmenian" : "...",
          "conditionEnglish" : "...",
          "conditionFrench" : "...",
        
          "regionArmenian" : "...",
          "regionEnglish" : "...",
          "regionFrench" : "...",
        
          "addressArmenian" : "...",
          "addressEnglish" : "...",
          "addressFrench" : "...",
        
          "topographyArmenian" : "...",
          "topographyEnglish" : "...",
          "topographyFrench" : "...",
        
          "distanceFromResidenceArmenian" : "...",
          "distanceFromResidenceEnglish" : "...",
          "distanceFromResidenceFrench" : "...",
        
          "hydrographyArmenian" : "...",
          "hydrographyEnglish" : "...",
          "hydrographyFrench" : "...",
        
          "descriptionArmenian" : "...",
          "descriptionEnglish" : "...",
          "descriptionFrench" : "...",
        
          "culturalAffiliationArmenian" : "...",
          "culturalAffiliationEnglish" : "...",
          "culturalAffiliationFrench" : "...",
        
          "centuryArmenian" : "...",
          "centuryEnglish" : "...",
          "centuryFrench" : "...",
        
          "justificationOfTheNumberingBasedOnLithographyArmenian" : "...",
          "justificationOfTheNumberingBasedOnLithographyEnglish" : "...",
          "justificationOfTheNumberingBasedOnLithographyFrench" : "...",
        
          "chronologicalTableOfTheStudArmenian" : "...",
          "chronologicalTableOfTheStudEnglish" : "...",
          "chronologicalTableOfTheStudFrench" : "...",
        
          "authorArmenian" : "...",
          "authorEnglish" : "...",
          "authorFrench" : "...",
        
          "theBuildingMaterialArmenian" : "...",
          "theBuildingMaterialEnglish" : "...",
          "theBuildingMaterialFrench" : "...",
        
          "typeArmenian" : "...",
          "typeEnglish" : "...",
          "typeFrench" : "...",
        
          "colorArmenian" : "...",
          "colorEnglish" : "...",
          "colorFrench" : "...",
        
          "implementationTechniqueArmenian" : "...",
          "implementationTechniqueEnglish" : "...",
          "implementationTechniqueFrench" : "...",
        
          "stateOfMonumentArmenian" : "...",
          "stateOfMonumentEnglish" : "...",
          "stateOfMonumentFrench" : "...",

          "valuationArmenian" : "...",
          "valuationEnglish" : "...",
          "valuationFrench" : "..."
        }
        
        Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors.
        If there are any issues, correct them while preserving the original meaning.
        Here is dto: """ + monumentRequestDto.toString() + """
        """;

    System.out.println("method requestGeminiForMonuments called");

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
        "gemini-2.5-flash",
        content,
        null);

    return response.text();*/

}