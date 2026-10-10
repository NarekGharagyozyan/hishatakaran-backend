-- Settlements get the same shape monuments already have: a type, a topographic
-- reference (coordinates included), bibliography, measurements and videos.
--
-- Everything here is additive. The existing columns on settlements and the table
-- settlement_images are left untouched, so the settlements already in the database
-- keep all their data.

-- Կանխադրված տեսակներ. The admin form offers these under «Պահպանման միավորի անվանումը».
CREATE TABLE IF NOT EXISTS settlement_types
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name_hy VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    name_fr VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT IGNORE INTO settlement_types (id, name_hy, name_en, name_fr)
VALUES (1, 'քաղաք', 'City', 'Ville'),
       (2, 'ավան', 'Town', 'Bourg'),
       (3, 'գյուղ', 'Village', 'Village'),
       (4, 'գյուղատեղի', 'Village site', 'Site de village'),
       (5, 'բնակատեղի', 'Settlement site', 'Site d’habitat');

ALTER TABLE settlements
    ADD COLUMN another_names_hy TEXT,
    ADD COLUMN another_names_en TEXT,
    ADD COLUMN another_names_fr TEXT,
    ADD COLUMN history_hy       TEXT,
    ADD COLUMN history_en       TEXT,
    ADD COLUMN history_fr       TEXT,
    ADD COLUMN data_update_hy   LONGTEXT,
    ADD COLUMN data_update_en   LONGTEXT,
    ADD COLUMN data_update_fr   LONGTEXT,
    ADD COLUMN settlement_type  BIGINT NULL,
    ADD CONSTRAINT fk_settlements_settlement_type
        FOREIGN KEY (settlement_type) REFERENCES settlement_types (id);

CREATE TABLE IF NOT EXISTS settlement_topographics
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    settlement_id  BIGINT NOT NULL,
    topography_hy  TEXT,
    topography_en  TEXT,
    topography_fr  TEXT,
    address_hy     TEXT,
    address_en     TEXT,
    address_fr     TEXT,
    latitude       VARCHAR(255),
    longitude      VARCHAR(255),
    altitude_hy    TEXT,
    altitude_en    TEXT,
    altitude_fr    TEXT,
    hydrography_hy TEXT,
    hydrography_en TEXT,
    hydrography_fr TEXT,
    data_update_hy LONGTEXT,
    data_update_en LONGTEXT,
    data_update_fr LONGTEXT,
    PRIMARY KEY (id),
    CONSTRAINT uq_settlement_topographics_settlement UNIQUE (settlement_id),
    CONSTRAINT fk_settlement_topographics_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements (id) ON DELETE CASCADE
);

-- Coordinates belong to the topographic reference. Copy the ones settlements already
-- have; settlements.latitude / longitude are deliberately NOT dropped, so the copy can be
-- checked first and the columns removed in a later migration. Nothing reads them any more.
INSERT INTO settlement_topographics (settlement_id, latitude, longitude)
SELECT id, latitude, longitude
FROM settlements
WHERE (latitude IS NOT NULL AND TRIM(latitude) <> '')
   OR (longitude IS NOT NULL AND TRIM(longitude) <> '');

CREATE TABLE IF NOT EXISTS settlement_bibliography
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    settlement_id BIGINT NOT NULL,
    title_hy      TEXT,
    title_en      TEXT,
    title_fr      TEXT,
    url           TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_settlement_bibliography_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS settlement_measurements
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    settlement_id BIGINT NOT NULL,
    url           TEXT,
    caption_hy    TEXT,
    caption_en    TEXT,
    caption_fr    TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_settlement_measurements_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS settlement_videos
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    settlement_id BIGINT NOT NULL,
    title_hy      TEXT,
    title_en      TEXT,
    title_fr      TEXT,
    url           TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_settlement_videos_settlement
        FOREIGN KEY (settlement_id) REFERENCES settlements (id) ON DELETE CASCADE
);
