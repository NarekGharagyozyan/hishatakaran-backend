ALTER TABLE monuments
    RENAME COLUMN signature TO signature_hy;

ALTER TABLE monuments
    ADD COLUMN signature_en VARCHAR(255),
    ADD COLUMN signature_fr VARCHAR(255);