package org.hishatakaran.backend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.hishatakaran.backend.mapper.ExhibitionMapper;
import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.mapper.ProgramCulturalHeritageDocumentationMapper;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.mapper.SettlementMapper;
import org.hishatakaran.backend.mapper.TeamMemberMapper;
import org.hishatakaran.backend.model.ExhibitionResponseDto;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.ProgramCulturalHeritageDocumentationResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.SearchResponseDto;
import org.hishatakaran.backend.model.SettlementResponseDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.repository.ExhibitionRepository;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.ProgramCulturalHeritageDocumentationRepository;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.hishatakaran.backend.repository.SettlementRepository;
import org.hishatakaran.backend.repository.TeamMembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MonumentRepository monumentRepository;
    private final ProgramRepository programRepository;
    private final ProgramCulturalHeritageDocumentationRepository programCulturalHeritageDocumentationRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final SettlementRepository settlementRepository;
    private final TeamMembersRepository teamMembersRepository;
    private final LibraryRepository libraryRepository;

    private final TransactionTemplate transactionTemplate;

    public SearchResponseDto globalSearch(String queryText) {
        if (queryText == null || queryText.isBlank()) {
            return new SearchResponseDto(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of());
        }

        CompletableFuture<List<MonumentResponseDto>> monumentsFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status ->
                monumentRepository.findAll(SearchSpecifications.searchMonument(queryText))
                    .stream().map(MonumentMapper::toDto).toList()
            )
        );

      CompletableFuture<List<ProgramCulturalHeritageDocumentationResponseDto>> programsCulturalHeritageFuture = CompletableFuture.supplyAsync(() ->
          transactionTemplate.execute(status -> {
            List<String> fields = List.of("titleHy", "titleEn", "titleFr", "subtitleHy", "subtitleEn",
                "subtitleFr");
            return programCulturalHeritageDocumentationRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                .stream()
                .map(ProgramCulturalHeritageDocumentationMapper::toResponseDto)
                .toList();
          })
      );

        CompletableFuture<List<ProgramResponseDto>> programsFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("titleHy", "titleEn", "titleFr", "descriptionHy", "descriptionEn",
                    "descriptionFr", "programHy", "programEn", "programFr");
                return programRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream()
                    .map(program -> {
                        ProgramResponseDto dto = ProgramMapper.toDto(program);
                        program.getImages().size();
                        return dto;
                    })
                    .toList();
            })
        );

      CompletableFuture<List<ExhibitionResponseDto>> exhibitionFuture = CompletableFuture.supplyAsync(() ->
          transactionTemplate.execute(status -> {
            List<String> fields = List.of("titleHy", "titleEn", "titleFr", "descriptionHy", "descriptionEn",
                "descriptionFr", "programHy", "programEn", "programFr");
            return exhibitionRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                .stream()
                .map(program -> {
                  ExhibitionResponseDto dto = ExhibitionMapper.toDto(program);
                  program.getImages().size();
                  return dto;
                })
                .toList();
          })
      );

        CompletableFuture<List<TeamMemberResponseDto>> teamFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("fullNameHy", "fullNameEn", "fullNameFr", "positionHy", "positionEn",
                    "positionFr", "descriptionHy", "descriptionEn", "descriptionFr", "signature");
                return teamMembersRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream()
                    .map(teamMember -> {
                        TeamMemberResponseDto dto = TeamMemberMapper.toDto(teamMember);
                        teamMember.getImage();
                        return dto;
                    })
                    .toList();
            })
        );

        CompletableFuture<List<LibraryResponseDto>> libraryFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("titleHy", "titleEn", "titleFr", "descriptionHy", "descriptionEn", "descriptionFr", "authorsHy", "authorsEn", "authorsFr");
                return libraryRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream()
                    .map(library -> {
                        LibraryResponseDto dto = LibraryMapper.toDto(library);
                        library.getCoverUrl();
                        return dto;
                    })
                    .toList();
            })
        );

      CompletableFuture<List<SettlementResponseDto>> settlementFuture = CompletableFuture.supplyAsync(() ->
          transactionTemplate.execute(status -> {
            List<String> fields = List.of("nameHy", "nameEn", "nameFr", "descriptionHy", "descriptionEn", "descriptionFr");
            return settlementRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                .stream()
                .map(settlement -> {
                  SettlementResponseDto dto = SettlementMapper.toDto(settlement);
                  settlement.getImages();
                  return dto;
                })
                .toList();
          })
      );

        CompletableFuture.allOf(monumentsFuture, programsFuture, teamFuture, libraryFuture).join();

        SearchResponseDto response = new SearchResponseDto();
        response.setMonuments(monumentsFuture.join());
        response.setPrograms(programsFuture.join());
        response.setProgramCulturalHeritages(programsCulturalHeritageFuture.join());
        response.setExhibitions(exhibitionFuture.join());
        response.setTeamMembers(teamFuture.join());
        response.setLibraries(libraryFuture.join());
        response.setSettlements(settlementFuture.join());

        return response;
    }
}