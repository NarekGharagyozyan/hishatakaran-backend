create table if not exists main_page
(
    id         bigint auto_increment
        primary key,
    background varchar(255) null,
    text_en    text         null,
    text_fr    text         null,
    text_hy    text         null,
    title_en   text         null,
    title_fr   text         null,
    title_hy   text         null
);

