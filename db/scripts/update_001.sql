CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    UNIQUE (phone)
);

CREATE TABLE hall (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE seat (
    id SERIAL PRIMARY KEY,
    row INT NOT NULL,
    seat INT NOT NULL,
    hall_id INT REFERENCES hall(id),
    UNIQUE (row, seat, hall_id)
);

CREATE TABLE film_session (
    id SERIAL PRIMARY KEY,
    name TEXT,
    date TIMESTAMP,
    hall_id INT REFERENCES hall(id),
    UNIQUE (date, hall_id)
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    price NUMERIC(8,2) NOT NULL,
    film_session_id INT REFERENCES film_session(id),
    seat_id INT REFERENCES seat(id),
    UNIQUE (film_session_id, seat_id)
);

CREATE TABLE account_ticket (
    id SERIAL PRIMARY KEY,
    account_id INT REFERENCES account(id),
    ticket_id INT REFERENCES ticket(id),
    UNIQUE (ticket_id)
);