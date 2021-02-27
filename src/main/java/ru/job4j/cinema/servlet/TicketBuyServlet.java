package ru.job4j.cinema.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.AccountTicket;
import ru.job4j.cinema.service.TicketBuyService;
import ru.job4j.cinema.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TicketBuyServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        TicketBuyService service = TicketBuyService.instOf();
        req.setCharacterEncoding("UTF-8");
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        String name = data.get("name").getAsString();
        String phone = data.get("phone").getAsString();
        Account account = new Account(name, phone);
        JsonArray ticketsJson = data.getAsJsonArray("tickets");
        List<AccountTicket> tickets = new ArrayList<>();
        for (int i = 0; i < ticketsJson.size(); i++) {
            JsonObject ticket = ticketsJson.get(i).getAsJsonObject();
            tickets.add(new AccountTicket(ticket.get("ticketId").getAsInt()));
        }
        boolean rsl = service.buyTicket(account, tickets);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (!rsl) {
            writer.write(
                    "При покупке билетов произошла ошибка! Необходимо обновить страницу. "
                    + "Страница перезагрузится автоматически через 8 секунд."
            );
            resp.setStatus(500);
        } else {
            writer.write(
                    "Покупка прошла успешно! Необходимо обновить страницу. "
                    + "Страница перезагрузится автоматически через 8 секунд."
            );
        }
        writer.flush();
    }
}
