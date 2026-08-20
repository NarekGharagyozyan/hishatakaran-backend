package org.hishatakaran.backend.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.Exhibition;
import org.hishatakaran.backend.entity.ExhibitionImage;
import org.hishatakaran.backend.entity.ExhibitionLink;
import org.hishatakaran.backend.entity.ExhibitionVideo;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramEpisode;
import org.hishatakaran.backend.entity.ProgramImage;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.AboutUsRequestDto;
import org.hishatakaran.backend.model.AboutUsTranslationDto;
import org.hishatakaran.backend.model.CulturalHeritagesRequestDto;
import org.hishatakaran.backend.model.CulturalHeritagesTranslationDto;
import org.hishatakaran.backend.model.ExhibitionImageTranslationDto;
import org.hishatakaran.backend.model.ExhibitionTranslationDto;
import org.hishatakaran.backend.model.ExhibitionVideoTranslationDto;
import org.hishatakaran.backend.model.LibraryTranslationDto;
import org.hishatakaran.backend.model.LinkTranslationDto;
import org.hishatakaran.backend.model.MainPageRequestDto;
import org.hishatakaran.backend.model.MainPageTranslationDto;
import org.hishatakaran.backend.model.MonumentTranslationDto;
import org.hishatakaran.backend.model.MonumentTypeTranslateDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationRequestDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationTranslationDto;
import org.hishatakaran.backend.model.ProgramEpisodeTranslationDto;
import org.hishatakaran.backend.model.ProgramImageTranslationDto;
import org.hishatakaran.backend.model.ProgramTranslationDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.model.TeamMemberTranslationDto;
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


  public void translateProgram(
      Program program,
      TranslationLanguage language
  ) throws JsonProcessingException {

    String prompt =
        buildProgramTranslationPrompt(
            program,
            language
        );

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(programTranslationSchema())
            .build();

    Content content =
        Content.builder()
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

    ProgramTranslationDto dto =
        objectMapper.readValue(
            response.text(),
            ProgramTranslationDto.class
        );

    applyTranslation(
        program,
        dto,
        language
    );
  }

  private String buildProgramTranslationPrompt(
      Program program,
      TranslationLanguage language
  ) {

    String targetLanguage =
        language == TranslationLanguage.en
            ? "English"
            : "French";

    return """
      You are a professional translator.
      
      Translate the Armenian program data into %s.
      
      RULES:
      
      1. Translate only text.
      2. Do not summarize.
      3. Do not rewrite.
      4. Do not add information.
      5. Do not invent values.
      6. Keep the exact order of all arrays.
      7. The array indexes must correspond exactly to the original data.
      8. URLs must NOT be translated or modified.
      9. If a value is null, return null.
      10. If the input contains the literal characters "\\n", preserve them as the literal characters "\\n". Do not convert them into an actual newline or don't delete it.
      11. Return ONLY JSON matching the provided schema.
      
      TRANSLATION REQUIREMENTS:
      
      - Translate the program title.
      - Translate the program description.
      - Translate the program text/content.
      - Translate every link title.
      - Translate every episode title.
      - Translate every image caption.
      
      IMPORTANT:
      
      The "links" array must contain exactly the same number of items
      as the input "links" array.
      
      The "episodes" array must contain exactly the same number of items
      as the input "episodes" array.
      
      The "images" array must contain exactly the same number of items
      as the input "images" array.
      
      Do not change the order of any array.
      
      Armenian program data:
      
      %s
      """
        .formatted(
            targetLanguage,
            createTranslationObject(program)
        );
  }

  private Map<String, Object> createTranslationObject(
      Program program
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", program.getTitleHy());
    data.put("description", program.getDescriptionHy());
    data.put("program", program.getProgramHy());
    data.put("links", createLinks(program.getLinks()));
    data.put("episodes", createEpisodes(program.getEpisodes()));
    data.put("images", createImages(program.getImages()));

    return data;
  }

  private List<Map<String, Object>> createLinks(
      List<ProgramLink> links
  ) {

    if (links == null) {
      return null;
    }

    return links.stream()
        .map(link -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("linkTitle", link.getTitleHy());

          return map;

        })
        .toList();
  }

  private List<Map<String, Object>> createEpisodes(
      List<ProgramEpisode> episodes
  ) {

    if (episodes == null) {
      return null;
    }

    return episodes.stream()
        .map(episode -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("title", episode.getTitleHy());

          return map;

        })
        .toList();
  }

  private List<Map<String, Object>> createImages(
      List<ProgramImage> images
  ) {

    if (images == null) {
      return null;
    }

    return images.stream()
        .map(image -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("caption", image.getCaptionHy());

          return map;

        })
        .toList();
  }


  private Schema programTranslationSchema() {

    Map<String, Schema> properties =
        new HashMap<>();

    properties.put("title", stringSchema());
    properties.put("description", stringSchema());
    properties.put("program", stringSchema());

    properties.put("links",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(linkSchema())
            .build()
    );

    properties.put(
        "episodes",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(episodeSchema())
            .build()
    );

    properties.put(
        "images",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(imageSchema())
            .build()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema linkSchema() {

    Map<String, Schema> properties =
        new HashMap<>();

    properties.put("linkTitle", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema episodeSchema() {

    Map<String, Schema> properties =
        new HashMap<>();

    properties.put("title", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private void applyTranslation(
      Program program,
      ProgramTranslationDto dto,
      TranslationLanguage language
  ) {

    if (dto == null) {
      return;
    }

    boolean en =
        language == TranslationLanguage.en;

    if (en) {

      program.setTitleEn(dto.getTitle());
      program.setDescriptionEn(dto.getDescription());
      program.setProgramEn(dto.getProgram());

    } else {

      program.setTitleFr(dto.getTitle());
      program.setDescriptionFr(dto.getDescription());
      program.setProgramFr(dto.getProgram());
    }

    applyLinksTranslation(
        program.getLinks(),
        dto.getLinks(),
        language
    );

    applyEpisodesTranslation(
        program.getEpisodes(),
        dto.getEpisodes(),
        language
    );

    applyImagesTranslation(
        program.getImages(),
        dto.getImages(),
        language
    );
  }

  private void applyLinksTranslation(
      List<ProgramLink> links,
      List<LinkTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (links == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            links.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {
      ProgramLink link = links.get(i);
      LinkTranslationDto translation = dto.get(i);
      if (language == TranslationLanguage.en) {
        link.setTitleEn(translation.getLinkTitle());
      }
      else {
        link.setTitleFr(translation.getLinkTitle());
      }
    }
  }

  private void applyEpisodesTranslation(
      List<ProgramEpisode> episodes,
      List<ProgramEpisodeTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (episodes == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            episodes.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {

      ProgramEpisode episode =
          episodes.get(i);

      ProgramEpisodeTranslationDto translation =
          dto.get(i);

      if (language == TranslationLanguage.en) {

        episode.setTitleEn(
            translation.getTitle()
        );

      } else {

        episode.setTitleFr(
            translation.getTitle()
        );
      }
    }
  }

  private void applyImagesTranslation(
      List<ProgramImage> images,
      List<ProgramImageTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (images == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            images.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {

      ProgramImage image =
          images.get(i);

      ProgramImageTranslationDto translation =
          dto.get(i);

      if (language == TranslationLanguage.en) {

        image.setCaptionEn(
            translation.getCaption()
        );

      } else {

        image.setCaptionFr(
            translation.getCaption()
        );
      }
    }
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

    String targetLanguage = language == TranslationLanguage.en
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
        12. If there are html tags in the text, they must remain in place after translation.
        
        13. Historical monument place name transliteration requirement:
            If the Armenian text contains any geographical place name or location-related proper name (including but not limited to monasteries, churches, fortresses, archaeological sites, villages, cities, regions, mountains, rivers, valleys, streets, or historical locations), preserve the translated name and append the romanized Armenian original in parentheses immediately after it.
        
            Format:
            Translated place name [Armenian romanization]
        
            Rules:
            - For English translation: use Library of Congress Armenian romanization rules.
            - For French translation: use French-compatible romanization based on Armenian romanization rules.
            - Apply this rule in every text field, including monument names, descriptions, historical information, video titles, bibliography titles, and nested text values.
            - Apply this rule even when the field contains only a single monument/place name.
            - Never omit the transcription when a place name exists.
            - The required romanization is not considered additional information and must not be removed.
            - If some name or place name already have transcription, don't repeat ir in another place.
        
        14. Preserve the exact punctuation and structure of signatures when translating them.
        
            If a signature consists of initials separated by Armenian punctuation ․, convert each initial to its Latin equivalent and preserve the same structure.
            Do not add or remove punctuation that is present in the original signature.
            If the original signature contains multiple signatures separated by a comma, preserve the comma and spacing.
        
            Examples:
              Ս․Դ․ → S.D. → S.D
              Ա․Հ․, Ս․Դ․ → A.H., S.D. → A.H., S.D
        
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

    data.put("name", monument.getNameHy());
    data.put("specialName", monument.getSpecialNameHy());
    data.put("anotherNames", monument.getAnotherNamesHy());
    data.put("history", monument.getHistoryHy());
    data.put("originalAffiliation", monument.getOriginalAffiliationHy());
    data.put("storageUnitName", monument.getStorageUnitNameHy());
    data.put("individuallyCertifiablePartsOfTheStorageUnit", monument.getIndividuallyCertifiablePartsOfTheStorageUnitHy());
    data.put("signature", monument.getSignatureHy());

    data.put(
        "videos",
        monument.getVideos()
            .stream()
            .map(v -> {

              Map<String, Object> map = new HashMap<>();

              map.put("title", v.getTitleHy());

              return map;

            })
            .toList()
    );

    data.put(
        "images",
        monument.getImages()
            .stream()
            .map(v -> {

              Map<String, Object> map = new HashMap<>();

              map.put("caption", v.getCaptionHy());

              return map;

            })
            .toList()
    );

    data.put(
        "measurements",
        monument.getMeasurements()
            .stream()
            .map(v -> {

              Map<String, Object> map = new HashMap<>();

              map.put("caption", v.getCaptionHy());

              return map;

            })
            .toList()
    );

    data.put(
        "footnotes",
        monument.getFootnotes()
            .stream()
            .map(f -> {
              Map<String, Object> map = new HashMap<>();

              map.put("orderNumber", f.getOrderNumber());
              map.put("text", f.getTextHy());

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

              map.put("title", b.getTitleHy());

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

    data.put("region", topographic.getRegionHy());
    data.put("address", topographic.getAddressHy());
    data.put("topography", topographic.getTopographyHy());
    data.put("distanceFromResidence", topographic.getDistanceFromResidenceHy());
    data.put("altitude", topographic.getAltitudeHy());
    data.put("hydrography", topographic.getHydrographyHy());
    data.put("description", topographic.getDescriptionHy());

    return data;
  }

  private Map<String,Object> createHistoricalTranslationObject(
      HistoricalReference reference
  ) {

    if(reference == null)
      return null;

    Map<String,Object> data = new HashMap<>();

    data.put("culturalAffiliation", reference.getCulturalAffiliationHy());
    data.put("justificationOfTheNumberingBasedOnReliableDocument", reference.getJustificationOfTheNumberingBasedOnReliableDocumentHy());
    data.put("justificationOfTheNumberingBasedOnBibliographicalSources", reference.getJustificationOfTheNumberingBasedOnBibliographicalSourcesHy());
    data.put("justificationOfTheNumberingAccordingIconography", reference.getJustificationOfTheNumberingAccordingIconographyHy());
    data.put("justificationOfTheNumberingBasedOnEvidence", reference.getJustificationOfTheNumberingBasedOnEvidenceHy());
    data.put("justificationOfTheNumberingBasedOnLithography", reference.getJustificationOfTheNumberingBasedOnLithographyHy());
    data.put("chronologicalTableOfTheStud", reference.getChronologicalTableOfTheStudHy());
    data.put("chronologicalTableOfTheMonumentsStudy", reference.getChronologicalTableOfTheMonumentsStudyHy());
    data.put("briefHistoricalOverview", reference.getBriefHistoricalOverviewHy());
    data.put("author", reference.getAuthorHy());
    data.put("sourceForDeterminingTheAuthor", reference.getSourceForDeterminingTheAuthorHy());

    return data;
  }


  private Map<String,Object> createDescriptiveTranslationObject(
      DescriptiveCharacteristicReference reference
  ) {

    if(reference == null)
      return null;

    Map<String,Object> data = new HashMap<>();

    data.put("theBuildingMaterial", reference.getTheBuildingMaterialHy());
    data.put("archeologicalOverviewStratigraphyFindings", reference.getArcheologicalOverviewStratigraphyFindingsHy());
    data.put("architecturalOverview", reference.getArchitecturalOverviewHy());
    data.put("decorativeAndMonumentalFeaturesCompositionColours", reference.getDecorativeAndMonumentalFeaturesCompositionColoursHy());
    data.put("openingsWindows", reference.getOpeningsWindowsHy());
    data.put("openingsEntrances", reference.getOpeningsEntrancesHy());
    data.put("constructions", reference.getConstructionsHy());
    data.put("roof", reference.getRoofHy());
    data.put("type", reference.getTypeHy());
    data.put("levelsOfConstruction", reference.getLevelsOfConstructionHy());
    data.put("exterior", reference.getExteriorHy());
    data.put("length", reference.getLengthHy());
    data.put("width", reference.getWidthHy());
    data.put("height", reference.getHeightHy());
    data.put("depthThickness", reference.getDepthThicknessHy());
    data.put("area", reference.getAreaHy());
    data.put("lengthOfSpan", reference.getLengthOfSpanHy());
    data.put("implementationTechnique", reference.getImplementationTechniqueHy());
    data.put("stateOfMonument", reference.getStateOfMonumentHy());
    data.put("valuation", reference.getValuationHy());
    data.put("monumentDataUpdate", reference.getMonumentDataUpdateHy());

    return data;
  }

  private Schema createTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("name", stringSchema());
    properties.put("specialName", stringSchema());
    properties.put("anotherNames", stringSchema());
    properties.put("history", stringSchema());
    properties.put("originalAffiliation", stringSchema());
    properties.put("storageUnitName", stringSchema());
    properties.put("individuallyCertifiablePartsOfTheStorageUnit", stringSchema());
    properties.put("signature", stringSchema());
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
        "images",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(imageSchema())
            .build()
    );
    properties.put(
        "measurements",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(measurementSchema())
            .build()
    );
    properties.put(
        "footnotes",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(footnoteSchema())
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
    properties.put("distanceFromResidence", stringSchema());
    properties.put("altitude", stringSchema());
    properties.put("hydrography", stringSchema());
    properties.put("description", stringSchema());


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema footnoteSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put(
        "orderNumber",
        Schema.builder()
            .type(Type.Known.INTEGER)
            .build()
    );

    properties.put(
        "text",
        stringSchema()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema historicalSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("culturalAffiliation", stringSchema());
    properties.put("justificationOfTheNumberingBasedOnReliableDocument", stringSchema());
    properties.put("justificationOfTheNumberingBasedOnBibliographicalSources", stringSchema());
    properties.put("justificationOfTheNumberingAccordingIconography", stringSchema());
    properties.put("justificationOfTheNumberingBasedOnEvidence", stringSchema());
    properties.put("justificationOfTheNumberingBasedOnLithography", stringSchema());
    properties.put("chronologicalTableOfTheStud", stringSchema());
    properties.put("chronologicalTableOfTheMonumentsStudy", stringSchema());
    properties.put("author", stringSchema());
    properties.put("sourceForDeterminingTheAuthor", stringSchema());
    properties.put("briefHistoricalOverview", stringSchema());


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema descriptiveSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("archeologicalOverviewStratigraphyFindings", stringSchema());
    properties.put("architecturalOverview", stringSchema());
    properties.put("decorativeAndMonumentalFeaturesCompositionColours", stringSchema());
    properties.put("theBuildingMaterial", stringSchema());
    properties.put("openingsEntrances", stringSchema());
    properties.put("openingsWindows", stringSchema());
    properties.put("constructions", stringSchema());
    properties.put("levelsOfConstruction", stringSchema());
    properties.put("roof", stringSchema());
    properties.put("type", stringSchema());
    properties.put("exterior", stringSchema());
    properties.put("implementationTechnique", stringSchema());
    properties.put("length", stringSchema());
    properties.put("width", stringSchema());
    properties.put("height", stringSchema());
    properties.put("depthThickness", stringSchema());
    properties.put("area", stringSchema());
    properties.put("lengthOfSpan", stringSchema());
    properties.put("stateOfMonument", stringSchema());
    properties.put("valuation", stringSchema());
    properties.put("monumentDataUpdate", stringSchema());


    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema videoSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("title", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema imageSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("caption", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema measurementSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("caption", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema bibliographySchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("title", stringSchema());

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

  public void translateLibrary(
      Library library,
      TranslationLanguage language
  ) throws JsonProcessingException {

    String prompt =
        buildLibraryTranslationPrompt(
            library,
            language
        );

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(libraryTranslationSchema())
            .build();

    Content content =
        Content.builder()
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

    LibraryTranslationDto dto =
        objectMapper.readValue(
            response.text(),
            LibraryTranslationDto.class
        );

    applyLibraryTranslation(
        library,
        dto,
        language
    );
  }

  private String buildLibraryTranslationPrompt(
      Library library,
      TranslationLanguage language
  ) {

    String targetLanguage = language == TranslationLanguage.en
        ? "English"
        : "French";

    return """
        You are a professional translator.
        
        Translate the Armenian library metadata into %s.
        
        RULES:
        
        1. Translate ALL textual fields.
        2. Return ONLY valid JSON matching the provided schema.
        3. Do not summarize.
        4. Do not rewrite or omit information.
        5. If a field is null, return null.
        6. Preserve punctuation, years, numbering and bibliography formatting.
        7. Translate personal names using standard %s transliteration.
        8. Never leave Armenian text in translated fields.
        
        SPECIAL RULE FOR "authors":
        
        The "authors" field MUST be fully translated.
        
        Translate:
        - author names
        - initials
        - book titles
        - place names
        - organization names
        
        Keep unchanged:
        - years
        - commas
        - punctuation
        - bibliography formatting
        
        Example:
        
        Input:
        Սարգսյան Գ․, Գնունի Ա․, Մկրտչյան Լ․, 2022- Սարգսյան Գագիկ, Գնունի Արտակ, Մկրտչյան Լևոն, Արցախի Հանրապետության Քաշաթաղի շրջանի ամրոցները, Երևան 2022
        
        Output (English):
        Sargsyan G., Gnuni A., Mkrtchyan L., 2022 – Gagik Sargsyan, Artak Gnuni, Levon Mkrtchyan, Fortresses of Kashatagh Region of the Republic of Artsakh, Yerevan 2022
        
        Do NOT return Armenian text inside the "authors" field.
        
        Armenian library data:
        
        %s
        """
        .formatted(
            targetLanguage,
            targetLanguage,
            createLibraryTranslationObject(library)
        );
  }

  private Map<String, Object> createLibraryTranslationObject(
      Library library
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", library.getTitleHy());
    data.put("description", library.getDescriptionHy());
    data.put("copyrightText", library.getCopyrightTextHy());
    data.put("authors", library.getAuthorsHy());

    return data;
  }

  private void applyLibraryTranslation(
      Library library,
      LibraryTranslationDto dto,
      TranslationLanguage language
  ) {

    if (dto == null) {
      return;
    }

    boolean en = language == TranslationLanguage.en;

    if (en) {

      library.setTitleEn(dto.getTitle());
      library.setDescriptionEn(dto.getDescription());
      library.setCopyrightTextEn(dto.getCopyrightText());
      library.setAuthorsEn(dto.getAuthors());

    } else {

      library.setTitleFr(dto.getTitle());
      library.setDescriptionFr(dto.getDescription());
      library.setCopyrightTextFr(dto.getCopyrightText());
      library.setAuthorsFr(dto.getAuthors());
    }
  }

  private Schema libraryTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("title", stringSchema());
    properties.put("description", stringSchema());
    properties.put("copyrightText", stringSchema());
    properties.put("authors", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public TeamMemberTranslationDto translateTeamMember(
      TeamMembers member,
      TranslationLanguage language
  ) throws JsonProcessingException {


    String prompt = buildTeamMemberTranslationPrompt(
        member,
        language
    );

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(teamMemberTranslationSchema())
            .build();

    Content content =
        Content.builder()
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

    return objectMapper.readValue(
        response.text(),
        TeamMemberTranslationDto.class
    );
  }

  private String buildTeamMemberTranslationPrompt(
      TeamMembers member,
      TranslationLanguage language
  ) {

    String targetLanguage = language == TranslationLanguage.en
        ? "English"
        : "French";

    return """
      You are a professional translator.
  
      Translate Armenian team member information into %s.
  
      RULES:
  
      1. Translate ALL textual fields.
      2. Return ONLY JSON matching the schema.
      3. Do not summarize.
      4. Do not rewrite information.
      5. Preserve the original meaning.
      6. Translate names and surnames using standard %s transliteration.
      7. If a field is null, return null.
      8. Never return Armenian characters in translated fields.
      9. Preserve the exact punctuation and structure of signatures when translating them.
        
            If a signature consists of initials separated by Armenian punctuation ․, convert each initial to its Latin equivalent and preserve the same structure.
            Do not add or remove punctuation that is present in the original signature.
            If the original signature contains multiple signatures separated by a comma, preserve the comma and spacing.
        
            Examples:
              Ս․Դ․ → S.D. → S.D
              Ա․Հ․, Ս․Դ․ → A.H., S.D. → A.H., S.D
  
      Armenian team member data:
  
      %s
      """
        .formatted(
            targetLanguage,
            targetLanguage,
            createTeamMemberTranslationObject(member)
        );
  }

  private Map<String,Object> createTeamMemberTranslationObject(
      TeamMembers member
  ) {

    Map<String,Object> data = new HashMap<>();

    data.put("fullName", member.getFullNameHy());
    data.put("position", member.getPositionHy());
    data.put("description", member.getDescriptionHy());
    data.put("signature", member.getSignatureHy());

    return data;
  }

  private Schema teamMemberTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("fullName", stringSchema());
    properties.put("position", stringSchema());
    properties.put("description", stringSchema());
    properties.put("signature", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }


  public SettlementTranslationDto translateSettlement(
      SettlementRequestDto dto
  ) throws JsonProcessingException {

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(settlementTranslationSchema())
            .build();

    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(buildSettlementPrompt(dto))
                .build()
        ))
        .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        SettlementTranslationDto.class
    );
  }

  private String buildSettlementPrompt(
      SettlementRequestDto dto
  ) {

    return """
        You are a professional translator.

        The input text is written in Armenian.
        Return Armenian (corrected if needed), English and French.

        RULES

        1. nameHy MUST contain the Armenian title.
           Correct only grammar, spelling and punctuation if necessary.
        2. descriptionHy MUST contain the Armenian text.
           Correct only grammar, spelling and punctuation if necessary.
        3. Translate name into English and French.
        4. Translate description into English and French.
        5. Do not summarize.
        6. Do not rewrite.
        7. Preserve formatting.
        8. Return ONLY JSON matching the schema.

        Armenian data:

        %s
        """
        .formatted(createSettlementTranslationObject(dto));
  }

  private Map<String, Object> createSettlementTranslationObject(
      SettlementRequestDto dto
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("name", dto.getName());
    data.put("description", dto.getDescription());
    data.put(
        "images",
        dto.getImages()
            .stream()
            .map(v -> {
              Map<String, Object> map = new HashMap<>();
              map.put("caption", v.getCaption());
              return map;

            })
            .toList()
    );

    return data;
  }

  private Schema settlementTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("nameHy", stringSchema());
    properties.put("nameEn", stringSchema());
    properties.put("nameFr", stringSchema());
    properties.put("descriptionHy", stringSchema());
    properties.put("descriptionEn", stringSchema());
    properties.put("descriptionFr", stringSchema());

    properties.put(
        "images",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(imageSchema())
            .build()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public MonumentTypeTranslateDto translateMonumentType(
      String armenianName
  ) throws JsonProcessingException {

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(monumentTypesSchema())
            .build();

    Content content =
        Content.builder()
            .parts(List.of(
                Part.builder()
                    .text(buildMonumentTypePrompt(armenianName))
                    .build()
            ))
            .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        MonumentTypeTranslateDto.class
    );
  }

  private String buildMonumentTypePrompt(
      String armenianName
  ) {

    return """
      You are an expert in Armenian monument type.

      Translate the Armenian monument type into English and French.
      
      IMPORTANT:
      
      - This is a proper monument type name.
      - Translate its meaning.
      
      Return ONLY JSON.
      
      Input:
      
      %s
      """
        .formatted(armenianName);
  }

  private Schema monumentTypesSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("nameHy", stringSchema());
    properties.put("nameEn", stringSchema());
    properties.put("nameFr", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public MainPageTranslationDto translateMainPage(
      MainPageRequestDto dto
  ) throws JsonProcessingException {

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(mainPageTranslationSchema())
            .build();

    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(buildMainPagePrompt(dto))
                .build()
        ))
        .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        MainPageTranslationDto.class
    );
  }

  private String buildMainPagePrompt(
      MainPageRequestDto dto
  ) {

    return """
        You are a professional translator.

        The input text is written in Armenian.
        Return Armenian (corrected if needed), English and French.

        RULES

        1. titleHy MUST contain the Armenian title.
           Correct only grammar, spelling and punctuation if necessary.
        2. textHy MUST contain the Armenian text.
           Correct only grammar, spelling and punctuation if necessary.
        3. Translate title into English and French.
        4. Translate text into English and French.
        5. Do not summarize.
        6. Do not rewrite.
        7. Preserve formatting.
        8. Return ONLY JSON matching the schema.

        Armenian data:

        %s
        """
        .formatted(createMainPageTranslationObject(dto));
  }

  private Map<String, Object> createMainPageTranslationObject(
      MainPageRequestDto dto
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", dto.getTitle());
    data.put("text", dto.getText());

    return data;
  }

  private Schema mainPageTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("titleHy", stringSchema());
    properties.put("titleEn", stringSchema());
    properties.put("titleFr", stringSchema());
    properties.put("textHy", stringSchema());
    properties.put("textEn", stringSchema());
    properties.put("textFr", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }


  public AboutUsTranslationDto translateAboutUs(
      AboutUsRequestDto dto
  ) throws JsonProcessingException {

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(aboutUsTranslationSchema())
            .build();

    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(buildAboutUsPrompt(dto))
                .build()
        ))
        .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        AboutUsTranslationDto.class
    );
  }

  private String buildAboutUsPrompt(
      AboutUsRequestDto dto
  ) {

    return """
        You are a professional translator.

        The input text is written in Armenian.
        Return Armenian (corrected if needed), English and French.

        RULES

        1. titleHy MUST contain the Armenian title.
           Correct only grammar, spelling and punctuation if necessary.
        2. subtitleHy MUST contain the Armenian subtitle.
          Correct only grammar, spelling and punctuation if necessary.
        3. textHy MUST contain the Armenian text.
           Correct only grammar, spelling and punctuation if necessary.
        4. Translate title into English and French.
        5. Translate subtitle into English and French.
        6. Translate text into English and French.
        7. Do not summarize.
        8. Do not rewrite.
        9. Preserve formatting.
        10. Return ONLY JSON matching the schema.

        Armenian data:

        %s
        """
        .formatted(createAboutUsTranslationObject(dto));
  }

  private Map<String, Object> createAboutUsTranslationObject(
      AboutUsRequestDto dto
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", dto.getTitle());
    data.put("subtitle", dto.getSubtitle());
    data.put("text", dto.getText());

    return data;
  }

  private Schema aboutUsTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("titleHy", stringSchema());
    properties.put("titleEn", stringSchema());
    properties.put("titleFr", stringSchema());
    properties.put("subtitleHy", stringSchema());
    properties.put("subtitleEn", stringSchema());
    properties.put("subtitleFr", stringSchema());
    properties.put("textHy", stringSchema());
    properties.put("textEn", stringSchema());
    properties.put("textFr", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public CulturalHeritagesTranslationDto translateCulturalHeritages(
      CulturalHeritagesRequestDto dto
  ) throws JsonProcessingException {



    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(culturalHeritagesTranslationSchema())
            .build();

    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(buildCulturalHeritagesPrompt(dto))
                .build()
        ))
        .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        CulturalHeritagesTranslationDto.class
    );
  }

  private String buildCulturalHeritagesPrompt(
      CulturalHeritagesRequestDto dto
  ) {

    return """
        You are a professional translator.

        The input text is written in Armenian.
        Return Armenian (corrected if needed), English and French.

        RULES

        1. titleHy MUST contain the Armenian title.
           Correct only grammar, spelling and punctuation if necessary.
        2. subtitleHy MUST contain the Armenian subtitle.
          Correct only grammar, spelling and punctuation if necessary.
        3. Translate title into English and French.
        4. Translate subtitle into English and French.
        5. Do not summarize.
        6. Do not rewrite.
        7. Preserve formatting.
        8. Return ONLY JSON matching the schema.

        Armenian data:

        %s
        """
        .formatted(createCulturalHeritagesTranslationObject(dto));
  }

  private Map<String, Object> createCulturalHeritagesTranslationObject(
      CulturalHeritagesRequestDto dto
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", dto.getTitle());
    data.put("subtitle", dto.getSubtitle());

    return data;
  }

  private Schema culturalHeritagesTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("titleHy", stringSchema());
    properties.put("titleEn", stringSchema());
    properties.put("titleFr", stringSchema());
    properties.put("subtitleHy", stringSchema());
    properties.put("subtitleEn", stringSchema());
    properties.put("subtitleFr", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }


  public ProgramCulturalHeritageDocumentationTranslationDto translateProgramCulturalHeritageDocumentation(
      ProgramCulturalHeritageDocumentationRequestDto dto
  ) throws JsonProcessingException {



    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(programCulturalHeritageDocumentationTranslationSchema())
            .build();

    Content content = Content.builder()
        .parts(List.of(
            Part.builder()
                .text(buildProgramCulturalHeritageDocumentationPrompt(dto))
                .build()
        ))
        .build();

    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            content,
            config
        );

    return objectMapper.readValue(
        response.text(),
        ProgramCulturalHeritageDocumentationTranslationDto.class
    );
  }

  private String buildProgramCulturalHeritageDocumentationPrompt(
      ProgramCulturalHeritageDocumentationRequestDto dto
  ) {

    return """
        You are a professional translator.

        The input text is written in Armenian.
        Return Armenian (corrected if needed), English and French.

        RULES

        1. titleHy MUST contain the Armenian title.
           Correct only grammar, spelling and punctuation if necessary.
        2. subtitleHy MUST contain the Armenian subtitle.
          Correct only grammar, spelling and punctuation if necessary.
        3. Translate title into English and French.
        4. Translate subtitle into English and French.
        5. Do not summarize.
        6. Do not rewrite.
        7. Preserve formatting.
        8. Return ONLY JSON matching the schema.

        Armenian data:

        %s
        """
        .formatted(createProgramCulturalHeritageDocumentationTranslationObject(dto));
  }

  private Map<String, Object> createProgramCulturalHeritageDocumentationTranslationObject(
      ProgramCulturalHeritageDocumentationRequestDto dto
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", dto.getTitle());
    data.put("subtitle", dto.getSubtitle());

    return data;
  }

  private Schema programCulturalHeritageDocumentationTranslationSchema() {

    Map<String, Schema> properties =
        new HashMap<>();

    properties.put("titleHy", stringSchema());
    properties.put("titleEn", stringSchema());
    properties.put("titleFr", stringSchema());
    properties.put("subtitleHy", stringSchema());
    properties.put("subtitleEn", stringSchema());
    properties.put("subtitleFr", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public void translateExhibition(
      Exhibition exhibition,
      TranslationLanguage language
  ) throws JsonProcessingException {

    String prompt =
        buildExhibitionTranslationPrompt(
            exhibition,
            language
        );

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(exhibitionTranslationSchema())
            .build();

    Content content =
        Content.builder()
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

    ExhibitionTranslationDto dto =
        objectMapper.readValue(
            response.text(),
            ExhibitionTranslationDto.class
        );

    applyTranslation(
        exhibition,
        dto,
        language
    );
  }

  private String buildExhibitionTranslationPrompt(
      Exhibition exhibition,
      TranslationLanguage language
  ) {

    String targetLanguage =
        language == TranslationLanguage.en
            ? "English"
            : "French";

    return """
      You are a professional translator.
      
      Translate the Armenian exhibition data into %s.
      
      RULES:
      
      1. Translate only text.
      2. Do not summarize.
      3. Do not rewrite.
      4. Do not add information.
      5. Do not invent values.
      6. Keep the exact order of all arrays.
      7. The array indexes must correspond exactly to the original data.
      8. URLs must NOT be translated or modified.
      9. If a value is null, return null.
      10. If the input contains the literal characters "\\n", preserve them as the literal characters "\\n". Do not convert them into an actual newline or don't delete it.
      11. Return ONLY JSON matching the provided schema.
      
      TRANSLATION REQUIREMENTS:
      
      - Translate the exhibition title.
      - Translate the exhibition description.
      - Translate the exhibition text/content.
      - Translate every link title.
      - Translate every video title.
      - Translate every image caption.
      
      IMPORTANT:
      
      The "links" array must contain exactly the same number of items
      as the input "links" array.
      
      The "videos" array must contain exactly the same number of items
      as the input "videos" array.
      
      The "images" array must contain exactly the same number of items
      as the input "images" array.
      
      Do not change the order of any array.
      
      Armenian exhibition data:
      
      %s
      """
        .formatted(
            targetLanguage,
            createTranslationObject(exhibition)
        );
  }

  private Map<String, Object> createTranslationObject(
      Exhibition exhibition
  ) {

    Map<String, Object> data = new HashMap<>();

    data.put("title", exhibition.getTitleHy());
    data.put("description", exhibition.getDescriptionHy());
    data.put("program", exhibition.getProgramHy());
    data.put("links", createExhibitionLinks(exhibition.getLinks()));
    data.put("videos", createExhibitionVideos(exhibition.getVideos()));
    data.put("images", createExhibitionImages(exhibition.getImages()));

    return data;
  }

  private List<Map<String, Object>> createExhibitionLinks(
      List<ExhibitionLink> links
  ) {

    if (links == null) {
      return null;
    }

    return links.stream()
        .map(link -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("linkTitle", link.getTitleHy());

          return map;

        })
        .toList();
  }

  private List<Map<String, Object>> createExhibitionVideos(
      List<ExhibitionVideo> videos
  ) {

    if (videos == null) {
      return null;
    }

    return videos.stream()
        .map(video -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("title", video.getTitleHy());

          return map;

        })
        .toList();
  }

  private List<Map<String, Object>> createExhibitionImages(
      List<ExhibitionImage> images
  ) {

    if (images == null) {
      return null;
    }

    return images.stream()
        .map(image -> {

          Map<String, Object> map =
              new HashMap<>();

          map.put("caption", image.getCaptionHy());

          return map;

        })
        .toList();
  }


  private Schema exhibitionTranslationSchema() {

    Map<String, Schema> properties =
        new HashMap<>();

    properties.put("title", stringSchema());
    properties.put("description", stringSchema());
    properties.put("program", stringSchema());

    properties.put("links",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(linkSchema())
            .build()
    );

    properties.put(
        "videos",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(exhibitionVideoSchema())
            .build()
    );

    properties.put(
        "images",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(imageSchema())
            .build()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema exhibitionVideoSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("title", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private void applyTranslation(
      Exhibition exhibition,
      ExhibitionTranslationDto dto,
      TranslationLanguage language
  ) {

    if (dto == null) {
      return;
    }

    boolean en =
        language == TranslationLanguage.en;

    if (en) {

      exhibition.setTitleEn(dto.getTitle());
      exhibition.setDescriptionEn(dto.getDescription());
      exhibition.setProgramEn(dto.getProgram());

    } else {

      exhibition.setTitleFr(dto.getTitle());
      exhibition.setDescriptionFr(dto.getDescription());
      exhibition.setProgramFr(dto.getProgram());
    }

    applyExhibitionLinksTranslation(
        exhibition.getLinks(),
        dto.getLinks(),
        language
    );

    applyExhibitionVideosTranslation(
        exhibition.getVideos(),
        dto.getVideos(),
        language
    );

    applyExhibitionImagesTranslation(
        exhibition.getImages(),
        dto.getImages(),
        language
    );
  }

  private void applyExhibitionLinksTranslation(
      List<ExhibitionLink> links,
      List<LinkTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (links == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            links.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {

      ExhibitionLink link =
          links.get(i);

      LinkTranslationDto translation =
          dto.get(i);

      if (language == TranslationLanguage.en) {

        link.setTitleEn(
            translation.getLinkTitle()
        );

      } else {

        link.setTitleFr(
            translation.getLinkTitle()
        );
      }
    }
  }

  private void applyExhibitionVideosTranslation(
      List<ExhibitionVideo> videos,
      List<ExhibitionVideoTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (videos == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            videos.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {

      ExhibitionVideo video =
          videos.get(i);

      ExhibitionVideoTranslationDto translation =
          dto.get(i);

      if (language == TranslationLanguage.en) {

        video.setTitleEn(
            translation.getTitle()
        );

      } else {

        video.setTitleFr(
            translation.getTitle()
        );
      }
    }
  }

  private void applyExhibitionImagesTranslation(
      List<ExhibitionImage> images,
      List<ExhibitionImageTranslationDto> dto,
      TranslationLanguage language
  ) {

    if (images == null || dto == null) {
      return;
    }

    int size =
        Math.min(
            images.size(),
            dto.size()
        );

    for (int i = 0; i < size; i++) {

      ExhibitionImage image =
          images.get(i);

      ExhibitionImageTranslationDto translation =
          dto.get(i);

      if (language == TranslationLanguage.en) {

        image.setCaptionEn(
            translation.getCaption()
        );

      } else {

        image.setCaptionFr(
            translation.getCaption()
        );
      }
    }
  }
}