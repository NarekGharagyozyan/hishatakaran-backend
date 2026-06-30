package org.hishatakaran.backend.repository;

import org.hishatakaran.backend.entity.DescriptiveCharacteristicReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescriptiveCharacteristicReferenceRepository extends JpaRepository<DescriptiveCharacteristicReference, Long> {
    List<DescriptiveCharacteristicReference> findByMonumentId(Long monumentId);

}