INSERT INTO regions (id, name)
VALUES (1, 'Shirak');

INSERT INTO settlements (id, region)
VALUES (1, 1);

INSERT INTO state_of_monument (name)
VALUES ('GOOD');

INSERT INTO library (id, title, book_url, cover_url)
VALUES (
           UUID_TO_BIN('11111111-1111-1111-1111-111111111111'),
           'Sample Book',
           'https://example.com/book.pdf',
           'https://example.com/cover.jpg'
       );

INSERT INTO monuments (
    id,
    status,
    name,
    region,
    settlement,
    monument_type,
    special_name,
    history,
    original_affiliation,
    storage_unit_name,
    monument_condition,
    created_at,
    updated_at
)
VALUES (
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),
           'PUBLISHED',
           'Sample Monument',
           1,
           1,
           'ARCHAEOLOGICAL',
           'Special Monument',
           'Sample monument history',
           'Ancient Culture',
           'National Museum',
           'Well Preserved',
           NOW(),
           NOW()
       );

INSERT INTO monument_another_names (monument_id, another_name)
VALUES (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'Alternative Monument Name');

INSERT INTO monument_pictures (monument_id, picture_url)
VALUES (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'https://example.com/monument.jpg');

INSERT INTO bibliography (id, monument)
VALUES (
           UUID_TO_BIN('33333333-3333-3333-3333-333333333333'),
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222')
       );

INSERT INTO bibliography_urls (bibliography_id, url)
VALUES (
           UUID_TO_BIN('33333333-3333-3333-3333-333333333333'),
           'https://example.com/bibliography'
       );

INSERT INTO topographics (
    id,
    monument,
    region_history,
    address,
    topography,
    distance_from_residence,
    altitude,
    hydrography,
    description
)
VALUES (
           UUID_TO_BIN('44444444-4444-4444-4444-444444444444'),
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),
           'Historic Shirak',
           'Shirak Province',
           'Mountain slope',
           '2 km',
           1500,
           'Nearby river',
           'Topographic sample description'
       );

INSERT INTO historical_references (
    id,
    monument,
    cultural_affiliation,
    century,
    justification_of_the_numbering_based_on_lithography,
    chronological_table_of_the_stud,
    author
)
VALUES (
           UUID_TO_BIN('55555555-5555-5555-5555-555555555555'),
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),
           'Urartian',
           '8th century BC',
           'Based on inscription style',
           'Chronological sample table',
           'Historian Name'
       );

INSERT INTO descriptive_characteristic_reference (
    id,
    monument,
    the_building_material,
    type,
    color,
    implementation_technique,
    state_of_monument,
    valuation
)
VALUES (
           UUID_TO_BIN('66666666-6666-6666-6666-666666666666'),
           UUID_TO_BIN('22222222-2222-2222-2222-222222222222'),
           'Basalt',
           'Temple structure',
           'BLACK',
           'Stone carving',
           'GOOD',
           'High historical value'
       );

INSERT INTO news (
    id,
    title,
    text,
    status
)
VALUES (
           UUID_TO_BIN('77777777-7777-7777-7777-777777777777'),
           'Sample News',
           'Sample news content',
           'PUBLISHED'
       );

INSERT INTO news_pictures (news_id, picture_url)
VALUES (
           UUID_TO_BIN('77777777-7777-7777-7777-777777777777'),
           'https://example.com/news.jpg'
       );