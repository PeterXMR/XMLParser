DROP TABLE IF EXISTS municipality;

CREATE TABLE municipality
(
    id SERIAL PRIMARY KEY,
    code INT NOT NULL,
    name VARCHAR (50) NOT NULL
);

DROP TABLE IF EXISTS district;

CREATE TABLE district
(
    id  SERIAL PRIMARY KEY,
    code INT NOT NULL,
    name VARCHAR (50) NOT NULL,
    municipality_code INT NOT NULL
);
