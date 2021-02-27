package ru.job4j.cinema.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.PsqlStore;
import ru.job4j.cinema.store.Store;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FilmSessionService {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private static final FilmSessionService INST = new FilmSessionService();
    private final Store store = PsqlStore.instOf();

    private FilmSessionService() { }

    public static FilmSessionService instOf() {
        return INST;
    }

    public String getFilmSessionInfo(int filmSessionId) {
        Map<String, String> rsl = new LinkedHashMap<>();
        final Gson gson = new GsonBuilder().create();
        Optional<FilmSession> filmSessionOpt = store.findFilmSessionById(filmSessionId);
        FilmSession filmSession = checkOptional(filmSessionOpt, "Киносеанс не найден");
        Optional<Hall> hallOpt = store.findHallById(filmSession.getHallId());
        Hall hall = checkOptional(hallOpt, "Зал не найден");
        Optional<List<Ticket>> ticketsOpt = store.findTicketsByFilmSessionId(filmSessionId);
        List<Ticket> tickets = checkOptional(ticketsOpt, "Билеты на сеанс не найдены");
        rsl.put("filmSession", gson.toJson(filmSession));
        rsl.put("hall", gson.toJson(hall));
        rsl.put("tickets", gson.toJson(tickets));
        return gson.toJson(rsl);
    }

    private <T> T checkOptional(Optional<T> optional, String msg) {
        if (optional.isEmpty()) {
            throw new IllegalArgumentException(msg);
        }
        return optional.get();
    }
}
