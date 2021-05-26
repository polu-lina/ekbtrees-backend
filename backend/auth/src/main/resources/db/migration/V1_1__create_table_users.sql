CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1;

create table if not exists users
(
    id            bigint primary key default nextval('user_id_seq'),
    creation_date timestamp,
    email         varchar(255),
    enabled       boolean,
    fb_id         bigint,
    first_name    varchar(255),
    last_name     varchar(255),
    password      varchar(255),
    phone         varchar(255),
    provider      varchar(255),
    vk_id         bigint
);
