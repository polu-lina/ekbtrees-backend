CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 1;

create table if not exists roles
(
    id   bigint primary key default nextval('role_id_seq'),
    name varchar(255),
    creation_date timestamp
);

create table if not exists users_roles
(
    role_id bigint not null constraint users_roles_user_id references users,
    user_id bigint not null constraint users_roles_role_id references roles,
    constraint users_roles_pkey
        primary key (role_id, user_id)
);