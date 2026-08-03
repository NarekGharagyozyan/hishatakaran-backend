CREATE TABLE monument_statuses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_hy TEXT NOT NULL,
    name_en TEXT NOT NULL,
    name_fr TEXT NOT NULL
);


ALTER TABLE monuments
    ADD COLUMN monument_status BIGINT;


ALTER TABLE monuments
    ADD CONSTRAINT fk_monument_status
    FOREIGN KEY (monument_status)
    REFERENCES monument_statuses(id);


INSERT INTO monument_statuses
(id, name_hy, name_en, name_fr)
VALUES
    (1,'Հուշարձանի «անձնագիր»','Monument "Passport"','«Passeport» du monument'),
    (2,'Վավերացված հուշարձան','Documented Monument','Monument documenté');