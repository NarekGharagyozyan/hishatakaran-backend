package org.hishatakaran.backend.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.entity.HistoricalReference;
import org.hishatakaran.backend.entity.Library;
import org.hishatakaran.backend.entity.Monument;
import org.hishatakaran.backend.entity.Program;
import org.hishatakaran.backend.entity.ProgramLink;
import org.hishatakaran.backend.entity.TeamMembers;
import org.hishatakaran.backend.entity.Topographic;
import org.hishatakaran.backend.model.LibraryRequestDto;
import org.hishatakaran.backend.model.LibraryTranslationDto;
import org.hishatakaran.backend.model.LinkTranslationDto;
import org.hishatakaran.backend.model.MonumentRequestDto;
import org.hishatakaran.backend.model.MonumentTranslationDto;
import org.hishatakaran.backend.model.MonumentTypeTranslateDto;
import org.hishatakaran.backend.model.ProgramRequestDto;
import org.hishatakaran.backend.model.ProgramTranslationDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.model.TeamMemberRequestDto;
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

    String targetLanguage = language == TranslationLanguage.en
        ? "English"
        : "French";

    return """
        You are a professional translator.

        Translate the Armenian program data into %s.

        RULES:
        1. Translate only text.
        2. Do not summarize.
        3. Do not rewrite.
        4. Keep the order of arrays.
        5. Return ONLY JSON matching the schema.
        6. Do not invent values.
        7. If a value is null, return null.

        Armenian program data:

        %s
        """
        .formatted(
            targetLanguage,
            createTranslationObject(program)
        );
  }

  private List<Map<String,Object>> createLinks(List<ProgramLink> links) {

    if (links == null)
      return null;

    return links.stream()
        .map(link -> {

          Map<String,Object> map = new HashMap<>();

          map.put("linkTitle", link.getTitleHy());

          return map;

        })
        .toList();
  }

  private Map<String,Object> createTranslationObject(Program program) {

    Map<String,Object> data = new HashMap<>();

    data.put("title", program.getTitleHy());
    data.put("description", program.getDescriptionHy());
    data.put("links", createLinks(program.getLinks()));

    return data;
  }


  private Schema programTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("title", stringSchema());
    properties.put("description", stringSchema());

    properties.put(
        "links",
        Schema.builder()
            .type(Type.Known.ARRAY)
            .items(linkSchema())
            .build()
    );

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  private Schema linkSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("linkTitle", stringSchema());

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

    boolean en = language == TranslationLanguage.en;

    if (en) {

      program.setTitleEn(dto.getTitle());
      program.setDescriptionEn(dto.getDescription());

    } else {

      program.setTitleFr(dto.getTitle());
      program.setDescriptionFr(dto.getDescription());

    }

    applyLinksTranslation(
        program.getLinks(),
        dto.getLinks(),
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

    properties.put(
        "culturalAffiliation",
        stringSchema()
    );

    properties.put(
        "justificationOfTheNumberingBasedOnReliableDocument",
        stringSchema()
    );

    properties.put(
        "justificationOfTheNumberingBasedOnBibliographicalSources",
        stringSchema()
    );

    properties.put(
        "justificationOfTheNumberingAccordingIconography",
        stringSchema()
    );

    properties.put(
        "justificationOfTheNumberingBasedOnEvidence",
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

    properties.put(
        "sourceForDeterminingTheAuthor",
        stringSchema()
    );

    properties.put(
        "briefHistoricalOverview",
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
        "archeologicalOverviewStratigraphyFindings",
        stringSchema()
    );

    properties.put(
        "architecturalOverview",
        stringSchema()
    );

    properties.put(
        "decorativeAndMonumentalFeaturesCompositionColours",
        stringSchema()
    );

    properties.put(
        "theBuildingMaterial",
        stringSchema()
    );

    properties.put(
        "openingsEntrances",
        stringSchema()
    );

    properties.put(
        "openingsWindows",
        stringSchema()
    );

    properties.put(
        "constructions",
        stringSchema()
    );

    properties.put(
        "levelsOfConstruction",
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
        "exterior",
        stringSchema()
    );

    properties.put(
        "implementationTechnique",
        stringSchema()
    );

    properties.put(
        "length",
        stringSchema()
    );

    properties.put(
        "width",
        stringSchema()
    );

    properties.put(
        "height",
        stringSchema()
    );

    properties.put(
        "depthThickness",
        stringSchema()
    );

    properties.put(
        "area",
        stringSchema()
    );

    properties.put(
        "lengthOfSpan",
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

    return data;
  }

  private Schema teamMemberTranslationSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("fullName", stringSchema());
    properties.put("position", stringSchema());
    properties.put("description", stringSchema());

    return Schema.builder()
        .type(Type.Known.OBJECT)
        .properties(properties)
        .build();
  }

  public SettlementTranslationDto translateSettlement(
      String armenianName
  ) throws JsonProcessingException {

    GenerateContentConfig config =
        GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(settlementSchema())
            .build();

    Content content =
        Content.builder()
            .parts(List.of(
                Part.builder()
                    .text(buildSettlementPrompt(armenianName))
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
      String armenianName
  ) {

    return """
      You are an expert in Armenian geographical names.
      
      Translate the Armenian settlement name into English and French.
      
      IMPORTANT:
      
      - This is a proper geographical name.
      - Do NOT translate its meaning.
      - Transliterate it.
      
      English:
      - Use the official ALA-LC Romanization for Armenian.
      
      French:
      - Use the same ALA-LC transliteration, adapted only where required by conventional French orthography.
      
      Do not invent alternative spellings.
      
      Return ONLY JSON.
      
      Input:
      
      %s
      """
        .formatted(armenianName);
  }

  private Schema settlementSchema() {

    Map<String, Schema> properties = new HashMap<>();

    properties.put("nameHy", stringSchema());
    properties.put("nameEn", stringSchema());
    properties.put("nameFr", stringSchema());

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
}