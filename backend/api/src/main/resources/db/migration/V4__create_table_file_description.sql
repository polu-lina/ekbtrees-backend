CREATE SEQUENCE IF NOT EXISTS file_description_id_seq START WITH 1;

CREATE TABLE IF NOT EXISTS file_description (
    id bigint PRIMARY KEY DEFAULT nextval('file_description_id_seq'),
    title text,
    mime_type text,
    size bigint,
    uri text NOT NULL,
    hash text NOT NULL
);