package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateShowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = DbStore.instOf();
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        Candidate candidate = store.findCandidateById(id);
        String city = store.allCity().get(candidate.getCityId());
        req.setAttribute("city", city);
        req.setAttribute("candidate", candidate);
        req.getRequestDispatcher("/candidate/show.jsp").forward(req, resp);
    }
}
