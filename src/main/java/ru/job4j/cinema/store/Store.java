package ru.job4j.cinema.store;

import ru.job4j.cinema.model.*;

import java.util.List;
import java.util.Optional;

public interface Store {
    Optional<FilmSession> findFilmSessionById(int id);

    Optional<Hall> findHallById(int id);

    Optional<List<Ticket>> findTicketsByFilmSessionId(int id);

    Optional<Account> findAccountByPhone(String phone);

    void save(Account account);

    void save(AccountTicket accountTicket);

    void delete(Account account);
}
