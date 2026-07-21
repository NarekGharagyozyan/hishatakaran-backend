package org.hishatakaran.backend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.hishatakaran.backend.mapper.LibraryMapper;
import org.hishatakaran.backend.mapper.MonumentMapper;
import org.hishatakaran.backend.mapper.ProgramMapper;
import org.hishatakaran.backend.mapper.TeamMemberMapper;
import org.hishatakaran.backend.model.LibraryResponseDto;
import org.hishatakaran.backend.model.MonumentResponseDto;
import org.hishatakaran.backend.model.ProgramResponseDto;
import org.hishatakaran.backend.model.SearchResponseDto;
import org.hishatakaran.backend.model.TeamMemberResponseDto;
import org.hishatakaran.backend.repository.LibraryRepository;
import org.hishatakaran.backend.repository.MonumentRepository;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.hishatakaran.backend.repository.TeamMembersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MonumentRepository monumentRepository;
    private final ProgramRepository programRepository;
    private final TeamMembersRepository teamMembersRepository;
    private final LibraryRepository libraryRepository;

    private final TransactionTemplate transactionTemplate;

    public SearchResponseDto globalSearch(String queryText) {
        if (queryText == null || queryText.isBlank()) {
            return new SearchResponseDto(List.of(), List.of(), List.of(), List.of());
        }

        // Запускаем 4 тяжелых запроса параллельно
        CompletableFuture<List<MonumentResponseDto>> monumentsFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status ->
                monumentRepository.findAll(SearchSpecifications.searchMonument(queryText))
                    .stream().map(MonumentMapper::toDto).toList()
            )
        );

        CompletableFuture<List<ProgramResponseDto>> programsFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("titleHy", "titleEn", "titleFr", "descriptionHy", "descriptionEn",
                    "descriptionFr");
                return programRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream().map(ProgramMapper::toResponseDto).toList();
            })
        );

        CompletableFuture<List<TeamMemberResponseDto>> teamFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("fullNameHy", "fullNameEn", "fullNameFr", "positionHy", "positionEn",
                    "positionFr", "descriptionHy", "descriptionEn", "descriptionFr", "signature");
                return teamMembersRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream().map(TeamMemberMapper::toDto).toList();
            })
        );

        CompletableFuture<List<LibraryResponseDto>> libraryFuture = CompletableFuture.supplyAsync(() ->
            transactionTemplate.execute(status -> {
                List<String> fields = List.of("titleHy", "titleEn", "titleFr", "descriptionHy", "descriptionEn", "descriptionFr", "authorsHy", "authorsEn", "authorsFr");
                return libraryRepository.findAll(SearchSpecifications.containsTextInFields(queryText, fields))
                    .stream().map(LibraryMapper::toDto).toList();
            })
        );

        // Ожидаем завершения всех потоков
        CompletableFuture.allOf(monumentsFuture, programsFuture, teamFuture, libraryFuture).join();

        // Собираем ответ
        SearchResponseDto response = new SearchResponseDto();
        response.setMonuments(monumentsFuture.join());
        response.setPrograms(programsFuture.join());
        response.setTeamMembers(teamFuture.join());
        response.setLibraries(libraryFuture.join());

        return response;
    }
}