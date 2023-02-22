DROP TABLE coffee IF EXISTS;

CREATE TABLE coffee  (
    coffee_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand VARCHAR(20),
    origin VARCHAR(20),
    characteristics VARCHAR(30)
);