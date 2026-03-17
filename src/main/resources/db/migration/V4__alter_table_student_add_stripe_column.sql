ALTER TABLE student
    ADD COLUMN stripes SMALLINT NOT NULL DEFAULT 0;

ALTER TABLE student
    ADD CONSTRAINT check_stripes
        CHECK (stripes >= 0 AND stripes <= 4);