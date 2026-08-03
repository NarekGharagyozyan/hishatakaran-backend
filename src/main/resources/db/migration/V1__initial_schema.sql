create table if not exists admins
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)  null,
    updated_at    datetime(6)  null,
    login         varchar(255) not null,
    password_hash varchar(255) not null,
    constraint UK7gb4hqhf0qd8bhppyp3wq3hj
        unique (login)
);

create table if not exists contact_us
(
    id                    bigint auto_increment
        primary key,
    created_at            datetime(6)  null,
    updated_at            datetime(6)  null,
    email_or_phone_number varchar(255) null,
    message               varchar(255) null,
    name                  varchar(255) null
);

create table if not exists library
(
    id                bigint auto_increment
        primary key,
    authors_en        varchar(255) null,
    authors_fr        varchar(255) null,
    authors_hy        varchar(255) null,
    book_url          varchar(255) null,
    copyright_text_en varchar(255) null,
    copyright_text_fr varchar(255) null,
    copyright_text_hy varchar(255) null,
    copyright_url     varchar(255) null,
    cover_url         varchar(255) null,
    description_en    text         null,
    description_fr    text         null,
    description_hy    text         null,
    title_en          varchar(255) null,
    title_fr          varchar(255) null,
    title_hy          varchar(255) null
);

create table if not exists monument_types
(
    id      bigint auto_increment
        primary key,
    name_en varchar(255) not null,
    name_fr varchar(255) not null,
    name_hy varchar(255) not null
);

create table if not exists news
(
    id           bigint auto_increment
        primary key,
    is_published bit          null,
    text_en      text         null,
    text_fr      text         null,
    text_hy      text         null,
    title_en     varchar(255) null,
    title_fr     varchar(255) null,
    title_hy     varchar(255) null
);

create table if not exists news_images
(
    news_id    bigint       not null,
    images_url varchar(255) null,
    constraint FKhcwu8kdkcw4mkqlygdq0g3dtq
        foreign key (news_id) references news (id)
);

create table if not exists programs
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)  null,
    updated_at     datetime(6)  null,
    cover          varchar(255) null,
    description_en varchar(255) null,
    description_fr varchar(255) null,
    description_hy varchar(255) null,
    is_published   bit          null,
    pdf            varchar(255) null,
    title_en       varchar(255) null,
    title_fr       varchar(255) null,
    title_hy       varchar(255) null
);

create table if not exists program_images
(
    program_id bigint       not null,
    image_urls varchar(255) null,
    constraint FKp61a04ikc1meya65uo68uqkdv
        foreign key (program_id) references programs (id)
);

create table if not exists program_links
(
    id       bigint auto_increment
        primary key,
    title_en varchar(255) null,
    title_fr varchar(255) null,
    title_hy varchar(255) null,
    url      varchar(255) null,
    program  bigint       not null,
    constraint FK4npesj0masvnu7kvv2lrig7gy
        foreign key (program) references programs (id)
);

create table if not exists regions
(
    id      bigint auto_increment
        primary key,
    name_en varchar(255) not null,
    name_fr varchar(255) not null,
    name_hy varchar(255) not null
);

create table if not exists settlements
(
    id      bigint auto_increment
        primary key,
    name_en varchar(255) not null,
    name_fr varchar(255) not null,
    name_hy varchar(255) not null,
    region  bigint       not null,
    constraint FKdrbava1bwonyg6dm7i5nvyltb
        foreign key (region) references regions (id)
);

create table if not exists monuments
(
    id                                                    bigint auto_increment
        primary key,
    created_at                                            datetime(6)  null,
    updated_at                                            datetime(6)  null,
    another_names_en                                      text         null,
    another_names_fr                                      text         null,
    another_names_hy                                      text         null,
    history_en                                            text         null,
    history_fr                                            text         null,
    history_hy                                            text         null,
    individually_certifiable_parts_of_the_storage_unit_en text         null,
    individually_certifiable_parts_of_the_storage_unit_fr text         null,
    individually_certifiable_parts_of_the_storage_unit_hy text         null,
    is_published                                          bit          null,
    name_en                                               text         null,
    name_fr                                               text         null,
    name_hy                                               text         null,
    original_affiliation_en                               text         null,
    original_affiliation_fr                               text         null,
    original_affiliation_hy                               text         null,
    show_in_main_page                                     bit          null,
    signature                                             varchar(255) null,
    special_name_en                                       text         null,
    special_name_fr                                       text         null,
    special_name_hy                                       text         null,
    storage_unit_name_en                                  text         null,
    storage_unit_name_fr                                  text         null,
    storage_unit_name_hy                                  text         null,
    monument_type                                         bigint       not null,
    region                                                bigint       not null,
    settlement                                            bigint       not null,
    constraint FK6x8qtguijsw5h48iwrntktprv
        foreign key (region) references regions (id),
    constraint FKpwiqvq8gk1kkb964mt9nf854r
        foreign key (settlement) references settlements (id),
    constraint FKvx42nj5k5785lknm33fh839d
        foreign key (monument_type) references monument_types (id)
);

create table if not exists bibliography
(
    id       bigint auto_increment
        primary key,
    title_en varchar(255) null,
    title_fr varchar(255) null,
    title_hy varchar(255) null,
    url      varchar(255) null,
    monument bigint       not null,
    constraint FK88e7e3fb0ub351e0cumm4cpe5
        foreign key (monument) references monuments (id)
);

create table if not exists descriptive_characteristic_reference
(
    id                                                        bigint auto_increment
        primary key,
    archeological_overview_stratigraphy_findings_en           text   null,
    archeological_overview_stratigraphy_findings_fr           text   null,
    archeological_overview_stratigraphy_findings_hy           text   null,
    architectural_overview_en                                 text   null,
    architectural_overview_fr                                 text   null,
    architectural_overview_hy                                 text   null,
    area_en                                                   text   null,
    area_fr                                                   text   null,
    area_hy                                                   text   null,
    constructions_en                                          text   null,
    constructions_fr                                          text   null,
    constructions_hy                                          text   null,
    decorative_and_monumental_features_composition_colours_en text   null,
    decorative_and_monumental_features_composition_colours_fr text   null,
    decorative_and_monumental_features_composition_colours_hy text   null,
    depth_thickness_en                                        text   null,
    depth_thickness_fr                                        text   null,
    depth_thickness_hy                                        text   null,
    exterior_en                                               text   null,
    exterior_fr                                               text   null,
    exterior_hy                                               text   null,
    height_en                                                 text   null,
    height_fr                                                 text   null,
    height_hy                                                 text   null,
    implementation_technique_en                               text   null,
    implementation_technique_fr                               text   null,
    implementation_technique_hy                               text   null,
    length_en                                                 text   null,
    length_fr                                                 text   null,
    length_hy                                                 text   null,
    length_of_span_en                                         text   null,
    length_of_span_fr                                         text   null,
    length_of_span_hy                                         text   null,
    levels_of_construction_en                                 text   null,
    levels_of_construction_fr                                 text   null,
    levels_of_construction_hy                                 text   null,
    openings_entrances_en                                     text   null,
    openings_entrances_fr                                     text   null,
    openings_entrances_hy                                     text   null,
    openings_windows_en                                       text   null,
    openings_windows_fr                                       text   null,
    openings_windows_hy                                       text   null,
    roof_en                                                   text   null,
    roof_fr                                                   text   null,
    roof_hy                                                   text   null,
    state_of_monument_en                                      text   null,
    state_of_monument_fr                                      text   null,
    state_of_monument_hy                                      text   null,
    the_building_material_en                                  text   null,
    the_building_material_fr                                  text   null,
    the_building_material_hy                                  text   null,
    type_en                                                   text   null,
    type_fr                                                   text   null,
    type_hy                                                   text   null,
    valuation_en                                              text   null,
    valuation_fr                                              text   null,
    valuation_hy                                              text   null,
    width_en                                                  text   null,
    width_fr                                                  text   null,
    width_hy                                                  text   null,
    monument                                                  bigint not null,
    constraint UK3opfmye1lorel0hvvwxb3t7ou
        unique (monument),
    constraint FKn3iwq7pdobaxkwshnpr8amtl8
        foreign key (monument) references monuments (id)
);

create table if not exists footnotes
(
    id           bigint auto_increment
        primary key,
    order_number bigint null,
    text_en      text   null,
    text_fr      text   null,
    text_hy      text   null,
    monument_id  bigint null,
    constraint FKovwoi57br56wo7uqw7g7owwl6
        foreign key (monument_id) references monuments (id)
);

create table if not exists historical_references
(
    id                                                           bigint auto_increment
        primary key,
    author_en                                                    text   null,
    author_fr                                                    text   null,
    author_hy                                                    text   null,
    brief_historical_overview_en                                 text   null,
    brief_historical_overview_fr                                 text   null,
    brief_historical_overview_hy                                 text   null,
    chronological_table_of_the_monuments_study_en                text   null,
    chronological_table_of_the_monuments_study_fr                text   null,
    chronological_table_of_the_monuments_study_hy                text   null,
    chronological_table_of_the_stud_en                           text   null,
    chronological_table_of_the_stud_fr                           text   null,
    chronological_table_of_the_stud_hy                           text   null,
    cultural_affiliation_en                                      text   null,
    cultural_affiliation_fr                                      text   null,
    cultural_affiliation_hy                                      text   null,
    justification_of_the_numbering_according_iconography_en      text   null,
    justification_of_the_numbering_according_iconography_fr      text   null,
    justification_of_the_numbering_according_iconography_hy      text   null,
    justification_of_the_numbering_based_on_biblio_en            text   null,
    justification_of_the_numbering_based_on_biblio_fr            text   null,
    justification_of_the_numbering_based_on_biblio_hy            text   null,
    justification_of_the_numbering_based_on_evidence_en          text   null,
    justification_of_the_numbering_based_on_evidence_fr          text   null,
    justification_of_the_numbering_based_on_evidence_hy          text   null,
    justification_of_the_numbering_based_on_lithography_en       text   null,
    justification_of_the_numbering_based_on_lithography_fr       text   null,
    justification_of_the_numbering_based_on_lithography_hy       text   null,
    justification_of_the_numbering_based_on_reliable_document_en text   null,
    justification_of_the_numbering_based_on_reliable_document_fr text   null,
    justification_of_the_numbering_based_on_reliable_document_hy text   null,
    source_for_determining_the_author_en                         text   null,
    source_for_determining_the_author_fr                         text   null,
    source_for_determining_the_author_hy                         text   null,
    monument                                                     bigint not null,
    constraint UKep8fxrtmmka2oehlmvmoe5n8g
        unique (monument),
    constraint FKe1k92uo0d5b15g2iek0b9xk05
        foreign key (monument) references monuments (id)
);

create table if not exists monument_images
(
    id          bigint auto_increment
        primary key,
    caption_en  text         null,
    caption_fr  text         null,
    caption_hy  text         null,
    url         varchar(255) null,
    monument_id bigint       null,
    constraint FKbf3j1ogqjlvloxcg4h3slgkjv
        foreign key (monument_id) references monuments (id)
);

create table if not exists monument_measurements
(
    id          bigint auto_increment
        primary key,
    caption_en  text         null,
    caption_fr  text         null,
    caption_hy  text         null,
    url         varchar(255) null,
    monument_id bigint       null,
    constraint FKni0ba5xe9nb7pbab6dochi1mv
        foreign key (monument_id) references monuments (id)
);

create table if not exists monument_videos
(
    id       bigint auto_increment
        primary key,
    title_en varchar(255) null,
    title_fr varchar(255) null,
    title_hy varchar(255) null,
    url      varchar(255) null,
    monument bigint       not null,
    constraint FKn5yk3y2t3pw0v8pcwlakvg91q
        foreign key (monument) references monuments (id)
);

create table if not exists team_members
(
    id             bigint auto_increment
        primary key,
    description_en text         null,
    description_fr text         null,
    description_hy text         null,
    full_name_en   varchar(255) null,
    full_name_fr   varchar(255) null,
    full_name_hy   varchar(255) null,
    image          varchar(255) null,
    position_en    text         null,
    position_fr    text         null,
    position_hy    text         null,
    signature      varchar(255) null,
    url            varchar(255) null
);

create table if not exists topographics
(
    id                         bigint auto_increment
        primary key,
    address_en                 text         null,
    address_fr                 text         null,
    address_hy                 text         null,
    altitude_en                text         null,
    altitude_fr                text         null,
    altitude_hy                text         null,
    description_en             text         null,
    description_fr             text         null,
    description_hy             text         null,
    distance_from_residence_en text         null,
    distance_from_residence_fr text         null,
    distance_from_residence_hy text         null,
    hydrography_en             text         null,
    hydrography_fr             text         null,
    hydrography_hy             text         null,
    latitude                   varchar(255) null,
    longitude                  varchar(255) null,
    region_en                  text         null,
    region_fr                  text         null,
    region_hy                  text         null,
    topography_en              text         null,
    topography_fr              text         null,
    topography_hy              text         null,
    monument                   bigint       not null,
    constraint UKke0y6quii5lu5y57hs0u3qb8x
        unique (monument),
    constraint FK7ow4xmrieovwa4bjv4kvgi04k
        foreign key (monument) references monuments (id)
);

