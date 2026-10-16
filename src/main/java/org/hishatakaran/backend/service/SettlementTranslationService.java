package org.hishatakaran.backend.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementBibliography;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementMeasurement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.SettlementVideo;
import org.hishatakaran.backend.model.SettlementTopographicTranslationDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementTranslationService {

    private final GeminiService geminiService;

    /**
     * The translate button, the only place a settlement is translated: every Armenian field
     * into one language, replacing what is there, as {@code POST /monuments/{id}/translate/{language}} does.
     */
    public void translate(
            Settlement settlement,
            TranslationLanguage language
    ) {

        SettlementTranslationDto translation =
                geminiService.translateSettlement(
                    sourceOf(settlement),
                    language
                );

        applyTranslation(
            settlement,
            translation,
            language
        );
    }

    // ---------- what is sent to Gemini ----------

    /** The Armenian text; list entries keep their order so that the answer lines up with them. */
    private Map<String, Object> sourceOf(Settlement settlement) {

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", settlement.getNameHy());
        data.put("anotherNames", settlement.getAnotherNamesHy());
        data.put("history", settlement.getHistoryHy());
        data.put("description", settlement.getDescriptionHy());
        data.put("dataUpdate", settlement.getDataUpdateHy());

        data.put("topographics", topographicSource(settlement.getTopographics()));

        data.put("bibliography", entries(settlement.getBibliography(), "title", SettlementBibliography::getTitleHy));
        data.put("images", entries(settlement.getImages(), "caption", SettlementImage::getCaptionHy));
        data.put("measurements", entries(settlement.getMeasurements(), "caption", SettlementMeasurement::getCaptionHy));
        data.put("videos", entries(settlement.getVideos(), "title", SettlementVideo::getTitleHy));

        return data;
    }

    private Map<String, Object> topographicSource(SettlementTopographic topographic) {

        if (topographic == null) {
            return null;
        }

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("topography", topographic.getTopographyHy());
        data.put("address", topographic.getAddressHy());
        data.put("altitude", topographic.getAltitudeHy());
        data.put("hydrography", topographic.getHydrographyHy());
        data.put("dataUpdate", topographic.getDataUpdateHy());

        return data;
    }

    private <E> List<Map<String, Object>> entries(List<E> items, String key, Function<E, String> text) {

        return items
            .stream()
            .map(item -> {
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put(key, text.apply(item));
                return entry;
            })
            .toList();
    }

    // ---------- what comes back ----------

    private void applyTranslation(
        Settlement settlement,
        SettlementTranslationDto dto,
        TranslationLanguage language
    ) {

        if (dto == null) {
            return;
        }

        boolean en = language == TranslationLanguage.en;

        // settlements.name_en / name_fr are NOT NULL, so an empty answer keeps what is there.
        String name = clean(dto.getName());
        if (name != null) {
            if (en) {
                settlement.setNameEn(name);
            } else {
                settlement.setNameFr(name);
            }
        }

        if (en) {

            settlement.setAnotherNamesEn(clean(dto.getAnotherNames()));
            settlement.setHistoryEn(clean(dto.getHistory()));
            settlement.setDescriptionEn(clean(dto.getDescription()));
            settlement.setDataUpdateEn(clean(dto.getDataUpdate()));

        } else {

            settlement.setAnotherNamesFr(clean(dto.getAnotherNames()));
            settlement.setHistoryFr(clean(dto.getHistory()));
            settlement.setDescriptionFr(clean(dto.getDescription()));
            settlement.setDataUpdateFr(clean(dto.getDataUpdate()));
        }

        applyTopographicTranslation(settlement.getTopographics(), dto.getTopographics(), en);

        applyAligned(
            settlement.getImages(),
            dto.getImages(),
            (image, translation) -> {
                if (en) {
                    image.setCaptionEn(clean(translation.getCaption()));
                } else {
                    image.setCaptionFr(clean(translation.getCaption()));
                }
            }
        );

        applyAligned(
            settlement.getMeasurements(),
            dto.getMeasurements(),
            (measurement, translation) -> {
                if (en) {
                    measurement.setCaptionEn(clean(translation.getCaption()));
                } else {
                    measurement.setCaptionFr(clean(translation.getCaption()));
                }
            }
        );

        applyAligned(
            settlement.getVideos(),
            dto.getVideos(),
            (video, translation) -> {
                if (en) {
                    video.setTitleEn(clean(translation.getTitle()));
                } else {
                    video.setTitleFr(clean(translation.getTitle()));
                }
            }
        );

        applyAligned(
            settlement.getBibliography(),
            dto.getBibliography(),
            (item, translation) -> {
                if (en) {
                    item.setTitleEn(clean(translation.getTitle()));
                } else {
                    item.setTitleFr(clean(translation.getTitle()));
                }
            }
        );
    }

    private void applyTopographicTranslation(
        SettlementTopographic topographic,
        SettlementTopographicTranslationDto dto,
        boolean en
    ) {

        if (topographic == null || dto == null) {
            return;
        }

        if (en) {

            topographic.setTopographyEn(clean(dto.getTopography()));
            topographic.setAddressEn(clean(dto.getAddress()));
            topographic.setAltitudeEn(clean(dto.getAltitude()));
            topographic.setHydrographyEn(clean(dto.getHydrography()));
            topographic.setDataUpdateEn(clean(dto.getDataUpdate()));

        } else {

            topographic.setTopographyFr(clean(dto.getTopography()));
            topographic.setAddressFr(clean(dto.getAddress()));
            topographic.setAltitudeFr(clean(dto.getAltitude()));
            topographic.setHydrographyFr(clean(dto.getHydrography()));
            topographic.setDataUpdateFr(clean(dto.getDataUpdate()));
        }
    }

    /**
     * Gemini returns one entry per input element in input order. A short list is applied as
     * far as it goes, and elements past its end keep the translation they already have.
     */
    private <E, T> void applyAligned(
        List<E> entities,
        List<T> translations,
        BiConsumer<E, T> apply
    ) {

        if (entities == null || translations == null) {
            return;
        }

        int size = Math.min(entities.size(), translations.size());

        for (int i = 0; i < size; i++) {
            apply.accept(entities.get(i), translations.get(i));
        }
    }

    private static String clean(String value) {

        if (value == null) {
            return null;
        }

        if (value.isBlank()) {
            return null;
        }

        if (value.equalsIgnoreCase("null")) {
            return null;
        }

        return value;
    }
}
