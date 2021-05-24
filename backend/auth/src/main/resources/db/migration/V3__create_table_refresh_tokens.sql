CREATE SEQUENCE IF NOT EXISTS refresh_tokens_id_seq START WITH 1;

create table if not exists refresh_tokens
(
    id            bigint primary key default nextval('user_id_seq'),
    creation_date timestamp,
    token         text,
    user_id       bigint not null constraint refresh_tokens_user_id references users
);
