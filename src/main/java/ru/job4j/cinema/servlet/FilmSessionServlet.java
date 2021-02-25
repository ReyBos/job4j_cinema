package ru.job4j.cinema.servlet;

import ru.job4j.cinema.service.FilmSessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FilmSessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int filmSessionId = 1;
        FilmSessionService service = FilmSessionService.instOf();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(service.getFilmSessionInfo(filmSessionId));
        writer.flush();
    }
}