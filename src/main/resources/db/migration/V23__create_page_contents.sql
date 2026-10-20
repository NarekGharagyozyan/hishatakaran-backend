CREATE TABLE IF NOT EXISTS page_contents
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    page           VARCHAR(50) NOT NULL,
    title_hy       TEXT,
    title_en       TEXT,
    title_fr       TEXT,
    description_hy TEXT,
    description_en TEXT,
    description_fr TEXT,
    background     VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (page)
);
