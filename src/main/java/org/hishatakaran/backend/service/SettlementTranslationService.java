package org.hishatakaran.backend.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.model.SettlementTopographicTranslationDto;
import org.hishatakaran.backend.model.SettlementTranslationDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementTranslationService {

    private final GeminiService geminiService;

    /**
     * The translate button: every Armenian field into one language, replacing what is there,
     * as {@code POST /monuments/{id}/translate/{language}} does.
     */
    public void translate(
            Settlement settlement,
            TranslationLanguage language
    ) {

        SettlementTranslationDto translation =
                geminiService.translateSettlement(
                    sourceOf(settlement, language, false),
                    language
                );

        applyTranslation(
            settlement,
            translation,
            language,
            false
        );
    }

    /**
     * Run when an edit is saved: wherever there is Armenian text and its English or French is
     * empty, translate just that and fill it in. Whatever is already there is never touched, so
     * a hand-written translation survives, and when nothing is missing Gemini is not called.
     *
     * <p>A translation that fails is logged and the gaps stay: it must not cost the admin the
     * edit that is being saved. The translate button fills them later.
     */
    public void fillMissing(Settlement settlement) {

        for (TranslationLanguage language : TranslationLanguage.values()) {

            Map<String, Object> source = sourceOf(settlement, language, true);

            if (!hasText(source)) {
                continue;
            }

            try {
                applyTranslation(
                    settlement,
                    geminiService.translateSettlement(source, language),
                    language,
                    true
                );
            } catch (RuntimeException e) {
                log.warn("Settlement {} was saved without its {} translation of the new text",
                    settlement.getId(), language, e);
            }
        }
    }

    // ---------- what is sent to Gemini ----------

    /**
     * The Armenian text for one target language. With {@code onlyMissing} a field is included
     * only when it has Armenian text and nothing yet in that language; every other slot is null,
     * and list entries stay in place as null so positions still line up with the answer.
     */
    private Map<String, Object> sourceOf(
        Settlement settlement,
        TranslationLanguage language,
        boolean onlyMissing
    ) {

        boolean en = language == TranslationLanguage.en;

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", pick(settlement.getNameHy(), en ? settlement.getNameEn() : settlement.getNameFr(), onlyMissing));
        data.put("anotherNames", pick(settlement.getAnotherNamesHy(), en ? settlement.getAnotherNamesEn() : settlement.getAnotherNamesFr(), onlyMissing));
        data.put("history", pick(settlement.getHistoryHy(), en ? settlement.getHistoryEn() : settlement.getHistoryFr(), onlyMissing));
        data.put("description", pick(settlement.getDescriptionHy(), en ? settlement.getDescriptionEn() : settlement.getDescriptionFr(), onlyMissing));
        data.put("dataUpdate", pick(settlement.getDataUpdateHy(), en ? settlement.getDataUpdateEn() : settlement.getDataUpdateFr(), onlyMissing));

        data.put("topographics", topographicSource(settlement.getTopographics(), en, onlyMissing));

        data.put("bibliography", entries(settlement.getBibliography(), "title",
            item -> pick(item.getTitleHy(), en ? item.getTitleEn() : item.getTitleFr(), onlyMissing)));
        data.put("images", entries(settlement.getImages(), "caption",
            image -> pick(image.getCaptionHy(), en ? image.getCaptionEn() : image.getCaptionFr(), onlyMissing)));
        data.put("measurements", entries(settlement.getMeasurements(), "caption",
            measurement -> pick(measurement.getCaptionHy(), en ? measurement.getCaptionEn() : measurement.getCaptionFr(), onlyMissing)));
        data.put("videos", entries(settlement.getVideos(), "title",
            video -> pick(video.getTitleHy(), en ? video.getTitleEn() : video.getTitleFr(), onlyMissing)));

        return data;
    }

    private Map<String, Object> topographicSource(
        SettlementTopographic topographic,
        boolean en,
        boolean onlyMissing
    ) {

        if (topographic == null) {
            return null;
        }

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("topography", pick(topographic.getTopographyHy(), en ? topographic.getTopographyEn() : topographic.getTopographyFr(), onlyMissing));
        data.put("address", pick(topographic.getAddressHy(), en ? topographic.getAddressEn() : topographic.getAddressFr(), onlyMissing));
        data.put("altitude", pick(topographic.getAltitudeHy(), en ? topographic.getAltitudeEn() : topographic.getAltitudeFr(), onlyMissing));
        data.put("hydrography", pick(topographic.getHydrographyHy(), en ? topographic.getHydrographyEn() : topographic.getHydrographyFr(), onlyMissing));
        data.put("dataUpdate", pick(topographic.getDataUpdateHy(), en ? topographic.getDataUpdateEn() : topographic.getDataUpdateFr(), onlyMissing));

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

    /** Armenian text to send, or null when this slot is not wanted. */
    private static String pick(String hy, String current, boolean onlyMissing) {

        if (!onlyMissing) {
            return hy;
        }

        return clean(hy) != null && clean(current) == null ? hy : null;
    }

    /** True when somewhere in the data there is real text left to translate. */
    private static boolean hasText(Object value) {

        if (value instanceof String text) {
            return clean(text) != null;
        }

        if (value instanceof Map<?, ?> map) {
            return map.values().stream().anyMatch(SettlementTranslationService::hasText);
        }

        if (value instanceof Collection<?> collection) {
            return collection.stream().anyMatch(SettlementTranslationService::hasText);
        }

        return false;
    }

    // ---------- what comes back ----------

    private void applyTranslation(
        Settlement settlement,
        SettlementTranslationDto dto,
        TranslationLanguage language,
        boolean onlyMissing
    ) {

        if (dto == null) {
            return;
        }

        boolean en = language == TranslationLanguage.en;

        // settlements.name_en / name_fr are NOT NULL, so an empty answer keeps what is there.
        String name = merge(settlement.getNameHy(), en ? settlement.getNameEn() : settlement.getNameFr(), dto.getName(), onlyMissing);
        if (name != null) {
            if (en) {
                settlement.setNameEn(name);
            } else {
                settlement.setNameFr(name);
            }
        }

        if (en) {

            settlement.setAnotherNamesEn(merge(settlement.getAnotherNamesHy(), settlement.getAnotherNamesEn(), dto.getAnotherNames(), onlyMissing));
            settlement.setHistoryEn(merge(settlement.getHistoryHy(), settlement.getHistoryEn(), dto.getHistory(), onlyMissing));
            settlement.setDescriptionEn(merge(settlement.getDescriptionHy(), settlement.getDescriptionEn(), dto.getDescription(), onlyMissing));
            settlement.setDataUpdateEn(merge(settlement.getDataUpdateHy(), settlement.getDataUpdateEn(), dto.getDataUpdate(), onlyMissing));

        } else {

            settlement.setAnotherNamesFr(merge(settlement.getAnotherNamesHy(), settlement.getAnotherNamesFr(), dto.getAnotherNames(), onlyMissing));
            settlement.setHistoryFr(merge(settlement.getHistoryHy(), settlement.getHistoryFr(), dto.getHistory(), onlyMissing));
            settlement.setDescriptionFr(merge(settlement.getDescriptionHy(), settlement.getDescriptionFr(), dto.getDescription(), onlyMissing));
            settlement.setDataUpdateFr(merge(settlement.getDataUpdateHy(), settlement.getDataUpdateFr(), dto.getDataUpdate(), onlyMissing));
        }

        applyTopographicTranslation(settlement.getTopographics(), dto.getTopographics(), en, onlyMissing);

        applyAligned(
            settlement.getImages(),
            dto.getImages(),
            (image, translation) -> {
                if (en) {
                    image.setCaptionEn(merge(image.getCaptionHy(), image.getCaptionEn(), translation.getCaption(), onlyMissing));
                } else {
                    image.setCaptionFr(merge(image.getCaptionHy(), image.getCaptionFr(), translation.getCaption(), onlyMissing));
                }
            }
        );

        applyAligned(
            settlement.getMeasurements(),
            dto.getMeasurements(),
            (measurement, translation) -> {
                if (en) {
                    measurement.setCaptionEn(merge(measurement.getCaptionHy(), measurement.getCaptionEn(), translation.getCaption(), onlyMissing));
                } else {
                    measurement.setCaptionFr(merge(measurement.getCaptionHy(), measurement.getCaptionFr(), translation.getCaption(), onlyMissing));
                }
            }
        );

        applyAligned(
            settlement.getVideos(),
            dto.getVideos(),
            (video, translation) -> {
                if (en) {
                    video.setTitleEn(merge(video.getTitleHy(), video.getTitleEn(), translation.getTitle(), onlyMissing));
                } else {
                    video.setTitleFr(merge(video.getTitleHy(), video.getTitleFr(), translation.getTitle(), onlyMissing));
                }
            }
        );

        applyAligned(
            settlement.getBibliography(),
            dto.getBibliography(),
            (item, translation) -> {
                if (en) {
                    item.setTitleEn(merge(item.getTitleHy(), item.getTitleEn(), translation.getTitle(), onlyMissing));
                } else {
                    item.setTitleFr(merge(item.getTitleHy(), item.getTitleFr(), translation.getTitle(), onlyMissing));
                }
            }
        );
    }

    private void applyTopographicTranslation(
        SettlementTopographic topographic,
        SettlementTopographicTranslationDto dto,
        boolean en,
        boolean onlyMissing
    ) {

        if (topographic == null || dto == null) {
            return;
        }

        if (en) {

            topographic.setTopographyEn(merge(topographic.getTopographyHy(), topographic.getTopographyEn(), dto.getTopography(), onlyMissing));
            topographic.setAddressEn(merge(topographic.getAddressHy(), topographic.getAddressEn(), dto.getAddress(), onlyMissing));
            topographic.setAltitudeEn(merge(topographic.getAltitudeHy(), topographic.getAltitudeEn(), dto.getAltitude(), onlyMissing));
            topographic.setHydrographyEn(merge(topographic.getHydrographyHy(), topographic.getHydrographyEn(), dto.getHydrography(), onlyMissing));
            topographic.setDataUpdateEn(merge(topographic.getDataUpdateHy(), topographic.getDataUpdateEn(), dto.getDataUpdate(), onlyMissing));

        } else {

            topographic.setTopographyFr(merge(topographic.getTopographyHy(), topographic.getTopographyFr(), dto.getTopography(), onlyMissing));
            topographic.setAddressFr(merge(topographic.getAddressHy(), topographic.getAddressFr(), dto.getAddress(), onlyMissing));
            topographic.setAltitudeFr(merge(topographic.getAltitudeHy(), topographic.getAltitudeFr(), dto.getAltitude(), onlyMissing));
            topographic.setHydrographyFr(merge(topographic.getHydrographyHy(), topographic.getHydrographyFr(), dto.getHydrography(), onlyMissing));
            topographic.setDataUpdateFr(merge(topographic.getDataUpdateHy(), topographic.getDataUpdateFr(), dto.getDataUpdate(), onlyMissing));
        }
    }

    /**
     * The value a column ends up with. The translate button replaces it with the answer. When
     * filling gaps the answer is used only if there is Armenian text and nothing in this language
     * yet, so a translation that is already there is never replaced and text with no Armenian
     * source is never made up.
     */
    private static String merge(String hy, String current, String translated, boolean onlyMissing) {

        if (!onlyMissing) {
            return clean(translated);
        }

        if (clean(hy) == null || clean(current) != null) {
            return current;
        }

        String answer = clean(translated);

        return answer != null ? answer : current;
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
