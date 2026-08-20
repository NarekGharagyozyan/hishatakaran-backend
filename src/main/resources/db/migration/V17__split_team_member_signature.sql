ALTER TABLE team_members
    RENAME COLUMN signature TO signature_hy;

ALTER TABLE team_members
    ADD COLUMN signature_en VARCHAR(255),
    ADD COLUMN signature_fr VARCHAR(255);