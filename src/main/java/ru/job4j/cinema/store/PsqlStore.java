package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class PsqlStore implements Store {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (InputStream in = PsqlStore.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            cfg.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Optional<FilmSession> findFilmSessionById(int id) {
        String sql = "SELECT * FROM film_session WHERE id = ?";
        FilmSession filmSession = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    filmSession = new FilmSession(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("date"),
                            resultSet.getInt("hall_id")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return filmSession == null ? Optional.empty() : Optional.of(filmSession);
    }

    @Override
    public Optional<Hall> findHallById(int id) {
        String sql = "SELECT * FROM hall WHERE id = ?";
        Hall hall = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    hall = new Hall(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return hall == null ? Optional.empty() : Optional.of(hall);
    }

    @Override
    public Optional<List<Ticket>> findTicketsByFilmSessionId(int id) {
        String sql = "SELECT ticket.*, seat.row, seat.seat, account_ticket.account_id "
                + " FROM ticket "
                + " LEFT JOIN seat ON ticket.seat_id = seat.id "
                + " LEFT JOIN account_ticket ON ticket.id = account_ticket.ticket_id "
                + " WHERE film_session_id = ? "
                + " ORDER BY row ASC, seat ASC";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(
                            resultSet.getInt("id"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("film_session_id"),
                            resultSet.getInt("seat_id"),
                            resultSet.getInt("row"),
                            resultSet.getInt("seat"),
                            resultSet.getInt("account_id")
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return tickets.size() == 0 ? Optional.empty() : Optional.of(tickets);
    }

    @Override
    public Optional<Account> findAccountByPhone(String phone) {
        String sql = "SELECT * FROM account WHERE phone = ?";
        Account account = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(sql)
        ) {
            ps.setString(1, phone);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    account = new Account(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return account == null ? Optional.empty() : Optional.of(account);
    }

    @Override
    public void save(Account account) {
        String sql = "insert into account(name, phone) values (?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public void save(AccountTicket accountTicket) {
        String sql = "insert into account_ticket(account_id, ticket_id) values (?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setInt(1, accountTicket.getAccountId());
            ps.setInt(2, accountTicket.getTicketId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    accountTicket.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }
}
