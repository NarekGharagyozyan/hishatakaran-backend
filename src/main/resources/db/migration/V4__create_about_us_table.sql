CREATE TABLE about_us (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title_hy TEXT,
    title_en TEXT,
    title_fr TEXT,
    subtitle_hy TEXT,
    subtitle_en TEXT,
    subtitle_fr TEXT,
    text_hy TEXT,
    text_en TEXT,
    text_fr TEXT,
    background VARCHAR(255),
    PRIMARY KEY (id)
);