package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DescriptiveCharacteristicReferenceRepository extends JpaRepository<DescriptiveCharacteristicReference, UUID> {
    List<DescriptiveCharacteristicReference> findByMonumentId(UUID monumentId);

}