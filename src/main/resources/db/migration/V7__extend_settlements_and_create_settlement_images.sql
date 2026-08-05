ALTER TABLE settlements
    ADD COLUMN description_hy TEXT,
    ADD COLUMN description_en TEXT,
    ADD COLUMN description_fr TEXT,
    ADD COLUMN longitude VARCHAR(255),
    ADD COLUMN latitude VARCHAR(255);

CREATE TABLE settlement_images (
    id BIGINT NOT NULL AUTO_INCREMENT,
    url TEXT,
    caption_hy TEXT,
    caption_en TEXT,
    caption_fr TEXT,
    settlement_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_settlement_images_settlement
        FOREIGN KEY (settlement_id)
            REFERENCES settlements(id)
            ON DELETE CASCADE
);