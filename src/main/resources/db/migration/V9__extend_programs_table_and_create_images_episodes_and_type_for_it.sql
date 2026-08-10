ALTER TABLE programs
    MODIFY COLUMN description_hy TEXT,
    MODIFY COLUMN description_en TEXT,
    MODIFY COLUMN description_fr TEXT;

ALTER TABLE programs
    ADD COLUMN program_hy TEXT,
    ADD COLUMN program_en TEXT,
    ADD COLUMN program_fr TEXT;

DROP TABLE IF EXISTS program_images;

CREATE TABLE program_images (
    id BIGINT NOT NULL AUTO_INCREMENT,
    url VARCHAR(255),
    caption_hy TEXT,
    caption_en TEXT,
    caption_fr TEXT,
    program_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_program_images_program
        FOREIGN KEY (program_id)
        REFERENCES programs(id)
        ON DELETE CASCADE
);

CREATE TABLE program_episodes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    program BIGINT NOT NULL,
    title_hy VARCHAR(255),
    title_en VARCHAR(255),
    title_fr VARCHAR(255),
    url VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_program_episodes_program
        FOREIGN KEY (program)
        REFERENCES programs(id)
        ON DELETE CASCADE
);

CREATE TABLE program_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name_hy VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    name_fr VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE programs
    ADD COLUMN program_type BIGINT,
    ADD CONSTRAINT fk_programs_program_type
        FOREIGN KEY (program_type)
        REFERENCES program_types(id);

INSERT INTO program_types
(id, name_hy, name_en, name_fr)
VALUES
    (1,'Գիտաժողով','Symposium','Symposium'),
    (2,'Կոնֆերանս','Conference','Conférence'),
    (3,'Դասախոսություն','Lecture','Lecture');