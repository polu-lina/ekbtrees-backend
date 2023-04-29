ALTER TABLE trees
    ADD COLUMN pruning varchar(255),
    ADD COLUMN root_condition varchar(255),
    ADD COLUMN trunk_states varchar(255)[],
    ADD COLUMN branch_states varchar(255)[],
    ADD COLUMN cortical_states varchar(255)[];