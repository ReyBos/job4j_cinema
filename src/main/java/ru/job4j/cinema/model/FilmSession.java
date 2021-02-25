package ru.job4j.cinema.model;

import java.sql.Timestamp;
import java.util.Objects;

public class FilmSession {
    private int id;
    private String name;
    private Timestamp date;
    private int hallID;

    public FilmSession(int id) {
        this.id = id;
    }

    public FilmSession(int id, String name, Timestamp date, int hallID) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.hallID = hallID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
