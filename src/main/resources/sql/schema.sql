-- menu
create or replace table menu
(
    id          bigint auto_increment
        primary key,
    parent_id   int           null,
    depth       int default 1 not null,
    title       varchar(255)  not null,
    link        text          not null,
    description text          null,
    created_at  datetime      not null,
    modified_at datetime      null,
    deleted_at  datetime      null
);

create or replace index menu_depth_index
    on menu (depth);

-- banner
create or replace table banner
(
    id             bigint auto_increment
        primary key,
    menu_id        bigint        null,
    title          varchar(255)  null,
    link           text          null,
    description    text          null,
    image_path     text          not null,
    image_width    int           not null,
    image_height   int           not null,
    image_size     int           not null,
    created_at     datetime      not null,
    modified_at    datetime      null,
    deleted_at     datetime      null
);

create or replace index banner_menu_id_index
    on banner (menu_id);