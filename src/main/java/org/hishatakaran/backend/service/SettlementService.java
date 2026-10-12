package org.hishatakaran.backend.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.hishatakaran.backend.entity.Settlement;
import org.hishatakaran.backend.entity.SettlementBibliography;
import org.hishatakaran.backend.entity.SettlementImage;
import org.hishatakaran.backend.entity.SettlementMeasurement;
import org.hishatakaran.backend.entity.SettlementTopographic;
import org.hishatakaran.backend.entity.SettlementType;
import org.hishatakaran.backend.entity.SettlementVideo;
import org.hishatakaran.backend.exception.SomethingWentWrongException;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.mapper.SettlementTypeMapper;
import org.hishatakaran.backend.model.BibliographyResponseDto;
import org.hishatakaran.backend.model.ImageResponseDto;
import org.hishatakaran.backend.model.LanguagesResponseDto;
import org.hishatakaran.backend.model.MeasurementResponseDto;
import org.hishatakaran.backend.model.MonumentFilterRequest;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.MonumentVideoResponseDto;
import org.hishatakaran.backend.model.SettlementDetailsResponseDto;
import org.hishatakaran.backend.model.SettlementEditDto;
import org.hishatakaran.backend.model.SettlementRequestDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.model.SettlementTopographicRequestDto;
import org.hishatakaran.backend.model.SettlementTopographicResponseDto;
import org.hishatakaran.backend.model.SettlementTypeResponseDto;
import org.hishatakaran.backend.model.TranslationLanguage;
import org.hishatakaran.backend.repository.RegionRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.hishatakaran.backend.repository.SettlementTypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

  private final RegionRepository regionRepository;
  private final SettlementRepository settlementRepository;
  private final SettlementTypeRepository settlementTypeRepository;
  private final SettlementTranslationService settlementTranslationService;
  private final FileStorageService fileStorageService;
  private final MonumentService monumentService;

  /**
   * Stores the Armenian text as typed, like monuments do. English and French come from
   * {@link #translate}. The name columns are NOT NULL, hence the empty strings.
   */
  @Transactional
  public SettlementDetailsResponseDto createNewSettlement(Long regionId, SettlementRequestDto dto) {

    Settlement settlement = new Settlement();
    settlement.setRegion(regionRepository.findById(regionId)
        .orElseThrow(() -> new RuntimeException("Region not found")));

    settlement.setNameHy(dto.getName());
    settlement.setNameEn("");
    settlement.setNameFr("");
    settlement.setAnotherNamesHy(dto.getAnotherNames());
    settlement.setHistoryHy(dto.getHistory());
    settlement.setDescriptionHy(dto.getDescription());
    settlement.setDataUpdateHy(dto.getDataUpdate());

    if (dto.getSettlementTypeId() != null) {
      settlement.setSettlementType(findSettlementType(dto.getSettlementTypeId()));
    }

    SettlementTopographicRequestDto topographics = dto.getTopographics();
    if (topographics != null) {
      SettlementTopographic topographic = new SettlementTopographic();
      topographic.setSettlement(settlement);
      topographic.setTopographyHy(topographics.getTopography());
      topographic.setAddressHy(topographics.getAddress());
      topographic.setLatitude(topographics.getLatitude());
      topographic.setLongitude(topographics.getLongitude());
      topographic.setAltitudeHy(topographics.getAltitude());
      topographic.setHydrographyHy(topographics.getHydrography());
      topographic.setDataUpdateHy(topographics.getDataUpdate());
      settlement.setTopographics(topographic);
    }

    if (dto.getBibliography() != null) {
      dto.getBibliography().forEach(item -> settlement.getBibliography().add(
          new SettlementBibliography(item.getTitle(), null, null, item.getUrl(), settlement)
      ));
    }

    if (dto.getImages() != null) {
      dto.getImages().forEach(image -> settlement.getImages().add(
          new SettlementImage(image.getUrl(), image.getCaption(), null, null, settlement)
      ));
    }

    if (dto.getMeasurements() != null) {
      dto.getMeasurements().forEach(measurement -> settlement.getMeasurements().add(
          new SettlementMeasurement(measurement.getUrl(), measurement.getCaption(), null, null, settlement)
      ));
    }

    if (dto.getVideos() != null) {
      dto.getVideos().forEach(video -> settlement.getVideos().add(
          new SettlementVideo(video.getTitle(), null, null, video.getUrl(), settlement)
      ));
    }

    return SettlementMapper.toDetailsDto(settlementRepository.save(settlement));
  }

  /**
   * Saves what is sent, in all three languages. As in {@code updateMonument}, an omitted
   * text group or list leaves the stored value alone; a list that is sent replaces the
   * stored one.
   *
   * <p>Text sent with Armenian only (a field the admin just added) gets its empty English and
   * French filled in by translation; text that already has a translation is left as sent.
   * {@link #translate} is the button that redoes a whole language.
   */
  @Transactional
  public SettlementDetailsResponseDto editSettlement(Long settlementId, SettlementEditDto dto) {

    Settlement settlement = settlementRepository.findById(settlementId)
        .orElseThrow(() -> new RuntimeException("Settlement not found"));

    applyGroup(
        dto.getName(),
        settlement::setNameHy,
        value -> settlement.setNameEn(nullToEmpty(value)),
        value -> settlement.setNameFr(nullToEmpty(value))
    );
    applyGroup(dto.getAnotherNames(),
        settlement::setAnotherNamesHy, settlement::setAnotherNamesEn, settlement::setAnotherNamesFr);
    applyGroup(dto.getHistory(),
        settlement::setHistoryHy, settlement::setHistoryEn, settlement::setHistoryFr);
    applyGroup(dto.getDescription(),
        settlement::setDescriptionHy, settlement::setDescriptionEn, settlement::setDescriptionFr);
    applyGroup(dto.getDataUpdate(),
        settlement::setDataUpdateHy, settlement::setDataUpdateEn, settlement::setDataUpdateFr);

    if (dto.getRegionId() != null) {
      settlement.setRegion(regionRepository.findById(dto.getRegionId())
          .orElseThrow(() -> new RuntimeException("Region not found")));
    }

    if (dto.getSettlementTypeId() != null) {
      settlement.setSettlementType(findSettlementType(dto.getSettlementTypeId()));
    }

    applyTopographicEdit(settlement, dto.getTopographics());

    if (dto.getBibliography() != null) {
      settlement.getBibliography().clear();
      for (BibliographyResponseDto item : dto.getBibliography()) {
        LanguagesResponseDto title = orEmpty(item.getTitle());
        settlement.getBibliography().add(new SettlementBibliography(
            title.getHy(), title.getEn(), title.getFr(), item.getUrl(), settlement
        ));
      }
    }

    if (dto.getImages() != null) {
      settlement.getImages().clear();
      for (ImageResponseDto image : dto.getImages()) {
        LanguagesResponseDto caption = orEmpty(image.getCaption());
        settlement.getImages().add(new SettlementImage(
            image.getUrl(), caption.getHy(), caption.getEn(), caption.getFr(), settlement
        ));
      }
    }

    if (dto.getMeasurements() != null) {
      settlement.getMeasurements().clear();
      for (MeasurementResponseDto measurement : dto.getMeasurements()) {
        LanguagesResponseDto caption = orEmpty(measurement.getCaption());
        settlement.getMeasurements().add(new SettlementMeasurement(
            measurement.getUrl(), caption.getHy(), caption.getEn(), caption.getFr(), settlement
        ));
      }
    }

    if (dto.getVideos() != null) {
      settlement.getVideos().clear();
      for (MonumentVideoResponseDto video : dto.getVideos()) {
        LanguagesResponseDto title = orEmpty(video.getTitle());
        settlement.getVideos().add(new SettlementVideo(
            title.getHy(), title.getEn(), title.getFr(), video.getUrl(), settlement
        ));
      }
    }

    settlementTranslationService.fillMissing(settlement);

    return SettlementMapper.toDetailsDto(settlementRepository.save(settlement));
  }

  private void applyTopographicEdit(Settlement settlement, SettlementTopographicResponseDto dto) {

    if (dto == null) {
      return;
    }

    SettlementTopographic topographic = settlement.getTopographics();
    if (topographic == null) {
      topographic = new SettlementTopographic();
      topographic.setSettlement(settlement);
      settlement.setTopographics(topographic);
    }

    applyGroup(dto.getTopography(),
        topographic::setTopographyHy, topographic::setTopographyEn, topographic::setTopographyFr);
    applyGroup(dto.getAddress(),
        topographic::setAddressHy, topographic::setAddressEn, topographic::setAddressFr);
    applyGroup(dto.getAltitude(),
        topographic::setAltitudeHy, topographic::setAltitudeEn, topographic::setAltitudeFr);
    applyGroup(dto.getHydrography(),
        topographic::setHydrographyHy, topographic::setHydrographyEn, topographic::setHydrographyFr);
    applyGroup(dto.getDataUpdate(),
        topographic::setDataUpdateHy, topographic::setDataUpdateEn, topographic::setDataUpdateFr);

    // Omitted coordinates stay as they are; send an empty string to clear one.
    if (dto.getLatitude() != null) {
      topographic.setLatitude(dto.getLatitude());
    }
    if (dto.getLongitude() != null) {
      topographic.setLongitude(dto.getLongitude());
    }
  }

  /** Translates every Armenian field into one language, like {@code POST /monuments/{id}/translate/{language}}. */
  @Transactional
  public SettlementDetailsResponseDto translate(Long id, TranslationLanguage language) {

    Settlement settlement = settlementRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Settlement not found"));

    settlementTranslationService.translate(settlement, language);

    return SettlementMapper.toDetailsDto(settlementRepository.save(settlement));
  }

  @Transactional
  public void deleteSettlement(Long id) {
    Settlement settlement = settlementRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Settlement not found"));

    MonumentFilterRequest monumentFilterRequest = new MonumentFilterRequest();
    monumentFilterRequest.setSettlementId(id);
    List<MonumentResponseDto> monumentResponseDtos = monumentService.filter(monumentFilterRequest);
    if (!monumentResponseDtos.isEmpty()) {
      throw new SomethingWentWrongException("Կան հուշարձաններ որոնք օգտագործում են տվյալ բնակավայրը։ Այդ հուշարձաններն են՝ "
          + monumentResponseDtos.stream()
          .map(monumentResponseDto -> monumentResponseDto.getName().getHy())
          .collect(Collectors.joining(", "))
      );
    }
    deleteFiles(settlement.getImages().stream().map(SettlementImage::getUrl).toList());
    deleteFiles(settlement.getMeasurements().stream().map(SettlementMeasurement::getUrl).toList());

    settlementRepository.delete(settlement);
  }

  public SettlementDetailsResponseDto getSettlement(Long id) {
    Settlement settlement = settlementRepository.findById(id).orElseThrow(() -> new RuntimeException("Settlement not found"));
    return SettlementMapper.toDetailsDto(settlement);
  }

  // The two lists below are unpaginated, so they carry the compact form: the full dossier
  // would lazy-load six collections for every settlement in the table.
  public List<SettlementResponseDto> getByRegion(Long regionId) {
    return settlementRepository.findAllByRegionId(regionId)
        .stream()
        .map(SettlementMapper::toDto)
        .toList();
  }

  public List<SettlementResponseDto> getAll() {
    return settlementRepository.findAll()
        .stream()
        .map(SettlementMapper::toDto)
        .sorted(Comparator.comparing(SettlementResponseDto::getId).reversed())
        .toList();
  }

  /** What the admin form offers under «Պահպանման միավորի անվանումը». */
  public List<SettlementTypeResponseDto> getAllSettlementTypes() {
    return settlementTypeRepository.findAll(Sort.by("id"))
        .stream()
        .map(SettlementTypeMapper::toDto)
        .toList();
  }

  private SettlementType findSettlementType(Long settlementTypeId) {
    return settlementTypeRepository.findById(settlementTypeId)
        .orElseThrow(() -> new RuntimeException("Settlement type not found"));
  }

  /** An omitted group leaves all three stored values alone. */
  private static void applyGroup(
      LanguagesResponseDto group,
      Consumer<String> hy,
      Consumer<String> en,
      Consumer<String> fr
  ) {
    if (group == null) {
      return;
    }
    hy.accept(group.getHy());
    en.accept(group.getEn());
    fr.accept(group.getFr());
  }

  /** The frontend sends back the group the mapper gave it, and the mapper emits null for an all-empty group. */
  private static LanguagesResponseDto orEmpty(LanguagesResponseDto group) {
    return group == null ? new LanguagesResponseDto() : group;
  }

  private static String nullToEmpty(String value) {
    return value == null ? "" : value;
  }

  private void deleteFiles(List<String> paths) {
    if (paths == null) {
      return;
    }

    paths.forEach(fileStorageService::deleteImage);
  }

}
