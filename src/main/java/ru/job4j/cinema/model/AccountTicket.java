package ru.job4j.cinema.model;

import java.util.Objects;

public class AccountTicket {
    private int id;
    private int accountID;
    private int ticketId;

    public AccountTicket(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountTicket that = (AccountTicket) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
