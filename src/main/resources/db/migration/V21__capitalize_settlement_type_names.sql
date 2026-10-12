-- V20 seeded the Armenian names in lower case; the other reference tables capitalize them.
UPDATE settlement_types
SET name_hy = CASE id
                  WHEN 1 THEN 'Քաղաք'
                  WHEN 2 THEN 'Ավան'
                  WHEN 3 THEN 'Գյուղ'
                  WHEN 4 THEN 'Գյուղատեղի'
                  WHEN 5 THEN 'Բնակատեղի'
    END
WHERE id IN (1, 2, 3, 4, 5);
