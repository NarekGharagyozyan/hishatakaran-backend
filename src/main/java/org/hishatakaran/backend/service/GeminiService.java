package org.hishatakaran.backend.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentTranslationDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.TeamMemberRequestDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

  @Value("${gemini.api.key}")
  private String apiKey;

  private Client client;

  private final ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    this.client = new Client.Builder()
        .apiKey(apiKey)
        .build();
  }

  public String requestGeminiForNews(String titleHy, String textHy) {
    String prompt = """
        I will provide you with a title and text in Armenian.
        You must translate them into English and French and return the result in the following format:
        {
          "titleHy": "...",
          "titleEn": "...",
          "titleFr": "..."
          "textHy": "...",
          "textEn": "...",
          "textFr": "..."
        }
                
        Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors.
        If there are any issues, correct them while preserving the original meaning.
                
        Here is the title: """ + titleHy + " And here is the text: " + textHy;
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
      backoff = @Backoff(delay = 100000, multiplier = 2.0)
  )
  public String requestGeminiFromHtml(String html) {

    System.out.println("method requestGeminiFromHtml called");

    Map<String, Schema> allProperties = new HashMap<>();

    allProperties.put("name", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("regionId", Schema.builder().type(Type.Known.NUMBER).build());
    allProperties.put("settlementId", Schema.builder().type(Type.Known.NUMBER).build());

    allProperties.put("monumentType", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("specialName", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("anotherNames", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder().type(Type.Known.STRING).build())
        .build());

    allProperties.put("history", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("originalAffiliation", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("storageUnitName", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("condition", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("bibliography", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(Map.of(
                "urls", Schema.builder()
                    .type(Type.Known.ARRAY)
                    .items(Schema.builder().type(Type.Known.STRING).build())
                    .build()
            ))
            .build())
        .build());

    allProperties.put("topographics", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(Map.of(
                "region", Schema.builder().type(Type.Known.STRING).build(),
                "address", Schema.builder().type(Type.Known.STRING).build(),
                "topography", Schema.builder().type(Type.Known.STRING).build(),
                "distanceFromResidence", Schema.builder().type(Type.Known.STRING).build(),
                "altitude", Schema.builder().type(Type.Known.NUMBER).build(),
                "hydrography", Schema.builder().type(Type.Known.STRING).build(),
                "description", Schema.builder().type(Type.Known.STRING).build()
            ))
            .build())
        .build());

    allProperties.put("historicalReferences", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(Map.of(
                "culturalAffiliation", Schema.builder().type(Type.Known.STRING).build(),
                "century", Schema.builder().type(Type.Known.STRING).build(),
                "justificationOfTheNumberingBasedOnLithography", Schema.builder().type(Type.Known.STRING).build(),
                "chronologicalTableOfTheStud", Schema.builder().type(Type.Known.STRING).build(),
                "author", Schema.builder().type(Type.Known.STRING).build()
            ))
            .build())
        .build());

    allProperties.put("descriptiveCharacteristics", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(Map.of(
                "theBuildingMaterial", Schema.builder().type(Type.Known.STRING).build(),
                "type", Schema.builder().type(Type.Known.STRING).build(),
                "color", Schema.builder().type(Type.Known.STRING).build(),
                "implementationTechnique", Schema.builder().type(Type.Known.STRING).build(),
                "stateOfMonument", Schema.builder().type(Type.Known.STRING).build(),
                "valuation", Schema.builder().type(Type.Known.STRING).build()
            ))
            .build())
        .build());

    allProperties.put("showInMainPage", Schema.builder().type(Type.Known.BOOLEAN).build());

    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(schema)
        .build();

    // 3. PROMPT (самое важное)
    String prompt = """
You are an expert archaeological data extraction system.

Your task:
Extract structured data from HTML of a historical monument page and return ONLY valid JSON.

CRITICAL RULES:
1. Return ONLY JSON (no text, no explanation)
2. Do NOT hallucinate missing data → use null
3. Keep Armenian names in Armenian
4. Normalize long text into clean single strings
5. Extract only what exists in HTML
6. Ignore navigation, footer, menu, ads

OUTPUT FORMAT MUST MATCH THIS STRUCTURE EXACTLY:

{
  "name": "Գառնիի հնագույն տաճար",
  "regionId": 1,
  "settlementId": 101,
  "monumentType": "Տաճար",
  "specialName": "Արևի տաճար",
  "anotherNames": [
    "Գառնիի տաճար",
    "Հեթանոսական տաճար"
  ],
  "history": "Հուշարձանը կառուցվել է մ.թ. առաջին դարում և հանդիսանում է հայկական հնագույն ճարտարապետության կարևոր նմուշ։",

  "originalAffiliation": "Հայկական հեթանոսական մշակույթ",
  "storageUnitName": "Պատմամշակութային արգելոց-թանգարան",
  "condition": "Լավ",

  "bibliography": [
    {
      "urls": [
        "հայկական պատմության ուսումնասիրություն",
        "հնագիտական հետազոտությունների ժողովածու"
      ]
    }
  ],

  "topographics": [
    {
      "region": "Կոտայքի մարզ",
      "address": "Գառնի համայնք",
      "topography": "Բարձրադիր սարահարթ",
      "distanceFromResidence": "Բնակավայրից մոտ մեկ կիլոմետր",
      "altitude": 1400,
      "hydrography": "Մոտակայքում հոսում է Ազատ գետը",
      "description": "Գտնվում է ժայռոտ բարձրության վրա"
    }
  ],

  "historicalReferences": [
    {
      "culturalAffiliation": "Հայկական հեթանոսական մշակույթ",
      "century": "Առաջին դար",
      "justificationOfTheNumberingBasedOnLithography": "...",
      "chronologicalTableOfTheStud": "...",
      "author": "..."
    }
  ],

  "descriptiveCharacteristics": [
    {
      "theBuildingMaterial": "Բազալտ",
      "type": "Կրոնական կառույց",
      "color": "Մոխրագույն",
      "implementationTechnique": "Քարակերտ շինարարություն",
      "stateOfMonument": "Վերականգնված",
      "valuation": "Բարձր պատմամշակութային արժեք"
    }
  ],
  "showInMainPage": false
}

NOW EXTRACT DATA FROM THIS HTML:

""" + html;

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

//  @Retryable(
//      retryFor = { Exception.class },
//      maxAttempts = 4,
//      backoff = @Backoff(delay = 10000, multiplier = 2.0)
//  )
  public String requestGeminiForMonuments(MonumentRequestDto monumentRequestDto) {

    Map<String, Schema> allProperties = new HashMap<>();

    allProperties.put("nameHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("specialNameHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("specialNameEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("specialNameFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("anotherNamesHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("anotherNamesEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("anotherNamesFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("monumentTypeHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("monumentTypeEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("monumentTypeFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("topographicRegionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographicRegionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographicRegionFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("historyHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("historyEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("historyFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("originalAffiliationHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("originalAffiliationEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("originalAffiliationFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("storageUnitNameHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("storageUnitNameEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("storageUnitNameFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("conditionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("conditionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("conditionFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("addressHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("addressEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("addressFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("topographyHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographyEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("topographyFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("distanceFromResidenceHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("distanceFromResidenceEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("distanceFromResidenceFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("hydrographyHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("hydrographyEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("hydrographyFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("descriptionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("culturalAffiliationHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("culturalAffiliationEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("culturalAffiliationFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("centuryHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("centuryEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("centuryFr", Schema.builder().type(Type.Known.STRING).build());

    Map<String, Schema> bibliographyProperties = new HashMap<>();
    bibliographyProperties.put("titleHy", Schema.builder().type(Type.Known.STRING).build());
    bibliographyProperties.put("titleEn", Schema.builder().type(Type.Known.STRING).build());
    bibliographyProperties.put("titleFr", Schema.builder().type(Type.Known.STRING).build());
    bibliographyProperties.put("url", Schema.builder().type(Type.Known.STRING).build());

    Schema bibliographyItemSchema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(bibliographyProperties)
        .build();

    allProperties.put("bibliography", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(bibliographyItemSchema)
        .build());

    allProperties.put("justificationOfTheNumberingBasedOnLithographyHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("justificationOfTheNumberingBasedOnLithographyEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("justificationOfTheNumberingBasedOnLithographyFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("chronologicalTableOfTheStudHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheStudEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheStudFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("chronologicalTableOfTheMonumentsStudyHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheMonumentsStudyEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("chronologicalTableOfTheMonumentsStudyFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("authorHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("theBuildingMaterialHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("theBuildingMaterialEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("theBuildingMaterialFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("openingsEntrancesHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("openingsEntrancesEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("openingsEntrancesFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("constructionsHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("constructionsEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("constructionsFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("roofHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("roofEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("roofFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("typeHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("typeEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("typeFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("colorHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("colorEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("colorFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("implementationTechniqueHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("implementationTechniqueEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("implementationTechniqueFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("stateOfMonumentHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("stateOfMonumentEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("stateOfMonumentFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("valuationHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("valuationEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("valuationFr", Schema.builder().type(Type.Known.STRING).build());

    Map<String, Schema> monumentVideosProperties = new HashMap<>();
    monumentVideosProperties.put("videoTitleHy", Schema.builder().type(Type.Known.STRING).build());
    monumentVideosProperties.put("videoTitleEn", Schema.builder().type(Type.Known.STRING).build());
    monumentVideosProperties.put("videoTitleFr", Schema.builder().type(Type.Known.STRING).build());
    monumentVideosProperties.put("url", Schema.builder().type(Type.Known.STRING).build());

    Schema monumentVideoItemSchema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(monumentVideosProperties)
        .build();

    allProperties.put("videos", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(monumentVideoItemSchema)
        .build());

    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

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

    return response.text();
  }

  public String requestGeminiForPrograms(ProgramRequestDto programRequestDto) {
    Map<String, Schema> allProperties = new HashMap<>();

    allProperties.put("titleHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("titleEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("titleFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("descriptionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionFr", Schema.builder().type(Type.Known.STRING).build());

    Map<String, Schema> linkProperties = new HashMap<>();
    linkProperties.put("linkTitleHy", Schema.builder().type(Type.Known.STRING).build());
    linkProperties.put("linkTitleEn", Schema.builder().type(Type.Known.STRING).build());
    linkProperties.put("linkTitleFr", Schema.builder().type(Type.Known.STRING).build());
    linkProperties.put("link", Schema.builder().type(Type.Known.STRING).build());

    Schema linkItemSchema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(linkProperties)
        .build();

    allProperties.put("links", Schema.builder()
        .type(Type.Known.ARRAY)
        .items(linkItemSchema)
        .build());

    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(schema)
        .build();

    String prompt = """
    I am providing you with a DTO containing Armenian historical monument data.
    You must translate all text fields into English and French.
            
    CRITICAL RULES:
    1. Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors. If there are issues, correct them while preserving the original meaning.
    2. For the 'links' array: translate the title of each link into English and French, and keep the 'link' URL exactly as it is (DO NOT modify or translate URLs).
    3. Populate all fields according to the requested JSON schema.
            
    Here is the DTO data:
    """ + programRequestDto.toString();

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

  public String requestGeminiForLibrary(LibraryRequestDto libraryRequestDto) {
    Map<String, Schema> allProperties = new HashMap<>();

    allProperties.put("titleHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("titleEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("titleFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("descriptionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("copyrightTextHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("copyrightTextEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("copyrightTextFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("authorsHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorsEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("authorsFr", Schema.builder().type(Type.Known.STRING).build());

    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(schema)
        .build();

    String prompt = """
    I am providing you with a DTO containing Armenian historical library data.
    You must translate all text fields into English and French.
            
    CRITICAL RULES:
    1. Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors. If there are issues, correct them while preserving the original meaning.
    2. Populate all fields according to the requested JSON schema.
            
    Here is the DTO data:
    """ + libraryRequestDto.toString();

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

  public String requestGeminiForTeamMember(TeamMemberRequestDto teamMemberRequestDto) {
    Map<String, Schema> allProperties = new HashMap<>();

    allProperties.put("nameHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("nameFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("surnameHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("surnameEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("surnameFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("descriptionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("descriptionFr", Schema.builder().type(Type.Known.STRING).build());

    allProperties.put("positionHy", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("positionEn", Schema.builder().type(Type.Known.STRING).build());
    allProperties.put("positionFr", Schema.builder().type(Type.Known.STRING).build());

    Schema schema = Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(allProperties)
        .build();

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(schema)
        .build();

    String prompt = """
    I am providing you with a DTO containing our new team member data.
    You must translate all text fields into English and French.
            
    CRITICAL RULES:
    1. Do not modify the Armenian text unless it contains grammatical, spelling, or punctuation errors. If there are issues, correct them while preserving the original meaning.
    2. Populate all fields according to the requested JSON schema.
            
    Here is the DTO data:
    """ + teamMemberRequestDto.toString();

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














  public MonumentTranslationDto translateMonument(
      Monument monument,
      TranslationLanguage language
  ) {

    String prompt = buildTranslationPrompt(monument, language);

    GenerateContentConfig config = GenerateContentConfig.builder()
        .responseMimeType("application/json")
        .responseSchema(createTranslationSchema())
        .build();


    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(prompt)
                .build()
        ))
        .build();


    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );


    try {

      return objectMapper.readValue(
          response.text(),
          MonumentTranslationDto.class
      );

    } catch (JsonProcessingException e) {

      throw new RuntimeException(
          "Cannot parse Gemini translation response",
          e
      );
    }
  }

  private String buildTranslationPrompt(
      Monument monument,
      TranslationLanguage language
  ) {

    String targetLanguage =
        language == TranslationLanguage.en
            ? "English"
            : "French";


    return """
        You are a professional translator specializing in Armenian historical monuments.

        Translate the Armenian monument data into %s.

        RULES:
        1. Translate only text.
        2. Preserve historical terminology.
        3. Do not summarize.
        4. Do not add information.
        5. Return ONLY valid JSON matching the schema.
        6. Include every field defined in the schema.
        7. Never omit properties.
        8. If a value is missing, return null instead of the string "null".
        9. Translate every text field.
        10. Translate titles inside videos and bibliography.
        11. Do not translate ids, urls or coordinates.


        Armenian monument data:

        %s

        """
        .formatted(
            targetLanguage,
            createArmenianTranslationObject(monument)
        );
  }

  private Map<String,Object> createArmenianTranslationObject(
      Monument monument
  ) {

    Map<String,Object> data = new HashMap<>();


    data.put(
        "name",
        monument.getNameHy()
    );

    data.put(
        "specialName",
        monument.getSpecialNameHy()
    );

    data.put(
        "anotherNames",
        monument.getAnotherNamesHy()
    );

    data.put(
        "history",
        monument.getHistoryHy()
    );

    data.put(
        "originalAffiliation",
        monument.getOriginalAffiliationHy()
    );

    data.put(
        "storageUnitName",
        monument.getStorageUnitNameHy()
    );

    data.put(
        "condition",
        monument.getConditionHy()
    );

    data.put(
        "videos",
        monument.getVideos()
            .stream()
            .map(v -> {

              Map<String, Object> map = new HashMap<>();

              map.put(
                  "title",
                  v.getTitleHy()
              );

              return map;

            })
            .toList()
    );

    data.put(
        "bibliography",
        monument.getBibliography()
            .stream()
            .map(b -> {

              Map<String, Object> map = new HashMap<>();

              map.put(
                  "title",
                  b.getTitleHy()
              );

              return map;

            })
            .toList()
    );


    data.put(
        "topographics",
        createTopographicTranslationObject(
            monument.getTopographics()
        )
    );


    data.put(
        "historicalReferences",
        createHistoricalTranslationObject(
            monument.getHistoricalReferences()
        )
    );


    data.put(
        "descriptiveCharacteristics",
        createDescriptiveTranslationObject(
            monument.getDescriptiveCharacteristics()
        )
    );


    return data;
  }

  private Map<String,Object> createTopographicTranslationObject(
      Topographic topographic
  ) {

    if(topographic == null)
      return null;


    Map<String,Object> data = new HashMap<>();


    data.put(
        "region",
        topographic.getRegionHy()
    );

    data.put(
        "address",
        topographic.getAddressHy()
    );

    data.put(
        "topography",
        topographic.getTopographyHy()
    );

    data.put(
        "distanceFromResidence",
        topographic.getDistanceFromResidenceHy()
    );

    data.put(
        "altitude",
        topographic.getAltitudeHy()
    );

    data.put(
        "hydrography",
        topographic.getHydrographyHy()
    );

    data.put(
        "description",
        topographic.getDescriptionHy()
    );


    return data;
  }

  private Map<String,Object> createHistoricalTranslationObject(
      HistoricalReference reference
  ) {

    if(reference == null)
      return null;


    Map<String,Object> data = new HashMap<>();


    data.put(
        "culturalAffiliation",
        reference.getCulturalAffiliationHy()
    );


    data.put(
        "century",
        reference.getCenturyHy()
    );


    data.put(
        "justificationOfTheNumberingBasedOnLithography",
        reference.getJustificationOfTheNumberingBasedOnLithographyHy()
    );


    data.put(
        "chronologicalTableOfTheStud",
        reference.getChronologicalTableOfTheStudHy()
    );


    data.put(
        "chronologicalTableOfTheMonumentsStudy",
        reference.getChronologicalTableOfTheMonumentsStudyHy()
    );


    data.put(
        "author",
        reference.getAuthorHy()
    );


    return data;
  }


  private Map<String,Object> createDescriptiveTranslationObject(
      DescriptiveCharacteristicReference reference
  ) {

    if(reference == null)
      return null;


    Map<String,Object> data = new HashMap<>();


    data.put(
        "theBuildingMaterial",
        reference.getTheBuildingMaterialHy()
    );

    data.put(
        "openingsEntrances",
        reference.getOpeningsEntrancesHy()
    );

    data.put(
        "constructions",
        reference.getConstructionsHy()
    );

    data.put(
        "roof",
        reference.getRoofHy()
    );

    data.put(
        "type",
        reference.getTypeHy()
    );

    data.put(
        "color",
        reference.getColorHy()
    );

    data.put(
        "implementationTechnique",
        reference.getImplementationTechniqueHy()
    );

    data.put(
        "stateOfMonument",
        reference.getStateOfMonumentHy()
    );

    data.put(
        "valuation",
        reference.getValuationHy()
    );


    return data;
  }

  private Schema createTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("name", stringSchema());

    properties.put("monumentType", stringSchema());

    properties.put("specialName", stringSchema());

    properties.put("anotherNames", stringSchema());

    properties.put("history", stringSchema());

    properties.put("originalAffiliation", stringSchema());

    properties.put("storageUnitName", stringSchema());

    properties.put("condition", stringSchema());

    properties.put("topographics", topographicSchema());

    properties.put("historicalReferences", historicalSchema());

    properties.put("descriptiveCharacteristics", descriptiveSchema());

    properties.put(
        "videos",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(videoSchema())
            .build()
    );

    properties.put(
        "bibliography",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(bibliographySchema())
            .build()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema topographicSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("region", stringSchema());

    properties.put("address", stringSchema());

    properties.put("topography", stringSchema());

    properties.put(
        "distanceFromResidence",
        stringSchema()
    );

    properties.put(
        "altitude",
        stringSchema()
    );

    properties.put(
        "hydrography",
        stringSchema()
    );

    properties.put(
        "description",
        stringSchema()
    );


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema historicalSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put(
        "culturalAffiliation",
        stringSchema()
    );

    properties.put(
        "century",
        stringSchema()
    );

    properties.put(
        "justificationOfTheNumberingBasedOnLithography",
        stringSchema()
    );

    properties.put(
        "chronologicalTableOfTheStud",
        stringSchema()
    );

    properties.put(
        "chronologicalTableOfTheMonumentsStudy",
        stringSchema()
    );

    properties.put(
        "author",
        stringSchema()
    );


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema descriptiveSchema() {

    Map<String, Schema> properties = new HashMap<>();


    properties.put(
        "theBuildingMaterial",
        stringSchema()
    );

    properties.put(
        "openingsEntrances",
        stringSchema()
    );

    properties.put(
        "constructions",
        stringSchema()
    );

    properties.put(
        "roof",
        stringSchema()
    );

    properties.put(
        "type",
        stringSchema()
    );

    properties.put(
        "color",
        stringSchema()
    );

    properties.put(
        "implementationTechnique",
        stringSchema()
    );

    properties.put(
        "stateOfMonument",
        stringSchema()
    );

    properties.put(
        "valuation",
        stringSchema()
    );


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema videoSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put(
        "title",
        stringSchema()
    );


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema bibliographySchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put(
        "title",
        stringSchema()
    );


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema stringSchema() {
    return Schema.builder()
        .type(Type.Known.STRING)
        .build();
  }

}