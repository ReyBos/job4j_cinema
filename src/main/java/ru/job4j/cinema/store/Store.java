package ru.job4j.cinema.store;

import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface Store {
    Optional<FilmSession> findFilmSessionById(int id);

    Optional<Hall> findHallById(int id);

    Optional<List<Ticket>> findTicketsByFilmSessionId(int id);
}
