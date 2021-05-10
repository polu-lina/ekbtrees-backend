CREATE SEQUENCE IF NOT EXISTS comment_id_seq START WITH 1;

CREATE TABLE IF NOT EXISTS comment (
    id bigint PRIMARY KEY DEFAULT nextval('comment_id_seq'),
    created timestamp with time zone NOT NULL DEFAULT now(),
    author_id bigint NOT NULL,
    text text NOT NULL,
    tree_id bigint NOT NULL
);