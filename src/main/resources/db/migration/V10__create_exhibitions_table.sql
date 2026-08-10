CREATE TABLE exhibitions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    is_published BOOLEAN,
    title_hy VARCHAR(255),
    title_en VARCHAR(255),
    title_fr VARCHAR(255),
    description_hy TEXT,
    description_en TEXT,
    description_fr TEXT,
    program_hy TEXT,
    program_en TEXT,
    program_fr TEXT,
    pdf VARCHAR(255),
    cover VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE exhibition_images (
    id BIGINT NOT NULL AUTO_INCREMENT,
    url VARCHAR(255),
    caption_hy TEXT,
    caption_en TEXT,
    caption_fr TEXT,
    exhibition_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_exhibition_images_exhibition
        FOREIGN KEY (exhibition_id)
        REFERENCES exhibitions(id)
        ON DELETE CASCADE
);

CREATE TABLE exhibition_links (
    id BIGINT NOT NULL AUTO_INCREMENT,
    exhibition BIGINT NOT NULL,
    title_hy VARCHAR(255),
    title_en VARCHAR(255),
    title_fr VARCHAR(255),
    url VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_exhibition_links_exhibition
        FOREIGN KEY (exhibition)
        REFERENCES exhibitions(id)
        ON DELETE CASCADE
);

CREATE TABLE exhibition_videos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    exhibition BIGINT NOT NULL,
    title_hy VARCHAR(255),
    title_en VARCHAR(255),
    title_fr VARCHAR(255),
    url VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_exhibition_videos_exhibition
        FOREIGN KEY (exhibition)
        REFERENCES exhibitions(id)
        ON DELETE CASCADE
);