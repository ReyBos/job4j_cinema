package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private double price;
    private int filmSessionId;
    private int seatId;
    private int row;
    private int seat;
    private int accountId;

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket(int id, double price, int filmSessionId, int seatId) {
        this.id = id;
        this.price = price;
        this.filmSessionId = filmSessionId;
        this.seatId = seatId;
    }

    public Ticket(
            int id, double price, int filmSessionId, int seatId, int row, int seat, int accountId
    ) {
        this.id = id;
        this.price = price;
        this.filmSessionId = filmSessionId;
        this.seatId = seatId;
        this.row = row;
        this.seat = seat;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFilmSessionId() {
        return filmSessionId;
    }

    public void setFilmSessionId(int filmSessionId) {
        this.filmSessionId = filmSessionId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
