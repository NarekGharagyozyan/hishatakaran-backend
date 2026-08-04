CREATE TABLE cultural_heritages (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title_hy TEXT,
    title_en TEXT,
    title_fr TEXT,
    subtitle_hy TEXT,
    subtitle_en TEXT,
    subtitle_fr TEXT,
    background VARCHAR(255),
    PRIMARY KEY (id)
);