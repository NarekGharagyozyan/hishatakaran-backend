package org.hishatakaran.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.hishatakaran.backend.model.DescriptiveCharacteristicResponseDto;
import org.hishatakaran.backend.repository.DescriptiveCharacteristicReferenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DescriptiveCharacteristicReferenceServiceTest {

    @Mock
    private DescriptiveCharacteristicReferenceRepository descriptiveRepository;

    @InjectMocks
    private DescriptiveCharacteristicReferenceService descriptiveService;

    private static DescriptiveCharacteristicReference descriptive(Long id, String typeHy) {
        DescriptiveCharacteristicReference descriptive = new DescriptiveCharacteristicReference();
        descriptive.setId(id);
        descriptive.setTypeHy(typeHy);
        descriptive.setTypeEn(typeHy + "-en");
        descriptive.setTypeFr(typeHy + "-fr");
        descriptive.setRoofHy("տանիք");
        return descriptive;
    }

    @Test
    void getAll_mapsEveryReferenceKeepingRepositoryOrder() {
        when(descriptiveRepository.findAll())
            .thenReturn(List.of(descriptive(2L, "եկեղեցի"), descriptive(1L, "խաչքար")));

        List<DescriptiveCharacteristicResponseDto> result = descriptiveService.getAll();

        assertThat(result).extracting(DescriptiveCharacteristicResponseDto::getId).containsExactly(2L, 1L);
        assertThat(result.get(0).getType().getHy()).isEqualTo("եկեղեցի");
        assertThat(result.get(0).getRoof().getHy()).isEqualTo("տանիք");
    }

    @Test
    void getAll_returnsEmptyListWhenNothingStored() {
        when(descriptiveRepository.findAll()).thenReturn(List.of());

        assertThat(descriptiveService.getAll()).isEmpty();
    }

    @Test
    void getById_returnsMappedReference() {
        when(descriptiveRepository.findById(8L)).thenReturn(Optional.of(descriptive(8L, "վանք")));

        DescriptiveCharacteristicResponseDto result = descriptiveService.getById(8L);

        assertThat(result.getId()).isEqualTo(8L);
        assertThat(result.getType().getHy()).isEqualTo("վանք");
    }

    @Test
    void getById_throwsWhenReferenceMissing() {
        when(descriptiveRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> descriptiveService.getById(404L))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getByMonument_mapsOnlyTheMonumentsReferences() {
        when(descriptiveRepository.findByMonumentId(42L))
            .thenReturn(List.of(descriptive(9L, "մատուռ")));

        List<DescriptiveCharacteristicResponseDto> result = descriptiveService.getByMonument(42L);

        assertThat(result).extracting(DescriptiveCharacteristicResponseDto::getId).containsExactly(9L);
    }

    @Test
    void getByMonument_returnsEmptyListWhenMonumentHasNone() {
        when(descriptiveRepository.findByMonumentId(42L)).thenReturn(List.of());

        assertThat(descriptiveService.getByMonument(42L)).isEmpty();
    }
}
