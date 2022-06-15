package ru.job4j.dream.servlet;

import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindCandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = DbStore.instOf();
        String name = req.getParameter("name");
        req.setAttribute("candidates", store.findCandidateByName(name));
        req.setAttribute("allCities", store.allCity());
        req.getRequestDispatcher("/candidates.jsp").forward(req, resp);
    }
}
