ALTER TABLE trees
    DROP COLUMN trunk_states,
    DROP COLUMN branch_states,
    DROP COLUMN cortical_states;

ALTER TABLE trees
    ADD COLUMN trunk_states varchar(1024),
    ADD COLUMN branch_states varchar(1024),
    ADD COLUMN cortical_states varchar(1024);
