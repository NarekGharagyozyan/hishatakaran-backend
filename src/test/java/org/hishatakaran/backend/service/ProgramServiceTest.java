package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hishatakaran.backend.entity.ProgramType;
import org.hishatakaran.backend.model.ProgramTypeResponseDto;
import org.hishatakaran.backend.repository.ProgramRepository;
import org.hishatakaran.backend.repository.ProgramTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {

    @Mock
    private ProgramRepository programRepository;
    @Mock
    private ProgramTypeRepository programTypeRepository;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private ProgramTranslationService programTranslationService;

    @InjectMocks
    private ProgramService programService;

    private static ProgramType programType(Long id, String nameHy) {
        ProgramType programType = new ProgramType();
        programType.setId(id);
        programType.setNameHy(nameHy);
        programType.setNameEn(nameHy + "-en");
        programType.setNameFr(nameHy + "-fr");
        return programType;
    }

    @Test
    void getAllProgramTypes_mapsEveryTypeKeepingRepositoryOrder() {
        when(programTypeRepository.findAll())
            .thenReturn(List.of(programType(1L, "Ֆիլմ"), programType(2L, "Հաղորդում")));

        List<ProgramTypeResponseDto> result = programService.getAllProgramTypes();

        assertThat(result).extracting(ProgramTypeResponseDto::getId).containsExactly(1L, 2L);
        assertThat(result.get(0).getName().getHy()).isEqualTo("Ֆիլմ");
        assertThat(result.get(1).getName().getEn()).isEqualTo("Հաղորդում-en");
    }

    @Test
    void getAllProgramTypes_returnsEmptyListWhenNoTypes() {
        when(programTypeRepository.findAll()).thenReturn(List.of());

        assertThat(programService.getAllProgramTypes()).isEmpty();
    }
}
