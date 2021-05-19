ALTER TABLE file_description
ADD tree_id bigint,
ADD FOREIGN KEY (tree_id) REFERENCES tree(id)