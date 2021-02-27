package ru.job4j.cinema.store;

import org.junit.Test;
import ru.job4j.cinema.model.Account;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PsqlStoreTest {
    @Test
    public void whenAddFindAndDeleteAccount() {
        Store store = PsqlStore.instOf();
        Account account = new Account("Андрей", "1234567");
        store.save(account);
        assertTrue(account.getId() > 0);
        Optional<Account> accountDb = store.findAccountByPhone("1234567");
        assertTrue(accountDb.isPresent());
        assertThat(accountDb.get(), is(account));
        store.delete(account);
        assertTrue(store.findAccountByPhone("1234567").isEmpty());
    }
}