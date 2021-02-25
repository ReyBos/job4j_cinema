package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private double price;
    private int filmSessionID;
    private int seatID;

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket(int id, double price, int filmSessionID, int seatID) {
        this.id = id;
        this.price = price;
        this.filmSessionID = filmSessionID;
        this.seatID = seatID;
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

    public int getFilmSessionID() {
        return filmSessionID;
    }

    public void setFilmSessionID(int filmSessionID) {
        this.filmSessionID = filmSessionID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
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
