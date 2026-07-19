package org.hishatakaran.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.hishatakaran.backend.entity.Monument;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class SearchSpecifications {

    public static <T> Specification<T> containsTextInFields(String text, List<String> fields) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) return cb.conjunction();
            
            String pattern = "%" + text.trim().toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();

            for (String field : fields) {
                predicates.add(cb.like(cb.lower(root.get(field)), pattern));
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Monument> searchMonument(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) return cb.conjunction();
            
            String pattern = "%" + text.trim().toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();

            List<String> textFields = List.of(
                "nameHy", "nameEn", "nameFr",
                "specialNameHy", "specialNameEn", "specialNameFr",
                "anotherNamesHy", "anotherNamesEn", "anotherNamesFr",
                "historyHy", "historyEn", "historyFr",
                "originalAffiliationHy", "originalAffiliationEn", "originalAffiliationFr",
                "storageUnitNameHy", "storageUnitNameEn", "storageUnitNameFr",
                "signature"
            );
            
            for (String field : textFields) {
                predicates.add(cb.like(cb.lower(root.get(field)), pattern));
            }

            predicates.add(cb.like(cb.lower(root.join("monumentType").get("nameHy")), pattern));
            predicates.add(cb.like(cb.lower(root.join("monumentType").get("nameEn")), pattern));
            predicates.add(cb.like(cb.lower(root.join("monumentType").get("nameFr")), pattern));

            predicates.add(cb.like(cb.lower(root.join("region").get("nameHy")), pattern));
            predicates.add(cb.like(cb.lower(root.join("region").get("nameEn")), pattern));
            predicates.add(cb.like(cb.lower(root.join("region").get("nameFr")), pattern));

            predicates.add(cb.like(cb.lower(root.join("settlement").get("nameHy")), pattern));
            predicates.add(cb.like(cb.lower(root.join("settlement").get("nameEn")), pattern));
            predicates.add(cb.like(cb.lower(root.join("settlement").get("nameFr")), pattern));

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}