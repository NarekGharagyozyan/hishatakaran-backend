UPDATE program_types
SET name_fr = 'Colloque'
WHERE id = 1;

UPDATE program_types
SET name_hy = 'Սեմինար',
    name_en = 'Seminar',
    name_fr = 'Séminaire'
WHERE id = 2;

UPDATE program_types
SET name_fr = 'Conférence'
WHERE id = 3;

INSERT INTO program_types
(id, name_hy, name_en, name_fr)
VALUES
    (4, 'Համաժողով', 'Congress', 'Congrès'),
    (5, 'Ուսմանց օր', 'Study day', 'Journée d''étude');