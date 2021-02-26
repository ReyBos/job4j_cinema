package ru.job4j.cinema.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.AccountTicket;
import ru.job4j.cinema.store.PsqlStore;
import ru.job4j.cinema.store.Store;

import java.util.List;

public class TicketBuyService {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private static final TicketBuyService INST = new TicketBuyService();
    private Store store = PsqlStore.instOf();

    private TicketBuyService() { }

    public static TicketBuyService instOf() {
        return INST;
    }

    public boolean buyTicket(Account account, List<AccountTicket> tickets) {
        return false;
    }
}
