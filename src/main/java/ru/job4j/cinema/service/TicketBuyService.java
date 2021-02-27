package ru.job4j.cinema.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.AccountTicket;
import ru.job4j.cinema.store.PsqlStore;
import ru.job4j.cinema.store.Store;

import java.util.List;
import java.util.Optional;

public class TicketBuyService {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private static final TicketBuyService INST = new TicketBuyService();
    private final Store store = PsqlStore.instOf();

    private TicketBuyService() { }

    public static TicketBuyService instOf() {
        return INST;
    }

    public boolean buyTicket(Account account, List<AccountTicket> tickets) {
        Optional<Account> accountDB = store.findAccountByPhone(account.getPhone());
        if (accountDB.isPresent()) {
            account = accountDB.get();
        } else {
            store.save(account);
        }
        for (AccountTicket accountTicket : tickets) {
            accountTicket.setAccountId(account.getId());
            store.save(accountTicket);
        }
        return true;
    }
}
