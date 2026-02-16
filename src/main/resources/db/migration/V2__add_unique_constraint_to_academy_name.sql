CREATE UNIQUE INDEX unique_academy_name
    ON academy (LOWER(name));
