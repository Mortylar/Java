SET DATABASE SQL SYNTAX PGS TRUE;


DROP TABLE IF EXISTS Product CASCADE;

CREATE TABLE IF NOT EXISTS Product (
                Id SERIAL PRIMARY KEY NOT null,
                Name VARCHAR,
                Price FLOAT);
